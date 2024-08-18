### Injection de Dépendances avec CDI (Contexts and Dependency Injection) dans Quarkus

CDI (Contexts and Dependency Injection) est une spécification Java qui permet la gestion des dépendances de manière flexible et modulaire. CDI est intégré dans Quarkus, facilitant l'injection de dépendances et le développement d'applications Java basées sur des composants.

Voici comment utiliser CDI dans Quarkus pour gérer les dépendances dans vos applications.

#### **1. Concepts de Base de CDI**

- **Injection de Dépendances :** Permet de fournir des instances de classes directement aux composants qui en ont besoin, sans que ces composants n'aient à gérer la création ou la gestion de ces instances.
- **Bean :** Une classe gérée par CDI qui peut être injectée dans d'autres composants.
- **Scope (Portée) :** Détermine la durée de vie d'un bean. Les scopes courants incluent `@ApplicationScoped`, `@RequestScoped`, et `@Singleton`.

#### **2. Configuration CDI dans Quarkus**

Quarkus utilise CDI par défaut, il n'y a donc généralement aucune configuration supplémentaire requise pour commencer à utiliser l'injection de dépendances.

##### **Exemple de Classe avec CDI :**

Voici un exemple simple montrant comment créer une classe service et l'injecter dans une ressource REST.

1. **Création d'une Classe de Service (Bean CDI) :**

```java
package org.acme;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    public String greet(String name) {
        return "Hello, " + name;
    }
}
```

Dans cet exemple :
- La classe `GreetingService` est annotée avec `@ApplicationScoped`, ce qui signifie que Quarkus crée une instance unique de cette classe pour toute la durée de vie de l'application.

2. **Injection de la Dépendance dans une Ressource REST :**

```java
package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/greet")
public class GreetingResource {

    @Inject
    GreetingService greetingService;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String greet(String name) {
        return greetingService.greet(name);
    }
}
```

Dans cet exemple :
- `@Inject` est utilisé pour injecter une instance de `GreetingService` dans la ressource `GreetingResource`.
- Lorsque la méthode `greet` est appelée, elle utilise le service injecté pour générer la salutation.

#### **3. Scopes CDI**

Les scopes déterminent la durée de vie et la portée des beans CDI. Voici quelques scopes couramment utilisés :

- **`@ApplicationScoped` :**
  - La portée est sur l'ensemble de l'application.
  - Un bean `@ApplicationScoped` est partagé entre tous les utilisateurs et toutes les sessions.

- **`@RequestScoped` :**
  - La portée est limitée à une seule requête HTTP.
  - Un bean `@RequestScoped` est créé au début d'une requête et détruit à la fin de celle-ci.

- **`@Singleton` :**
  - Un bean `@Singleton` est similaire à `@ApplicationScoped`, mais il est géré par la JVM elle-même et est également accessible en dehors du contexte CDI.

- **`@SessionScoped` :**
  - Utilisé pour maintenir un état spécifique à la session d'un utilisateur dans des applications web.

##### **Exemple avec `@RequestScoped` :**

```java
package org.acme;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestScopedService {

    public String processRequest() {
        return "Processed request at " + System.currentTimeMillis();
    }
}
```

#### **4. Producteurs et Qualifieurs**

**Producteurs :** CDI permet également de créer des beans de manière plus flexible en utilisant des producteurs. Un producteur est une méthode annotée avec `@Produces` qui fournit des objets CDI.

##### **Exemple de Producteur :**

```java
package org.acme;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class GreetingProducer {

    @Produces
    @Named("customGreeting")
    public String customGreeting() {
        return "Hello from a producer!";
    }
}
```

- Ici, la méthode `customGreeting` produit un objet `String` qui peut être injecté ailleurs avec l'annotation `@Named("customGreeting")`.

**Qualifieurs :** Les qualifieurs permettent de différencier les implémentations de beans injectables. Ils sont utiles lorsque vous avez plusieurs implémentations d'une même interface.

##### **Exemple de Qualifieur :**

```java
package org.acme;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, METHOD})
public @interface SpecialGreeting {
}
```

- Utilisez ce qualifieur pour annoter différentes implémentations et les différencier lors de l'injection.

#### **5. Injection dans des Composants Non-Beans**

Dans certains cas, vous pourriez vouloir injecter des dépendances dans des classes qui ne sont pas des beans CDI. Cela peut être fait en utilisant le programmeur CDI directement.

##### **Exemple :**

```java
package org.acme;

import javax.enterprise.inject.spi.CDI;

public class SomeComponent {

    public void execute() {
        GreetingService greetingService = CDI.current().select(GreetingService.class).get();
        System.out.println(greetingService.greet("John Doe"));
    }
}
```

#### **6. Gestion des Intercepteurs et Décorateurs**

CDI supporte également les intercepteurs (`@Interceptor`) et les décorateurs (`@Decorator`) qui permettent d'ajouter dynamiquement des comportements aux beans sans modifier leur code source.

##### **Exemple d'Intercepteur :**

```java
package org.acme;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logging
@Interceptor
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        System.out.println("Method " + context.getMethod().getName() + " is being called");
        return context.proceed();
    }
}
```

- Ici, l'intercepteur `LoggingInterceptor` intercepte les appels aux méthodes annotées avec `@Logging` pour ajouter une fonctionnalité de logging.

### Conclusion

CDI dans Quarkus permet une gestion puissante et flexible des dépendances dans vos applications Java. En comprenant et en utilisant les concepts de base tels que l'injection de dépendances, les scopes, les producteurs et les intercepteurs, vous pouvez créer des applications modulaires, testables, et maintenables.