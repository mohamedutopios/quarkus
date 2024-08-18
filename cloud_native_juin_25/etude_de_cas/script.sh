### Phase 0 
    ## 1. Modification des applications pour une utilisation de la configuration par variables d'environnement
    ## 2. Fournir la configuration aux modules de déploiements (quarkus kubernetes)
### Phase 1 Construction des images en natives avec Docker.
## Création de l'image author 
docker build -f deploy/Dockerfile.native -t author-microservice-image ./author/. 

## Création de l'image quote 
docker build -f deploy/Dockerfile.native -t quote-microservice-image ./quote/. 

### Phase 2 Modification des ressources kubernetes générées par le module (quarkus kubernetes)

### Resource kubernetes
cd author
mvnw install
cp target/kubernetes/kubernetes.yml ./../deploy/author-k8s.yml

cd ../quote

./quote/mvnw install
mvnw install
cp target/kubernetes/kubernetes.yml ./../deploy/quote-k8s.yml

cd ..

### Phase 3 vérification de la disponibilité d'un cluster kubernetes (localement kind)

## création du cluster si ce n'est pas déjà fait.

kind create cluster --name formation --config deploy/kind.yml

kind load docker-image author-microservice-image --name formation
kind load docker-image quote-microservice-image --name formation

### Phase 4 Déployer l'application sur notre cluster

kubectl apply -f deploy/author-k8s.yml
kubectl apply -f deploy/quote-k8s.yml

### Phase 5 utilisation des ports du host pour tester notre application

kubectl port-forward service/quote 8080:80
kubectl port-forward service/author 8081:80
