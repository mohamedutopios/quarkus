Pour interagir avec l'API REST que vous avez définie dans la classe `PersonneResource`, vous pouvez utiliser `curl`, un outil en ligne de commande pour effectuer des requêtes HTTP. Voici quelques exemples de commandes `curl` correspondant aux différentes opérations CRUD exposées par votre API.

### 1. **Créer une nouvelle personne (POST)**

Pour créer une nouvelle personne, utilisez la commande suivante :

```bash
curl -X POST -H "Content-Type: application/json" -d '{"nom": "Lefevre", "prenom": "Alice", "age": 22}' http://localhost:8080/personnes
```

### 2. **Lister toutes les personnes (GET)**

Pour obtenir la liste de toutes les personnes enregistrées, utilisez la commande suivante :

```bash
curl -X GET http://localhost:8080/personnes
```

### 3. **Trouver une personne par ID (GET)**

Pour trouver une personne spécifique en utilisant son `id`, utilisez :

```bash
curl -X GET http://localhost:8080/personnes/1
```

Remplacez `1` par l'ID de la personne que vous voulez rechercher.

### 4. **Trouver des personnes par nom (GET)**

Pour rechercher des personnes par leur nom, utilisez :

```bash
curl -X GET http://localhost:8080/personnes/nom/Dupont
```

Remplacez `Dupont` par le nom que vous souhaitez rechercher.

### 5. **Supprimer une personne par ID (DELETE)**

Pour supprimer une personne en utilisant son `id`, utilisez :

```bash
curl -X DELETE http://localhost:8080/personnes/1
```

Remplacez `1` par l'ID de la personne que vous souhaitez supprimer.

### 6. **Mettre à jour une personne (PUT)**

Pour mettre à jour les informations d'une personne, utilisez :

```bash
curl -X PUT -H "Content-Type: application/json" -d '{"nom": "Lefevre", "prenom": "Alice", "age": 23}' http://localhost:8080/personnes/1
```

Remplacez `1` par l'ID de la personne que vous souhaitez mettre à jour. Le corps de la requête doit contenir les nouvelles informations de la personne.

### Résumé des opérations `curl`

- **POST** `/personnes` : Crée une nouvelle personne.
- **GET** `/personnes` : Récupère la liste de toutes les personnes.
- **GET** `/personnes/{id}` : Récupère les détails d'une personne par son ID.
- **GET** `/personnes/nom/{nom}` : Récupère les personnes correspondant à un nom donné.
- **DELETE** `/personnes/{id}` : Supprime une personne par son ID.
- **PUT** `/personnes/{id}` : Met à jour les informations d'une personne par son ID.

Ces commandes `curl` vous permettront de tester les différentes fonctionnalités de votre API REST depuis la ligne de commande. Assurez-vous que votre application Quarkus est en cours d'exécution sur `localhost` et sur le port par défaut `8080` avant d'exécuter ces commandes.