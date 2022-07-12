# Getting Started

AIzaSyAd-1j2dU3SdDn8HivnYA5TVsiCgfGQW0g

## Vérifier la version du build
    
    CF. ers > pom.xml > version


## Dev, Build & Test sur environnement dev
    
    ...


## Test sur environnement Docker Compose

    docker build -t ers-api ./ers-api

    docker build -t ers-gateway ./ers-gateway

    docker build -t ers-redis ./ers-redis

    docker build -t fake-api-hospital ./fake-api-hospital
    
    docker-compose up -d


    curl http://localhost/NHSSpec

    curl -H "Content-Type: application/json" -X POST -d '{"latitude": 43.657554510261534, "longitude": 7.049383456590217}' http://localhost/BedAvailability
    
    docker-compose down








