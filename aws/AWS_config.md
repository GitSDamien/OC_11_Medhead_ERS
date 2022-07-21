

## ECR > Repositories

    636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-api
     
    636384929310.dkr.ecr.us-east-1.amazonaws.com/ers-gateway



## ESC > Cluster > EC2 Linux + Mise en réseau

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



## ECS > Définitions de tâches > EC2

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



## EC2 > Load balancers > Application Load Balancer

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


## ECS > Clusters > ERSCluster > Services

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


