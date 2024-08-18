### Création du cluster avec le mappage des ports avec le host vers le conteneur master du cluster.
# kind create cluster --name demo-microservice --config ./deploy/kind/config-kind.yml

#build des images 
docker build -t quote-service ./quote-service/. -f ./quote-service/Dockerfile.native
docker build -t author-microservice ./author-microservice/. -f ./author-microservice/Dockerfile.native
docker build -t api_composition ./api-composition/. -f ./author-microservice/Dockerfile.native
#envoie des images dans notre cluster kind
kind load docker-image quote-service --name demo-microservice
kind load docker-image author-microservice --name demo-microservice
kind load docker-image api-composition --name demo-microservice

# création des resources kubernetes
kubectl create -f ./deploy/k8s/k8s.yml