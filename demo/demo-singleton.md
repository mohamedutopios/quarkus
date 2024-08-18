Pour démontrer l'intérêt du singleton de manière simple et visible, vous pouvez créer un service qui maintient un compteur global partagé par plusieurs endpoints. Chaque fois que l'un de ces endpoints est appelé, le compteur est incrémenté. Grâce à l'annotation `@Singleton`, vous pouvez montrer que le même compteur est partagé entre tous les endpoints, prouvant ainsi que le service est unique dans toute l'application.

### Exemple simple avec Quarkus

#### Étape 1 : Créer un service `CounterService` en tant que singleton

```java
import javax.inject.Singleton;

@Singleton
public class CounterService {
    private int counter = 0;

    public int incrementAndGet() {
        counter++;
        return counter;
    }

    public int getCounter() {
        return counter;
    }
}
```

- **Explication :** Ce service `CounterService` est annoté avec `@Singleton`, garantissant qu'il y a une seule instance de ce service dans l'application. Le compteur `counter` est partagé par toutes les instances où le service est injecté.

#### Étape 2 : Créer deux ressources REST qui utilisent ce service

```java
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resource1")
public class Resource1 {

    @Inject
    CounterService counterService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String incrementAndGet() {
        return "Resource 1: Counter = " + counterService.incrementAndGet();
    }
}

@Path("/resource2")
public class Resource2 {

    @Inject
    CounterService counterService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String incrementAndGet() {
        return "Resource 2: Counter = " + counterService.incrementAndGet();
    }
}
```

- **Explication :** Ces deux ressources (`Resource1` et `Resource2`) injectent toutes deux le `CounterService`. Lorsque l'une ou l'autre est appelée, elle incrémente le compteur global.

#### Étape 3 : Tester l'application

- Démarrez l'application Quarkus.
- **Appelez l'endpoint `Resource1` :**
  - URL : `http://localhost:8080/resource1`
  - Réponse attendue : `Resource 1: Counter = 1`
- **Appelez l'endpoint `Resource2` :**
  - URL : `http://localhost:8080/resource2`
  - Réponse attendue : `Resource 2: Counter = 2`
- **Appelez à nouveau l'endpoint `Resource1` :**
  - URL : `http://localhost:8080/resource1`
  - Réponse attendue : `Resource 1: Counter = 3`

### Ce que cela démontre

1. **Partage du compteur global :** Le compteur est partagé entre les deux ressources grâce au singleton. Chaque fois que vous accédez à l'une des ressources, le compteur est incrémenté, peu importe quelle ressource est appelée. Cela montre que `CounterService` est unique et partagé entre tous les consommateurs.

2. **Effet visible et immédiat :** Le fait que les deux endpoints modifient le même compteur prouve que le service est singleton. Si `CounterService` n'était pas un singleton, chaque ressource aurait son propre compteur, et les valeurs retournées seraient indépendantes.

Cet exemple simple et direct montre clairement l'intérêt d'utiliser un singleton pour partager un état ou des ressources dans toute l'application.