# ERS-API

 Fonction principale du projet (CF. document Hypothèse de validation de principe). 
 
 Le sous-système d'intervention d'urgence en temps réel est destiné à recevoir une ou plusieurs spécialités médicales (voir les Données de référence sur les spécialités) et une banque de données d'informations récentes sur les hôpitaux afin de suggérer l'hôpital le plus proche offrant un lit disponible, associé à une ou plusieurs spécialisations correspondantes. Le lieu de l'incident d'urgence doit également être fourni.



## Commandes utiles

    mvn -B -f ./ers-api/ test

    mvn  verify -Djacoco.destFile=target/exportJacoco/jacoco-fast.exec
    