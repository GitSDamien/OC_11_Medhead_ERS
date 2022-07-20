# Environnement Docker

Tout notre environnement de développement est contenu dans le fichier [docker-compose.yml](../docker-compose.yml). Il est essentiel lors de notre CI/CD. Les tests unitaires et d'intégrations sont lancés au build des images.

![Architecture logicielle](./images/Docker_Environnement.png)

## Démarrer l'environnement
    docker-compose up -d --build

## Test End 2 End
    docker exec test-e2e mvn -B test
    
