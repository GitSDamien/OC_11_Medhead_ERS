
# OC_11_Medhead_ERS

## Test sur environnement Docker Compose

    docker build ./ers-api/. -t ers-api
    docker run -d -p 8081:8081 ers-api
    ...

    docker-compose build

    docker-compose up -d

    curl -H "Content-Type: application/json" 
        -H "gatewayKey: 343C-ED0B-4137-B27E" 
        -X POST -d '{
            "latitude": 43.657554510261534, 
            "longitude": 7.049383456590217, 
            "specs": [21, 54]
        }' http://localhost/api/v1/BedAvailability
    
    docker-compose down


## Environnement Docker
CF. [./ers/readme.md](./ers/readme.md)

Avec [docker-compose.yml](./docker-compose.yml) nous démarrons 3 conteneurs :
- Tomcat contenant notre application (port 8080)
- MySQL Server (port 3306)
- PHPMyAdmin (port 8081)


## Environnement AWS
CF. [./aws/readme.md](./aws/readme.md)

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

## CI/CD GitHub Actions
CF. [./aws/readme.md](./aws/readme.md)

### Cycle de vie

- Clone / Fork repository
- Create branch
- Perform changes
- Commit
    - GitHub Action : Build Java with Maven 
- Create a Pull Request
    - Pull Request Validation and merge into "main" branch 
    - GitHub Action : Deploy to AWS

