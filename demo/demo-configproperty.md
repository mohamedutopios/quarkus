L'annotation `@ConfigProperty` dans Quarkus est utilisée pour injecter des propriétés de configuration dans vos beans. Ces propriétés peuvent être définies dans le fichier `application.properties`, ou via d'autres sources de configuration comme des variables d'environnement, des systèmes de gestion de configuration, etc.

### Étape 1 : Définir des propriétés de configuration

Ajoutez les propriétés suivantes dans votre fichier `application.properties` :

```properties
# Simple configuration properties
app.message=Hello, Quarkus!
app.version=1.0.0

# Numeric and boolean values
app.max-items=100
app.feature-enabled=true

# Default value demonstration
app.default.greeting=Hello, default user!

# Configuration with nested keys
app.nested.config.host=localhost
app.nested.config.port=8080
```

### Étape 2 : Utiliser `@ConfigProperty` pour injecter les propriétés

#### Exemple 1 : Injection de propriétés simples

```java
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppConfigService {

    @Inject
    @ConfigProperty(name = "app.message")
    String message;

    @Inject
    @ConfigProperty(name = "app.version")
    String version;

    public String getMessage() {
        return message;
    }

    public String getVersion() {
        return version;
    }
}
```

- **Explication :** Les propriétés `app.message` et `app.version` sont injectées directement dans les champs `message` et `version` du service `AppConfigService`.

#### Exemple 2 : Injection de valeurs numériques et booléennes

```java
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FeatureConfigService {

    @Inject
    @ConfigProperty(name = "app.max-items")
    int maxItems;

    @Inject
    @ConfigProperty(name = "app.feature-enabled")
    boolean featureEnabled;

    public int getMaxItems() {
        return maxItems;
    }

    public boolean isFeatureEnabled() {
        return featureEnabled;
    }
}
```

- **Explication :** Ici, des valeurs numériques (`int`) et booléennes (`boolean`) sont injectées. Quarkus convertit automatiquement les valeurs des propriétés de configuration en types appropriés.

#### Exemple 3 : Utiliser une valeur par défaut

```java
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    @Inject
    @ConfigProperty(name = "app.default.greeting", defaultValue = "Hello, World!")
    String greeting;

    public String getGreeting() {
        return greeting;
    }
}
```

- **Explication :** Si la propriété `app.default.greeting` n'est pas définie dans le fichier `application.properties`, la valeur par défaut `"Hello, World!"` sera utilisée.

#### Exemple 4 : Injection de propriétés imbriquées (Complexe)

```java
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServerConfigService {

    @Inject
    @ConfigProperty(name = "app.nested.config.host")
    String host;

    @Inject
    @ConfigProperty(name = "app.nested.config.port")
    int port;

    public String getServerDetails() {
        return "Server is running on " + host + ":" + port;
    }
}
```

- **Explication :** Les propriétés imbriquées sont injectées en spécifiant le chemin complet. Ici, `host` et `port` sont injectés à partir de `app.nested.config.host` et `app.nested.config.port`.

### Étape 3 : Utiliser ces services dans une ressource REST

```java
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/config")
public class ConfigResource {

    @Inject
    AppConfigService appConfigService;

    @Inject
    FeatureConfigService featureConfigService;

    @Inject
    GreetingService greetingService;

    @Inject
    ServerConfigService serverConfigService;

    @GET
    @Path("/message")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        return "Message: " + appConfigService.getMessage() + "\nVersion: " + appConfigService.getVersion();
    }

    @GET
    @Path("/feature")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFeatureStatus() {
        return "Max Items: " + featureConfigService.getMaxItems() +
               "\nFeature Enabled: " + featureConfigService.isFeatureEnabled();
    }

    @GET
    @Path("/greeting")
    @Produces(MediaType.TEXT_PLAIN)
    public String getGreeting() {
        return greetingService.getGreeting();
    }

    @GET
    @Path("/server")
    @Produces(MediaType.TEXT_PLAIN)
    public String getServerDetails() {
        return serverConfigService.getServerDetails();
    }
}
```

### Étape 4 : Tester l'application

1. **Démarrez votre application Quarkus.**

2. **Testez les endpoints :**
   - **`/config/message`**
     - URL : `http://localhost:8080/config/message`
     - Réponse attendue :
       ```
       Message: Hello, Quarkus!
       Version: 1.0.0
       ```
   - **`/config/feature`**
     - URL : `http://localhost:8080/config/feature`
     - Réponse attendue :
       ```
       Max Items: 100
       Feature Enabled: true
       ```
   - **`/config/greeting`**
     - URL : `http://localhost:8080/config/greeting`
     - Réponse attendue :
       ```
       Hello, default user!
       ```
   - **`/config/server`**
     - URL : `http://localhost:8080/config/server`
     - Réponse attendue :
       ```
       Server is running on localhost:8080
       ```

### Ce que cela démontre

1. **Injection de configuration** : `@ConfigProperty` injecte des valeurs directement dans vos beans à partir du fichier `application.properties`, ce qui vous permet de centraliser et de gérer facilement les configurations.

2. **Types variés** : `@ConfigProperty` supporte une variété de types de données (chaînes, nombres, booléens) et injecte ces valeurs en les convertissant automatiquement.

3. **Valeurs par défaut** : Vous pouvez spécifier des valeurs par défaut qui sont utilisées si aucune valeur n'est fournie dans le fichier de configuration.

4. **Propriétés imbriquées** : Vous pouvez organiser vos configurations sous forme de propriétés imbriquées pour une meilleure structure et lisibilité.

### Conclusion

L'annotation `@ConfigProperty` est un outil puissant dans Quarkus pour injecter des configurations dans vos services de manière simple et flexible. Cela permet de séparer les configurations de l'implémentation du code, facilitant ainsi la gestion des différentes configurations pour différents environnements (développement, test, production).