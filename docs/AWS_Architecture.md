# Pipeline CI/CD & AWS Architecture

[![Architecture AWS](./images/AWS_Architecture.png)](./docs/AWS_Architecture.md)

## Pipeline CI/CD avec GitHub Actions

1. Lancement du docker-compose up --build
    - Build de toutes les images en éxecutant les tests unitaire et d'implémentation
2. Extrait les rapports de test du projet ERS-API
3. Extrait les rapports de test du projet ERS-GATEWAY
4. Exécute les test End2End
    - Extrait les rapports de test
5. Changement des datasources et rebuild des images (ERS-API & ERS-GATEWAY)
6. Envoi des images à Amazon ECR
7. Déployement à partir d'Amazon ECS task definition
8. Migration des bases de données sur Amazon RDS


## Environnement AWS

Grâce à **AWS CloudFormation** et son fichier de configuration [template.yaml](./aws/cloudformation/template.yaml) et ses [scripts](./aws/scripts/), il devient facile de créer toutes les ressources. 

Cette pile créée les ressources suivantes :
- Une instance Amazon EC2 Linux avec le serveur Tomcat et l'agent CodeDeploy installés
- Groupe d'autoscaling avec équilibreur de charge
- Nom de l'application CodeDeploy et groupe de déploiement
- Compartiment Amazon S3 pour stocker les artefacts de build
- Fournisseur d'identité OIDC de gestion des identités et des accès (IAM)
- Profil d'instance pour Amazon EC2
- Rôle de service pour CodeDeploy
- Groupes de sécurité pour ALB et Amazon EC2

Consulter 
- AWS_cloudFormation_install.md
- AWS_cleanup.md


# ECR > Repositories

    636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-api
     
    636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-gateway



# ESC > Cluster > EC2 Linux + Mise en réseau

    ERSCluster

    Modèle de mise en service                               instance à la demande

    Taille du volume EBS racine (Gio)                       30
    VPC                                                     Créer un VPC
    Rôle IAM de l'instance de conteneur                     escInstanceRole
    
    Type d'instance                                         t2.micro
    Nombre d'instances souhaitées                           2
    Paire de clés
    ID d'AMI ECS                                            Amazon Linux 2 AMI [ami-040d909ea4e56f8f3]
    VPC                                                     vpc-00f84f3e46e96bf5c
    Sous-réseau 1                                           subnet-0d05b7aa1dd77c8b1
    Association de sous-réseau 1 à une table de routage     rtbassoc-0a14f9a8b3ac334fa
    Sous-réseau 2                                           subnet-0582301ef8f247ce0
    Association de sous-réseau 2 à une table de routage     rtbassoc-0f63880fd231e6542
    Zones de disponibilité VPC                              us-east-1a, us-east-1b, us-east-1c, us-east-1d, us-east-1e, us-east-1f
    Groupe de sécurité                                      sg-090531e9dff68c34f
    Passerelle Internet                                     igw-014170095a126cea2
    Table de routage                                        rtb-00b4bd1244662400f
    Route Amazon EC2                                        EC2Co-Publi-LX3R916C05AP
    Attachement VPC Passerelle                              EC2Co-Attac-OGSZFDD6S8KC
    Configuration de lancement                              EC2ContainerService-ERSCluster-EcsInstanceLc-XViJJ0VNHtym
    Groupe Auto Scaling                                     EC2ContainerService-ERSCluster-EcsInstanceAsg-1VKIP5R9U3A0D



# ECS > Définitions de tâches > EC2

    ERSTaskDef

    ers-api
    
        image                               636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-api
        Limites de mémoire (Mio)            128
        Mappages de ports                   8081 > 8081
        PARAMETRES DU RESEAU                
            hostname                        ers-api

    ers-gateway
        image                               636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-gateway
        Limites de mémoire (Mio)            128
        Mappages de ports                   80 > 80
        STARTUP DEPENDENCY ORDERING         ers-api > start
        PARAMETRES DU RESEAU                
            Lien                            ers-api
            hostname                        ers-gateway



# EC2 > Load balancers > Application Load Balancer

    ERSLoadBalancer

    Scheme                                  Internet-facing
    Network mapping                         
        VPC                                 vpc-00f84f3e46e96bf5c
        us-east-1a & us-east-1b
    Listeners and routing
        Listener HTTP:80

            Create target group             type > Instances
            Target group name               ERSTargetGroups
            Protocol                        HTTP
            Port                            80
    

    Nom du DNS                              ERSLoadBalancer-1669719682.us-east-1.elb.amazonaws.com


# ECS > Clusters > ERSCluster > Services

    ERSService

    Type de lancement                       EC2
    Type de service                         REPLICA
    Nombre de tâches                        2

    Type de déploiement                     Mise à jour continue

    Équilibrage de charge
        Type                                Application Load Balancer

        Conteneur faisant l'objet d'un équilibrage de charge    
            ers-gateway:80:80                   Ajouter à l'ELB
            Port de l'écouteur de production    80:HTTP
            Nom du groupe cible                 ERSTargetGroups

    Service Auto Scaling                    Ne pas ajuster le nombre souhaité du service








https://www.c-sharpcorner.com/article/deploy-a-dockerized-laravel-application-to-aws-ecs-with-ec2-instance-launch-type/
https://blog.devgenius.io/host-any-app-to-aws-and-github-using-continuous-deployment-ci-cd-pipeline-step-by-step-d4150dbee2e8







































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






## Déployer le modèle CloudFormation

    AWS CloudFormation > Créer une pile

- Spécifier un modèle : Charger un fichier de modèle > template.yaml
- Nom de la pile : `MedHeadERS`
- VPC et sous-réseaux = (ceux-ci sont pré-remplis pour vous)
- GithubRepoName : `GitSDamien/OC_11_Medhead_ERS`
- GitHubThumbprintList : `6938fd4d98bab03faadb97b34396831e3780aea1`
- Cocher "Je sais qu’AWS CloudFormation peut créer des ressources IAM avec des noms personnalisés."


## Mettre à jour le code

    AWS CloudFormation > onglet : Sorties
    
- S3 bucket name = DeploymentBucket : `medheaders-webappdeploymentbucket-xnrz1941mfuc`

- IAM Role for GitHub = GithubIAMRoleArn : `arn:aws:iam::636384929310:role/CodeDeployRoleforGitHub`


Editer le fichier 

    .github/workflows/deploy.yml

    env > S3BUCKET : medheaders-webappdeploymentbucket-xnrz1941mfuc
    env > AWS_REGION : us-east-1


Remplacer le S3 bucket dans 

    aws/scripts/after-install.sh

Git Commit


## Configurer les secrets GitHub

    Repo Git > Settings > Secrets > Actions > New repository secret

    IAMROLE_GITHUB
    arn:aws:iam::636384929310:role/CodeDeployRoleforGitHub

## Intégrer CodeDeploy avec GitHub

    AWS CodeDeploy > Déployer > Applications > Select

    Onglet Déploiement > Créer un déploiement 

- Groupe de déploiement : CodeDeployGroupERS
- Type de révision : Mon application est stockée dans GitHub

Pour créer une connexion pour les applications AWS CodeDeploy à un compte GitHub, déconnectez-vous de GitHub dans un onglet de navigateur Web distinct. Dans GitHub token name , tapez un nom pour identifier cette connexion, puis choisissez Connect to GitHub . La page Web vous invite à autoriser CodeDeploy à interagir avec GitHub pour votre application.

    Annuler le déploiement (c'était juste pour linker GitHub et AWS)


## Déclencher le flux de travail des actions GitHub

## Vérifier le déploiement

EC2 > Équilibreur de charge > Équilibreur de charge > CodeD-Appli-1J2O313XX68ZQ > Nom du DNS


http://medhe-appli-bkbhcxcb852i-875200433.us-east-1.elb.amazonaws.com:8080/ers


## Connexion au shell d'AWS

    AWS > EC2 > Instances > Instances > Cocher "medhead-ers" > Action > Se connecter > Session Manager > Se connecter


## Nettoyer

Pour éviter des modifications futures, vous devez nettoyer les ressources que vous avez créées.

    1. Videz le bucket Amazon S3
    2. Supprimez la pile CloudFormation (CodeDeployStack) de la console AWS
    3. Supprimer le secret GitHub ('IAMROLE_GITHUB')
        1. Accédez aux paramètres du référentiel sur la page GitHub.
        2. Sélectionnez Secrets sous Actions.
        3. Sélectionnez IAMROLE_GITHUB et supprimez-le.










