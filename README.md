# RSO: Image metadata microservice

## Prerequisites

```bash
docker run -d --name pg-image-metadata -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=client-metadata -p 5432:5432 postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar client-catalog-api-1.0.0-SNAPSHOT.jar
```
Available at: localhost:8080/v1/client


## Docker commands
```bash
docker build -t novaslika .   
docker images
docker run novaslika    
docker tag novaslika prporso/novaslika   
docker push prporso/novaslika
docker ps
```

## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl create -f client-deployment.yaml 
kubectl apply -f client-deployment.yaml 
kubectl get services 
kubectl get deployments
kubectl get pods
kubectl logs client-deployment-6f59c5d96c-rjz46
kubectl delete pod client-deployment-6f59c5d96c-rjz46
```
Secrets: https://kubernetes.io/docs/concepts/configuration/secret/

