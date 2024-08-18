## Docker build image
docker build -f ./../Dockerfile.native -t contact-app-image ./../.

kind load docker-image contact-app-image --name formation

kubectl apply -f kubernetes.yml

kubectl port-forward service/contactapp 8082:80