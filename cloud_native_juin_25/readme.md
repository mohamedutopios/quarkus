### Commande build docker 
```bash
docker build -f <nom_docker_file> -t <nom_image> <path_context> # docker build -f Dockerfile.native -t demo-quarkus .
```

### Commande run docker

```bash
docker run -p <PORTBINDING> -d <NOM_IMAGE> <RUN_COMMAND_OVERRIDE> # docker run -p 6000:8080 -d demo-quarkus
```

