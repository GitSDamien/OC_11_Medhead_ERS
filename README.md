

    docker build ./ers-api/. -t ers-api

    docker run -d -p 8081:8081 ers-api

    docker-compose build

    docker-compose up -d



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
    Nombre d'instances souhaitées                           1
    Paire de clés
    ID d'AMI ECS                                            Amazon Linux 2 AMI [ami-040d909ea4e56f8f3]
    VPC                                                     vpc-02ce01f6f90996cc3
    Sous-réseau 1                                           subnet-045b1a101d2422213
    Association de sous-réseau 1 à une table de routage     rtbassoc-0b38474d98394c3ad
    Sous-réseau 2                                           subnet-0bf5e1a24f346f668
    Association de sous-réseau 2 à une table de routage     rtbassoc-084f5c9331ef7d347
    Zones de disponibilité VPC                              us-east-1a, us-east-1b, us-east-1c, us-east-1d, us-east-1e, us-east-1f
    Groupe de sécurité                                      sg-03f6f014260968010
    Passerelle Internet                                     igw-01c38562146d73e85
    Table de routage                                        rtb-099b5d9f130038082
    Route Amazon EC2                                        EC2Co-Publi-1RBEM1PG0IZK5
    Attachement VPC Passerelle                              EC2Co-Attac-1E8NINVRNQP2S
    Configuration de lancement                              EC2ContainerService-ERSCluster-EcsInstanceLc-lKFtB3aivN9m
    Groupe Auto Scaling                                     EC2ContainerService-ERSCluster-EcsInstanceAsg-185H8H3P7XK98



# ECS > Définitions de tâches > EC2

    ERSTaskDef

    ers-api
    
        image                               636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-api
        Limites de mémoire (Mio)            128
        Mappages de ports                   80 > 80
        PARAMETRES DU RESEAU                
            hostname                        ers-api

    ers-gateway
        image                               636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-gateway
        Limites de mémoire (Mio)            128
        Mappages de ports                   8081 > 8081
        STARTUP DEPENDENCY ORDERING         ers-api > start
        PARAMETRES DU RESEAU                
            Lien                            ers-api
            hostname                        ers-gateway



# EC2 > Load balancers > Application Load Balancer > Create

    ERSLoadBalancer

    Scheme                                  Internet-facing
    Network mapping                         
        VPC                                 vpc-02ce01f6f90996cc3
        us-east-1a & us-east-1b
    Listeners and routing
        Listener HTTP:80

            Create target group             type > Instances
            Target group name               ERSTargetGroup
            Protocol                        HTTP
            Port                            80
    

    Nom du DNS                              ERSLoadBalancer-341792278.us-east-1.elb.amazonaws.com


# ECS > Clusters > ERSCluster > Services > Create

    ERSService

    Type de lancement                       EC2
    Type de service                         REPLICA
    Nombre de tâches                        2

    Type de déploiement                     Mise à jour continue

    Équilibrage de charge
        Type                                Application Load Balancer

        Conteneur faisant l'objet d'un équilibrage de charge    
            ers-api:80:80                       Ajouter à l'ELB
            Port de l'écouteur de production    80:HTTP
            Nom du groupe cible                 ERSTargetGroup

    Service Auto Scaling                    Ne pas ajuster le nombre souhaité du service

    




# Create the Service of type EC2 in the cluster with the Application load balancer and attach the Task definition

ERSService






https://www.c-sharpcorner.com/article/deploy-a-dockerized-laravel-application-to-aws-ecs-with-ec2-instance-launch-type/
