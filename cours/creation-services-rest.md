### Création de Services RESTful avec JAX-RS dans Quarkus

JAX-RS (Java API for RESTful Web Services) est une API standardisée pour créer des services web REST en Java. Quarkus intègre JAX-RS pour faciliter la création de services RESTful. Voici un guide étape par étape pour créer un service RESTful avec JAX-RS dans Quarkus.

#### **1. Configuration de base**

Avant de commencer à coder, assurez-vous que votre projet est configuré pour utiliser JAX-RS. Lorsque vous créez un projet Quarkus, l'extension RESTEasy (implémentation JAX-RS par défaut dans Quarkus) est généralement incluse par défaut. Si ce n'est pas le cas, vous pouvez l'ajouter via Maven.

##### **Ajout de l'extension RESTEasy :**

Si l'extension RESTEasy n'est pas encore ajoutée, vous pouvez l'ajouter au fichier `pom.xml` :

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-resteasy</artifactId>
</dependency>
```

#### **2. Création d'une ressource REST**

Une ressource REST dans JAX-RS est une classe Java annotée qui expose des méthodes HTTP (GET, POST, PUT, DELETE) pour répondre aux requêtes clients.

##### **Exemple de classe de ressource REST :**

```java
package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class GreetingResource {

    // Méthode GET pour récupérer des informations
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello, Quarkus!";
    }

    // Méthode POST pour créer de nouvelles ressources
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGreeting(Greeting greeting) {
        // Logique pour créer une nouvelle ressource
        return Response.ok(greeting).status(201).build();
    }

    // Méthode PUT pour mettre à jour des ressources existantes
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGreeting(@PathParam("id") Long id, Greeting greeting) {
        // Logique pour mettre à jour une ressource
        return Response.ok(greeting).build();
    }

    // Méthode DELETE pour supprimer des ressources
    @DELETE
    @Path("/{id}")
    public Response deleteGreeting(@PathParam("id") Long id) {
        // Logique pour supprimer une ressource
        return Response.noContent().build();
    }
}
```

#### **3. Explication des annotations JAX-RS**

- **`@Path("/hello")` :** Spécifie le chemin relatif pour accéder à cette ressource. Par exemple, cette ressource sera accessible via `http://localhost:8080/hello`.

- **`@GET`, `@POST`, `@PUT`, `@DELETE` :** Annotations pour indiquer le type de requête HTTP que la méthode gère. 
  - `@GET` : Utilisé pour lire des informations.
  - `@POST` : Utilisé pour créer de nouvelles ressources.
  - `@PUT` : Utilisé pour mettre à jour des ressources existantes.
  - `@DELETE` : Utilisé pour supprimer des ressources.

- **`@Produces(MediaType.TEXT_PLAIN)` et `@Consumes(MediaType.APPLICATION_JSON)` :**
  - `@Produces` : Indique le type MIME (ou Content-Type) de la réponse. Par exemple, `MediaType.TEXT_PLAIN` signifie que la réponse sera du texte brut.
  - `@Consumes` : Indique le type MIME que la méthode peut accepter dans la requête. `MediaType.APPLICATION_JSON` signifie que la méthode accepte des données au format JSON.

- **`@PathParam` :** Utilisé pour extraire des variables du chemin d'URL. Par exemple, `@PathParam("id") Long id` extrait la valeur de `{id}` de l'URL `http://localhost:8080/hello/{id}`.

#### **4. Définir des entités pour les données**

Lors de la création de services RESTful, il est courant de travailler avec des entités qui représentent les données transférées. Par exemple, une classe `Greeting` peut être utilisée pour représenter un message de salutation :

```java
package org.acme;

public class Greeting {

    private Long id;
    private String message;

    // Constructeurs, getters, setters

    public Greeting() {
    }

    public Greeting(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```

#### **5. Test de l'API RESTful**

Une fois la ressource créée, vous pouvez tester l'API en démarrant l'application en mode développement :

```bash
./mvnw quarkus:dev
```

Accédez ensuite à l'API à l'aide de votre navigateur ou d'un outil comme `curl` ou [Postman](https://www.postman.com/).

##### **Exemples de requêtes :**

- **GET :** Pour récupérer un message de salutation :
  ```bash
  curl -X GET http://localhost:8080/hello
  ```

- **POST :** Pour créer une nouvelle ressource :
  ```bash
  curl -X POST http://localhost:8080/hello -H "Content-Type: application/json" -d '{"id":1,"message":"Hello, REST!"}'
  ```

- **PUT :** Pour mettre à jour une ressource existante :
  ```bash
  curl -X PUT http://localhost:8080/hello/1 -H "Content-Type: application/json" -d '{"id":1,"message":"Hello, Updated REST!"}'
  ```

- **DELETE :** Pour supprimer une ressource :
  ```bash
  curl -X DELETE http://localhost:8080/hello/1
  ```

#### **6. Gestion des erreurs et des réponses personnalisées**

Vous pouvez personnaliser les réponses HTTP en utilisant la classe `Response` de JAX-RS, comme montré dans l'exemple précédent. Cela vous permet de définir les codes de statut, les en-têtes, et les corps de réponse.

##### **Exemple de réponse personnalisée :**

```java
@GET
@Path("/greet/{name}")
@Produces(MediaType.TEXT_PLAIN)
public Response greet(@PathParam("name") String name) {
    if (name == null || name.isEmpty()) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity("Name parameter is missing")
                       .build();
    }
    return Response.ok("Hello, " + name + "!").build();
}
```

### Conclusion

Avec JAX-RS dans Quarkus, vous pouvez facilement créer des services RESTful robustes et flexibles. La combinaison de JAX-RS et de Quarkus permet une intégration fluide avec d'autres technologies et un déploiement optimisé pour des applications natives dans le cloud.