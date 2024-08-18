Pour comprendre la différence entre `@ApplicationScoped` et `@RequestScoped`, ainsi que l'intérêt de chacun, voyons un exemple concret où ces deux annotations sont utilisées pour gérer l'état dans une application web.

### Scénario

Imaginons une application web où :
1. **`@ApplicationScoped`** est utilisé pour maintenir un état global partagé entre toutes les sessions et requêtes.
2. **`@RequestScoped`** est utilisé pour maintenir un état spécifique à chaque requête HTTP.

Nous allons implémenter deux services :
- Un service global qui garde la trace du nombre total de requêtes effectuées sur l'application.
- Un service qui garde la trace du nombre de requêtes effectuées uniquement pour une requête HTTP spécifique.

### Étape 1 : Créer le service global avec `@ApplicationScoped`

```java
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GlobalRequestCounterService {
    private int globalCounter = 0;

    public void increment() {
        globalCounter++;
    }

    public int getCount() {
        return globalCounter;
    }
}
```

- **Explication :** `@ApplicationScoped` garantit qu'une seule instance de `GlobalRequestCounterService` est créée pour toute la durée de vie de l'application. Le compteur `globalCounter` est partagé entre toutes les requêtes et sessions, ce qui signifie que chaque fois qu'une requête est effectuée, le compteur global est incrémenté.

### Étape 2 : Créer le service de requête avec `@RequestScoped`

```java
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestCounterService {
    private int requestCounter = 0;

    public void increment() {
        requestCounter++;
    }

    public int getCount() {
        return requestCounter;
    }
}
```

- **Explication :** `@RequestScoped` garantit qu'une nouvelle instance de `RequestCounterService` est créée pour chaque requête HTTP. Le compteur `requestCounter` est unique à chaque requête et est réinitialisé après la fin de la requête.

### Étape 3 : Créer une ressource REST qui utilise ces services

```java
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/counter")
public class CounterResource {

    @Inject
    GlobalRequestCounterService globalRequestCounterService;

    @Inject
    RequestCounterService requestCounterService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String count() {
        globalRequestCounterService.increment();
        requestCounterService.increment();

        return "Global Count: " + globalRequestCounterService.getCount() +
               "\nRequest Count: " + requestCounterService.getCount();
    }
}
```

- **Explication :**
  - **GlobalRequestCounterService** : Chaque fois que l'endpoint `/counter` est appelé, le compteur global est incrémenté. Ce compteur est partagé entre toutes les requêtes.
  - **RequestCounterService** : Ce compteur est également incrémenté, mais il est unique à la requête en cours et est réinitialisé pour chaque nouvelle requête.

### Étape 4 : Tester l'application

1. **Démarrez votre application Quarkus.**
2. **Appeler l'endpoint `/counter` plusieurs fois :**
   - **Première requête :**
     - URL : `http://localhost:8080/counter`
     - Réponse attendue :
       ```
       Global Count: 1
       Request Count: 1
       ```
   - **Deuxième requête :**
     - URL : `http://localhost:8080/counter`
     - Réponse attendue :
       ```
       Global Count: 2
       Request Count: 1
       ```
   - **Troisième requête :**
     - URL : `http://localhost:8080/counter`
     - Réponse attendue :
       ```
       Global Count: 3
       Request Count: 1
       ```

### Ce que cela démontre

1. **`@ApplicationScoped` :**
   - **Partage global :** Le compteur global (`Global Count`) est partagé entre toutes les requêtes. Chaque fois que l'endpoint est appelé, le même compteur est utilisé, ce qui permet de suivre le nombre total de requêtes effectuées sur l'application.
   - **État persistant :** L'état est persistant tant que l'application est en cours d'exécution. Si vous arrêtez et redémarrez l'application, le compteur serait réinitialisé, mais pas entre les requêtes.

2. **`@RequestScoped` :**
   - **État de requête unique :** Le compteur de requête (`Request Count`) est unique à chaque requête. Même si vous appelez l'endpoint plusieurs fois, ce compteur est réinitialisé à chaque nouvelle requête.
   - **Pas de partage :** Le compteur de requête ne se partage pas entre différentes requêtes. Chaque requête a sa propre instance du service `RequestCounterService`.

### Conclusion

Cet exemple montre clairement la différence entre `@ApplicationScoped` et `@RequestScoped` :
- **`@ApplicationScoped`** est utilisé pour partager un état global entre toutes les requêtes de l'application.
- **`@RequestScoped`** est utilisé pour gérer un état spécifique à une seule requête HTTP, garantissant que chaque requête a sa propre instance indépendante du service.

Ces annotations sont essentielles pour contrôler le cycle de vie des objets dans une application Quarkus, permettant de choisir le bon niveau de partage ou d'isolation selon les besoins.