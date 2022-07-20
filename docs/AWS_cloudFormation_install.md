








## Integrating with GitHub Actions – CICD pipeline to Deploy a Web App to Amazon EC2

De nombreuses organisations adoptent les [pratiques DevOps](https://aws.amazon.com/devops/what-is-devops/) pour innover plus rapidement en automatisant et en rationalisant les processus de développement logiciel et de gestion de l'infrastructure. Au-delà de l'adoption culturelle, DevOps suggère également de suivre certaines meilleures pratiques et l'intégration continue et la livraison continue (CI/CD) sont parmi les plus importantes pour commencer. La pratique CI/CD réduit le temps nécessaire pour publier de nouvelles mises à jour logicielles en automatisant les activités de déploiement. De nombreux outils sont disponibles pour mettre en œuvre cette pratique. Bien qu'AWS dispose d'un ensemble d'outils natifs pour vous aider à atteindre vos objectifs CI/CD, il offre également une flexibilité et une extensibilité pour l'intégration avec de nombreux outils tiers.

Dans cet article, vous utiliserez [GitHub Actions](https://help.github.com/en/actions) pour créer un flux de travail CI/CD et [AWS CodeDeploy](https://aws.amazon.com/codedeploy/) pour déployer un exemple d'application Java SpringBoot sur des instances Amazon Elastic Compute Cloud ([Amazon EC2](https://docs.aws.amazon.com/ec2/index.html?nc2=h_ql_doc_ec2#amazon-ec2)) dans un Groupe de mise à l'échelle automatique.

GitHub Actions est une fonctionnalité de la plate-forme de développement populaire de GitHub qui vous aide à automatiser vos flux de travail de développement de logiciels au même endroit où vous stockez le code et collaborez sur les demandes d'extraction et les problèmes. Vous pouvez écrire des tâches individuelles appelées actions, puis les combiner pour créer un flux de travail personnalisé. Les workflows sont des processus automatisés personnalisés que vous pouvez configurer dans votre référentiel pour créer, tester, empaqueter, publier ou déployer n'importe quel projet de code sur GitHub.

AWS CodeDeploy est un service de déploiement qui automatise les déploiements d'applications sur des instances Amazon EC2, des instances sur site, des fonctions AWS Lambda sans serveur ou des services Amazon Elastic Container Service (Amazon ECS).


## Vue d'ensemble de la solution

La solution utilise les services suivants :

- [GitHub Actions](https://docs.github.com/en/actions) : Outil d'orchestration de workflow qui hébergera la Pipeline.
- [AWS CodeDeploy](https://aws.amazon.com/codedeploy/) : Service AWS pour gérer le déploiement sur Amazon EC2 Autoscaling Group.
- [AWS Auto Scaling](https://aws.amazon.com/ec2/autoscaling/) : Service AWS pour aider à maintenir la disponibilité et l'élasticité des applications en ajoutant ou supprimant automatiquement des instances EC2.
- [Amazon EC2](https://docs.aws.amazon.com/ec2/index.html?nc2=h_ql_doc_ec2#amazon-ec2) : Serveur de calcul pour le déploiement de l'application.
- [AWS CloudFormation](https://aws.amazon.com/cloudformation/) : Service AWS d'infrastructure en tant que code (IaC) utilisé pour faire tourner l'infrastructure initiale côté AWS.
- [IAM OIDC identity provider](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_roles_providers_create_oidc.html) : Service d'authentification fédérée pour établir la confiance entre GitHub et AWS afin de permettre aux actions GitHub de se déployer sur AWS sans conserver les secrets et les informations d'identification AWS.
- [Amazon S3](https://docs.aws.amazon.com/AmazonS3/latest/userguide/Welcome.html) : Amazon S3 pour stocker les artefacts de déploiement.

Le schéma suivant illustre l'architecture de la solution :
![Alt ​​Text](images/aws-coodedeplooy-github-action-deploymentV3.png?raw=true "Titre")

1. Le développeur valide les modifications de code de son référentiel local vers le référentiel GitHub. Dans cet article, l'action GitHub est déclenchée manuellement, mais cela peut être automatisé.
2. L'action GitHub déclenche l'étape de construction.
3. L'Open ID Connector (OIDC) de GitHub utilise les jetons pour s'authentifier auprès d'AWS et accéder aux ressources.
4. L'action GitHub charge les artefacts de déploiement sur Amazon S3.
5. L'action GitHub appelle CodeDeploy.
6. CodeDeploy déclenche le déploiement sur les instances Amazon EC2 dans un groupe Autoscaling.
7. CodeDeploy télécharge les artefacts depuis Amazon S3 et les déploie sur les instances Amazon EC2.

## Conditions préalables
Avant de commencer, vous devez remplir les conditions préalables suivantes :
    
   * Un compte AWS avec des autorisations pour créer les ressources nécessaires.
   * Un [Git Client](https://git-scm.com/downloads) pour cloner le code source fourni.
   * Un [compte GitHub](https://github.com/) avec des autorisations pour configurer les repo GitHub, créer des flux de travail et configurer les secrets GitHub.

## Procédure pas à pas
Les étapes suivantes fournissent une vue d'ensemble de haut niveau de la procédure pas à pas :

  1. Clonez le projet à partir du référentiel d'exemples de code AWS.
  2. Déployez le modèle AWS CloudFormation pour créer les services requis.
  3. Mettez à jour le code source.
  4. Configurez les secrets GitHub.
  5. Intégrez CodeDeploy à GitHub
  6. Déclenchez l'action GitHub pour générer et déployer le code.
  7. Vérifiez le déploiement.


## Télécharger le code source

Clonez le repository aws-codedeploy-github-actions-deployment 

    git clone https://github.com/aws-samples/aws-codedeploy-github-actions-deployment.git

Créez un repository vide dans votre compte GitHub personnel. Clonez ce référentiel sur votre ordinateur. Ignorez l'avertissement concernant le clonage d'un référentiel vide.

    git clone https://github.com/GitSDamien/GitActionsDeployToAWS.git
    
Copiez le code. Nous avons besoin du contenu du dossier caché .github pour que les actions GitHub fonctionnent.

    cp -r aws-codedeploy-github-actions-deployment/. GitActionsDeployToAWS

Structure des dossiers du référentiel

- Le dossier .github contient les actions définies dans le fichier YAML.
- Le dossier aws/scripts contient du code à exécuter lors des différents événements du cycle de vie du déploiement.
- Le dossier cloudformation contient le fichier template.yaml pour créer les ressources AWS requises.
- emergency-responder-subsystem est un exemple d'application utilisé par les actions GitHub pour créer et déployer.
- La racine du dépôt contient appspec.yml. Ce fichier est requis par CodeDeploy pour effectuer le déploiement sur Amazon EC2. Trouvez plus de détails [ici](https://translate.google.com/website?sl=auto&tl=fr&hl=fr&client=webapp&u=https://docs.aws.amazon.com/codedeploy/latest/userguide/reference-appspec-file-structure-hooks.html).

Les commandes suivantes vous aideront à vous assurer que votre référentiel distant pointe vers votre référentiel GitHub personnel.

    git remote remove origin

    git remote add origin https://github.com/GitSDamien/GitActionsDeployToAWS.git

    git branch -M main

    git push -u origin main


## Déployer le modèle CloudFormation
Pour déployer le modèle CloudFormation :

1. Ouvrez la console [AWS CloudFormation](https://console.aws.amazon.com/cloudformation/). Saisissez votre ID de compte, votre nom d'utilisateur et votre mot de passe.
2. Vérifiez votre région, car cette solution utilise us-east-1.
3. S'il s'agit d'un nouveau compte AWS CloudFormation, sélectionnez Créer une nouvelle pile. Sinon, sélectionnez Créer une pile.
4. Sélectionnez le modèle est prêt
5. Sélectionnez Télécharger un fichier de modèle
6. Sélectionnez Choisir un fichier. Accédez au fichier template.yml dans cloudformation/template.yaml.
7. Sélectionnez le fichier template.yml, puis sélectionnez suivant. (URL S3 : https://s3-external-1.amazonaws.com/cf-templates-142392yv9vyew-us-east-1/2022148YyT-template.yaml)
8. Dans Spécifier les détails de la pile, ajoutez ou modifiez les valeurs selon vos besoins.
    - Nom de la pile = CodeDeployStack.
    - VPC et sous-réseaux = (ceux-ci sont pré-remplis pour vous) vous pouvez modifier ces valeurs si vous préférez utiliser vos propres sous-réseaux)
    - GitHubThumbprintList = 6938fd4d98bab03faadb97b34396831e3780aea1
    - GitHubRepoName – GitSDamien/GitActionsDeployToAWS (Nom de votre référentiel personnel GitHub que vous avez créé)

9.	Sur la page Options, sélectionnez Suivant.
10.	Cochez la case de confirmation (Je sais qu’AWS CloudFormation peut créer des ressources IAM avec des noms personnalisés.) pour autoriser la création de ressources IAM, puis sélectionnez Créer. Il faudra environ 10 minutes à CloudFormation pour créer toutes les ressources. Cette pile créerait les ressources suivantes.
    - Deux instances Amazon EC2 Linux avec le serveur Tomcat et l'agent CodeDeploy sont installées
    - Groupe d'autoscaling avec équilibreur de charge
    - Nom de l'application CodeDeploy et groupe de déploiement
    - Compartiment Amazon S3 pour stocker les artefacts de build
    - Fournisseur d'identité OIDC de gestion des identités et des accès (IAM)
    - Profil d'instance pour Amazon EC2
    - Rôle de service pour CodeDeploy
    - Groupes de sécurité pour ALB et Amazon EC2


## Mettre à jour le code source

1.  Sur la console AWS CloudFormation, sélectionnez l'onglet Sorties. Notez que le nom du compartiment Amazon S3 et l'ARM du rôle IAM GitHub. Nous l'utiliserons à l'étape suivante.
    - S3 bucket name = DeploymentBucket

      codedeploystack-webappdeploymentbucket-rp19d7px496z

    - IAM Role for GitHub = GithubIAMRoleArn	
    
      arn:aws:iam::636384929310:role/CodeDeployRoleforGitHub

2. Mettez à jour le compartiment Amazon S3 dans le fichier de workflow deploy.yml. Accédez à /.github/workflows/deploy.yml depuis le répertoire racine de votre projet.

    Remplacez ##s3-bucket## par le nom du compartiment Amazon S3 créé précédemment. (codedeploystack-webappdeploymentbucket-rp19d7px496z)

    Remplacez ##region## par votre région AWS (us-east-1)

3. Mettez à jour le nom du compartiment Amazon S3 dans after-install.sh. Accédez à aws/scripts/after-install.sh. Ce script copie l'artefact de déploiement du compartiment Amazon S3 vers le dossier Tomcat Webapps.

4. N'oubliez pas de sauvegarder tous les fichiers et de pousser le code vers votre référentiel GitHub.

## Configurer les secrets GitHub
Les workflows GitHub Actions doivent accéder aux ressources de votre compte AWS. Ici, nous utilisons le fournisseur d'identité IAM OpenID Connect et le rôle IAM avec les stratégies IAM pour accéder à CodeDeploy et au compartiment Amazon S3. OIDC permet à vos flux de travail GitHub Actions d'accéder aux ressources dans AWS sans avoir besoin de stocker les informations d'identification AWS en tant que secrets GitHub de longue durée.
- Accédez à votre référentiel github. Sélectionnez l'onglet Paramètres.
- Sélectionnez Secrets dans la barre de menu de gauche.
- Sélectionnez Nouveau secret de référentiel.
- Sélectionnez Actions sous Secrets.
- Entrez le nom secret comme 'IAMROLE_GITHUB'.
- entrez la valeur en tant qu'ARN de GitHubIAMRole, que vous avez copié à partir de la section de sortie CloudFormation.
    
    arn:aws:iam::636384929310:role/CodeDeployRoleforGitHub

## Intégrer CodeDeploy avec GitHub
Pour que CodeDeploy puisse effectuer des étapes de déploiement à l'aide de scripts dans votre référentiel, il doit être intégré à GitHub.

L'application CodeDeploy et le groupe de déploiement sont déjà créés pour vous. Veuillez utiliser ces applications à l'étape suivante :

CodeDeploy Application = CodeDeployEmergencyResponderWithASG

Deployment group = CodeDeployGroupEmergencyResponder

1. Connectez-vous à AWS Management Console et ouvrez la console CodeDeploy à l' adresse https://console.aws.amazon.com/codedeploy

2. Dans le volet de navigation, développez Déployer , puis choisissez Applications .

3. Choisissez l'application que vous souhaitez lier à un autre compte GitHub.

4. Si votre application n'a pas de groupe de déploiement, choisissez Créer un groupe de déploiement pour en créer un. Pour plus d'informations, consultez Créer un groupe de déploiement avec CodeDeploy . Un groupe de déploiement est requis pour choisir Créer un déploiement à l'étape suivante.

5. Dans Déploiements , choisissez Créer un déploiement .

6. Dans Paramètres de déploiement , pour Type de révision , choisissez Mon application est stockée dans GitHub .

7. Pour créer une connexion pour les applications AWS CodeDeploy à un compte GitHub, déconnectez-vous de GitHub dans un onglet de navigateur Web distinct. Dans GitHub token name , tapez un nom pour identifier cette connexion, puis choisissez Connect to GitHub . La page Web vous invite à autoriser CodeDeploy à interagir avec GitHub pour votre application. Passez à l'étape 10.



## Déclencher le flux de travail des actions GitHub

Vous disposez maintenant des ressources AWS requises et avez configuré GitHub pour créer et déployer le code sur les instances Amazon EC2.

Les actions GitHub telles que définies dans  GITHUBREPO/.github/workflows/deploy.yml nous permettraient d'exécuter le workflow. Le workflow est actuellement configuré pour être exécuté manuellement.

Suivez les étapes suivantes pour l'exécuter manuellement.

Accédez à votre dépôt GitHub et sélectionnez l'onglet Actions

Sélectionnez le lien Générer et déployer , puis sélectionnez Exécuter le workflow comme indiqué dans l'image suivante.

Après quelques secondes, le workflow s'affiche. Ensuite, sélectionnez Construire et déployer .

Vous verrez deux étapes :

Build and Package.
Deploy.


## Build and Package
L'étape Build and Package crée l'exemple d'application SpringBoot, génère le fichier war, puis le charge dans le compartiment Amazon S3.

Vous devriez pouvoir voir le fichier war dans le compartiment Amazon S3.


## Deploy
À cette étape, le workflow invoquerait le service CodeDeploy et déclencherait le déploiement.

## Vérifier le déploiement
Connectez-vous à la console AWS et accédez à la console CodeDeploy.

Sélectionnez le nom de l'application et le groupe de déploiement. Vous verrez le statut Succeeded si le déploiement est réussi.

Faites pointer vos navigateurs vers l'URL de l'équilibreur de charge d'application.

Remarque : vous pouvez obtenir l'URL à partir de la section de sortie de la pile CloudFormation ou des équilibreurs de charge de la console Amazon EC2.

EC2 > Équilibreur de charge > Équilibreur de charge > CodeD-Appli-1J2O313XX68ZQ > Nom du DNS

    CodeD-Appli-1J2O313XX68ZQ-923106434.us-east-1.elb.amazonaws.com

http://coded-appli-1j2o313xx68zq-923106434.us-east-1.elb.amazonaws.com:8080/EmergencyResponderSubsystem/


## Facultatif – Automatisez le déploiement sur Git Push
Le workflow peut être automatisé en modifiant la ligne de code suivante dans votre fichier .github/workflow/deploy.yml.

De

    workflow_dispatch : {}

À

    #workflow_dispatch: {}
    push:
        branches: [ main ]
    pull_request:


Cela sera interprété par les actions GitHub pour exécuter automatiquement les workflows à chaque requête push ou pull effectuée sur la branche principale.

Après avoir testé manuellement le flux de bout en bout, vous pouvez activer le déploiement automatisé.


## Nettoyer

Pour éviter des modifications futures, vous devez nettoyer les ressources que vous avez créées.

    1. Videz le bucket Amazon S3
    2. Supprimez la pile CloudFormation (CodeDeployStack) de la console AWS
    3. Supprimer le secret GitHub ('IAMROLE_GITHUB')
        1. Accédez aux paramètres du référentiel sur la page GitHub.
        2. Sélectionnez Secrets sous Actions.
        3. Sélectionnez IAMROLE_GITHUB et supprimez-le.


## Security

See [CONTRIBUTING](CONTRIBUTING.md#security-issue-notifications) for more information.

## Références

https://aws.amazon.com/fr/blogs/devops/integrating-with-github-actions-ci-cd-pipeline-to-deploy-a-web-app-to-amazon-ec2/

https://docs-aws-amazon-com.translate.goog/codedeploy/latest/userguide/integrations-partners-github.html?_x_tr_sl=auto&_x_tr_tl=fr&_x_tr_hl=fr&_x_tr_pto=op,wapp

## License

This library is licensed under the MIT-0 License. See the LICENSE file.


## TODO
```
## GitActionsDeployToAWS/.github/workflows/deploy.yml
## GitActionsDeployToAWS/emergency-responder-subsystem/projectDescription:name
spring-boot-hello-world-example
emergency-responder-subsystem

## GitActionsDeployToAWS/.github/workflows/deploy.yml
## GitActionsDeployToAWS/cloudformation/template.yaml
CodeDeployAppNameWithASG
CodeDeployEmergencyResponderWithASG
CodeDeployERSWithASG


CodeDeployGroupName
CodeDeployGroupEmergencyResponder
CodeDeployGroupERS

## GitActionsDeployToAWS/aws/scripts/after-install.sh
## GitActionsDeployToAWS/cloudformation/template.yaml : Outputs : WebappUrl : Value
## GitActionsDeployToAWS/emergency-responder-subsystem/pom.xml : project:artifactId & name & warName
## GitActionsDeployToAWS/emergency-responder-subsystem/EmergencyResponderSubsystem.java
SpringBootHelloWorldExampleApplication
EmergencyResponderSubsystem
ers
```











