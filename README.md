https://faun.pub/building-a-ci-cd-pipeline-with-github-actions-and-docker-part-1-a9d8709c31fb


act -s GITHUB_TOKEN=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

docker-compose -f docker-compose.test.yml up -d

docker-compose -f docker-compose.test.yml down






------------------

docker build . -t haidar/server

docker run -d -p 80:80 haidar/server

docker-compose build

docker-compose up -d

---------------------



ers-app
ID de clé d'accès : AWS_ACCESS_KEY_ID : AKIAZIK37MYPPMK3A5UN
Clé d'accès secrète : AWS_SECRET_ACCESS_KEY : wHucCxSuZbqVzOVa4q1uWdqc8G425W6hvIepwxYk





https://www.c-sharpcorner.com/article/deploy-a-dockerized-laravel-application-to-aws-ecs-with-ec2-instance-launch-type/


# ESC Cluster : ERSCluster

    Type d'instance                                         t2.micro
    Nombre d'instances souhaitées                           1
    Paire de clés
    ID d'AMI ECS                                            ami-040d909ea4e56f8f3
    VPC                                                     vpc-0d568555ddcb9afa8
    Sous-réseau 1                                           subnet-0ad69d27e7c69b9f0
    Association de sous-réseau 1 à une table de routage     rtbassoc-0ec0d5d76351c90eb
    Sous-réseau 2                                           subnet-0bb23097e6a0cb451
    Association de sous-réseau 2 à une table de routage     rtbassoc-012f5eb1013ab3a23
    Zones de disponibilité VPC                              us-east-1a, us-east-1b, us-east-1c, us-east-1d, us-east-1e, us-east-1f
    Groupe de sécurité                                      sg-0660e87b01d9d3c19
    Passerelle Internet                                     igw-020d7e4fbac26dc4d
    Table de routage                                        rtb-0a0cbf15d5b226c8a
    Route Amazon EC2                                        EC2Co-Publi-1LN30OW4QQBK2
    Attachement VPC Passerelle                              EC2Co-Attac-JVOVBDN5PL3A
    Configuration de lancement                              EC2ContainerService-ERSCluster-EcsInstanceLc-qtlM3k7KgJso
    Groupe Auto Scaling                                     EC2ContainerService-ERSCluster-EcsInstanceAsg-11CXBMTJ2T0O9


# ECR Créer un référentiel

636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-app
636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-gateway


# ECS Créer une définition de tâche

ERSTaskDefinition

## Ajouter un conteneur

ers-app
ers-gateway
    STARTUP DEPENDENCY ORDERING : ers-app       start
    PARAMETRES DU RESEAU : Lien : ers-app

# Create the Service of type EC2 in the cluster with the Application load balancer and attach the Task definition

ERSService
