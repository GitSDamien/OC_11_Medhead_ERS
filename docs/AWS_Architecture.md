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
6. Envoi des images à Amazon ECR (ERS-API & ERS-GATEWAY)
7. Déployement à partir d'Amazon ECS task definition
8. Migration des bases de données sur Amazon RDS

<br>

Retrouver l'intégralité de chaque workflow passés dans la pipeline sur https://github.com/GitSDamien/test2/actions

[<img src="./images/GitHubActions_workflows.png" alt="GitHubActions_workflows" width="200"/>](https://github.com/GitSDamien/test2/actions)


Accèder aux rapports de test

[<img src="./images/GitHubActions_pipelineDetails.png" alt="GitHubActions_pipelineDetails" width="200"/>](./images/GitHubActions_pipelineDetails.png)
[<img src="./images/GitHubActions_testResults.png" alt="GitHubActions_testResults" width="200"/>](./images/GitHubActions_testResults.png)


<br>
<br>


## Vue d'ensemble de la solution

La solution utilise les services suivants :

- [GitHub Actions](https://docs.github.com/en/actions) : Outil d'orchestration de workflow qui hébergera la Pipeline ([pipeline.yml](../.github/workflows/pipeline.yml)).
- [AWS Auto Scaling](https://aws.amazon.com/ec2/autoscaling/) : Service AWS pour aider à maintenir la disponibilité et l'élasticité des applications en ajoutant ou supprimant automatiquement des instances EC2.
- [Amazon ECR](https://aws.amazon.com/fr/ecr/) : Registre de conteneurs entièrement géré qui offre un hébergement haute performance, afin que vous puissiez déployer de manière fiable des images d'applications et des artefacts n'importe où.
- [Amazon EC2](https://docs.aws.amazon.com/ec2/index.html?nc2=h_ql_doc_ec2#amazon-ec2) : Serveur de calcul pour le déploiement de l'application.
- [Amazon ECS](https://aws.amazon.com/fr/ecs/) : Service d'orchestration de conteneurs entièrement géré qui vous permet de déployer, de gérer et de mettre à l'échelle des applications conteneurisées en toute simplicité.
- [AWS CloudFormation](https://aws.amazon.com/cloudformation/) : Service AWS d'infrastructure en tant que code (IaC) utilisé pour faire tourner l'infrastructure initiale côté AWS.
- [IAM OIDC identity provider](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_roles_providers_create_oidc.html) : Service d'authentification fédérée pour établir la confiance entre GitHub et AWS afin de permettre aux actions GitHub de se déployer sur AWS sans conserver les secrets et les informations d'identification AWS.
- [Amazon RDS](https://aws.amazon.com/fr/rds) : Ensemble de services gérés qui facilite la configuration, l'utilisation et la mise à l'échelle des bases de données dans le cloud. Moteur utiisé : MySQL.


Grâce à **AWS CloudFormation** et son fichier de configuration [cloudFormation_template.yaml](../aws/cloudFormation_template.yaml) et ses [scripts](../aws/scripts/), il devient facile de créer toutes les ressources. 

Lors du déployement c'est le fichier [task-definition.json](../aws/) qui entre en jeu et positionne les images contenues sur l'ECR dans les bons compartiments et groupe d'auto-scaling.


## Veuillez consulter 
- [AWS_cloudFormation_install.md](./AWS_cloudFormation_install.md)
- [AWS_cleanup.md](./AWS_cleanup.md)
- [AWS_config.md](../aws/AWS_config.md)
- [cloudFormation_template.yaml](../aws/cloudFormation_template.yaml)
- [pipeline.yml](../.github/workflows/pipeline.yml)








































