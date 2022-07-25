

# Pipeline CI/CD avec GitHub Actions

![Pipeline CI/CD](./images/GitHubActions_pipeline.png)

## GitHub
Seul la branche "main" actionne la pipeline Ci/CD, nous pouvons donc avoir d'autres branches complement indépendantes.
Il est possible faire un "fork" sur le projet pour avoir le template du projet.

## GitHub Actions

1. Lancement du docker-compose up --build
    - Build de toutes les images en éxecutant les tests unitaire et d'implémentation
2. Extrait les rapports de test du projet ERS-API
3. Extrait les rapports de test du projet ERS-GATEWAY
4. Exécute le test de migration des données
5. Exécute les test End2End
    - Extrait les rapports de test
6. Changement des datasources et rebuild des images (ERS-API & ERS-GATEWAY)
7. Envoi des images à Amazon ECR (ERS-API & ERS-GATEWAY)
8. Déployement à partir d'Amazon ECS task definition
9. Migration des bases de données sur Amazon RDS
10. Envoi de notifications sur un canal Slack

<br>

Retrouver l'intégralité de chaque workflow passés dans la pipeline sur https://github.com/GitSDamien/test2/actions

[<img src="./images/GitHubActions_workflows.png" alt="GitHubActions_workflows" width="200"/>](https://github.com/GitSDamien/test2/actions)


Accèder aux rapports de test

[<img src="./images/GitHubActions_pipelineDetails.png" alt="GitHubActions_pipelineDetails" width="200"/>](./images/GitHubActions_pipelineDetails.png)
[<img src="./images/GitHubActions_testResults.png" alt="GitHubActions_testResults" width="200"/>](./images/GitHubActions_testResults.png)


<br>
<br>


## Veuillez consulter 
- [pipeline.yml](../.github/workflows/pipeline.yml)

