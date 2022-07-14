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
    VPC                                                     vpc-08eb6cbf1bb19ee50
    Sous-réseau 1                                           subnet-00f74aa24a915895f
    Association de sous-réseau 1 à une table de routage     rtbassoc-01943aaf579ec6b5c
    Sous-réseau 2                                           subnet-0a1fbf980970b2c51
    Association de sous-réseau 2 à une table de routage     rtbassoc-02d1b739e232010e3
    Zones de disponibilité VPC                              us-east-1a, us-east-1b, us-east-1c, us-east-1d, us-east-1e, us-east-1f
    Groupe de sécurité                                      sg-0072da76b406a0c55
    Passerelle Internet                                     igw-0ba1db7ed0ee453dc
    Table de routage                                        rtb-08113184c89c6e185
    Route Amazon EC2                                        EC2Co-Publi-FLY16L9RHWJ3
    Attachement VPC Passerelle                              EC2Co-Attac-7T8LGUHVWZ4C
    Configuration de lancement                              EC2ContainerService-ERSCluster-EcsInstanceLc-GOoUO2hLgSq2
    Groupe Auto Scaling                                     EC2ContainerService-ERSCluster-EcsInstanceAsg-10Q1NVBOS5O4D


# ECR Créer un référentiel

636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-api
636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-gateway


# ECS Créer une définition de tâche

ERSTaskDefinition

## Ajouter un conteneur

ers-api
ers-gateway
    STARTUP DEPENDENCY ORDERING : ers-api       start
    PARAMETRES DU RESEAU : Lien : ers-api




https://us-east-1.console.aws.amazon.com/ec2/v2/home?region=us-east-1#LoadBalancers:sort=loadBalancerName

ERSLoadBalancer

https://us-east-1.console.aws.amazon.com/ec2/v2/home?region=us-east-1#CreateTargetGroup:

ERSTargetGroup


# Create the Service of type EC2 in the cluster with the Application load balancer and attach the Task definition

ERSService
