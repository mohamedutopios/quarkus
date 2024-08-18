## En local il faut démarrer un cluster k8s avec kind
# kind create cluster --name formation --config kind/config.yml

docker build -f ./../Dockerfile.native -t demo-quarkus ./../.

## Après la construction des images docker, => il faut que les images soient accessible pour le cluster.
kind load docker-image demo-quarkus --name formation # cette commande permet de copier l'image dans chaque noeud du cluster kind.

kubectl apply -f kubernetes.yml

## En local on renvoie le port 8080 vers le service kubernetes demo
kubectl port-forward service/demo 8080:80
