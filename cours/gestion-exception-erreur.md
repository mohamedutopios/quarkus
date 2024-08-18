La gestion des erreurs et des exceptions dans Quarkus est un aspect crucial pour assurer la robustesse et la stabilité des applications. Quarkus, en tant que framework Java pour les microservices, propose plusieurs moyens pour gérer les erreurs et les exceptions efficacement.

### 1. Gestion des Exceptions dans les REST Endpoints

Quarkus utilise **JAX-RS** pour la gestion des API RESTful. Voici les principales méthodes pour gérer les exceptions dans les endpoints REST.

#### 1.1. Utilisation de `@ExceptionMapper`
Une manière élégante de gérer les exceptions dans Quarkus est d'utiliser les `ExceptionMapper`. Un `ExceptionMapper` permet de mapper une exception à une réponse HTTP spécifique.

Exemple :

```java
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {

    @Override
    public Response toResponse(CustomException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(new ErrorResponse("Custom error occurred", exception.getMessage()))
                       .build();
    }
}

class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}

class ErrorResponse {
    private String error;
    private String message;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    // getters and setters
}
```

Dans cet exemple, toutes les `CustomException` levées dans l'application seront capturées par `CustomExceptionMapper` et une réponse HTTP 400 (Bad Request) sera renvoyée avec un message d'erreur personnalisé.

#### 1.2. Utilisation de `@ServerExceptionMapper`
`@ServerExceptionMapper` est une annotation spécifique à Quarkus qui permet de gérer les exceptions au niveau du serveur. Elle est particulièrement utile pour capter les exceptions non gérées.

Exemple :

```java
import io.quarkus.security.ForbiddenException;
import io.quarkus.vertx.http.runtime.exceptions.HttpException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler {

    @ServerExceptionMapper
    public Response handleHttpException(HttpException exception) {
        return Response.status(exception.getStatusCode())
                       .entity(new ErrorResponse("HTTP Error", exception.getMessage()))
                       .build();
    }

    @ServerExceptionMapper
    public Response handleForbiddenException(ForbiddenException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                       .entity(new ErrorResponse("Access Denied", exception.getMessage()))
                       .build();
    }
}
```

#### 1.3. Gestion des Exceptions Globale avec `@ServerExceptionMapper`
Le `@ServerExceptionMapper` peut également être utilisé pour gérer toutes les exceptions non spécifiées de manière globale.

```java
@Provider
public class GlobalExceptionMapper {

    @ServerExceptionMapper
    public Response handleException(Exception exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity(new ErrorResponse("An unexpected error occurred", exception.getMessage()))
                       .build();
    }
}
```

### 2. Validation et Gestion des Erreurs avec Bean Validation

Quarkus supporte la validation via **Hibernate Validator**. Vous pouvez valider automatiquement les entrées de vos API REST en utilisant des annotations de validation comme `@NotNull`, `@Size`, etc. et gérer les violations de contraintes via des exceptions.

Exemple :

```java
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    // getters and setters
}
```

Et gérer les violations dans un mapper d'exceptions :

```java
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<String> errors = exception.getConstraintViolations()
                                       .stream()
                                       .map(ConstraintViolation::getMessage)
                                       .collect(Collectors.toList());

        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(errors)
                       .build();
    }
}
```

### 3. Gestion des Exceptions dans les Services et Autres Couches

Dans les couches de service ou de données, vous pouvez lever des exceptions personnalisées ou gérer les exceptions directement, puis les propager vers les couches supérieures pour une gestion centralisée.

Exemple dans un service :

```java
public class UserService {

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));
    }
}
```

### 4. Logs et Monitoring

Pour une gestion proactive des erreurs, Quarkus intègre bien avec des outils de monitoring et de logging comme **Logback**, **Prometheus**, et **Grafana**. Configurer des alertes basées sur les logs d'erreurs ou les métriques peut aider à détecter et réagir rapidement aux erreurs en production.

### 5. Conclusion

La gestion des erreurs et des exceptions dans Quarkus repose sur une combinaison de techniques :

- Mappers d'exceptions pour capturer et formater les erreurs dans les API REST.
- Validation des entrées avec Bean Validation pour gérer les erreurs de validation.
- Gestion des exceptions au niveau des services pour propager les erreurs.
- Utilisation des outils de logging et monitoring pour suivre et réagir aux erreurs en production.

En appliquant ces pratiques, vous pouvez assurer une gestion efficace et centralisée des erreurs dans vos applications Quarkus.