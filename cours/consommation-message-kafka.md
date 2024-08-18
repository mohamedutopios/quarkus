La consommation de messages via Kafka est un cas d'utilisation courant dans les applications de microservices modernes, en particulier pour la communication asynchrone basée sur des événements. Quarkus, avec l'extension SmallRye Reactive Messaging, simplifie la consommation de messages Kafka en intégrant de manière transparente les flux de messages dans votre application.

### 1. Prérequis

Assurez-vous que vous avez un cluster Kafka en cours d'exécution. Si vous ne l'avez pas, vous pouvez utiliser un outil comme [Kafka Docker](https://hub.docker.com/r/bitnami/kafka/) pour configurer rapidement un environnement Kafka.

### 2. Configuration de Kafka dans Quarkus

Pour commencer, vous devez ajouter les extensions nécessaires à votre projet Quarkus :

```bash
./mvnw quarkus:add-extension -Dextensions="smallrye-reactive-messaging-kafka"
```

Ensuite, configurez Kafka dans votre fichier `application.properties` pour définir comment votre application se connecte à Kafka et consomme les messages.

Exemple de configuration :

```properties
# Configuration du canal d'entrée pour Kafka
mp.messaging.incoming.my-kafka-channel.connector=smallrye-kafka
mp.messaging.incoming.my-kafka-channel.topic=my-topic
mp.messaging.incoming.my-kafka-channel.bootstrap.servers=localhost:9092
mp.messaging.incoming.my-kafka-channel.group.id=my-group
mp.messaging.incoming.my-kafka-channel.value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
```

### 3. Création d'un Consumer Kafka

Un consumer Kafka en Quarkus est simplement une méthode annotée avec `@Incoming` qui consomme des messages depuis un canal spécifié.

Voici un exemple simple de consumer Kafka qui lit des messages depuis le canal `my-kafka-channel` :

```java
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KafkaMessageConsumer {

    @Incoming("my-kafka-channel")
    public void consume(String message) {
        System.out.println("Received message: " + message);
        // Traitez le message ici
    }
}
```

- **`@Incoming("my-kafka-channel")`** : Indique que cette méthode reçoit des messages du canal `my-kafka-channel`, qui est configuré pour écouter un topic Kafka.

### 4. Gestion des Messages Complexes

Si vous avez besoin de consommer des messages plus complexes, comme des objets JSON, vous pouvez utiliser un deserializer personnalisé.

#### 4.1. Configuration du Deserializer JSON

Supposons que vous avez un message JSON que vous souhaitez convertir en un objet Java. Vous devez d'abord créer un POJO qui correspond à la structure JSON, puis configurer le deserializer JSON.

Exemple de POJO :

```java
public class User {
    public String id;
    public String name;
    public String email;

    // Constructeurs, getters, setters, etc.
}
```

Configurez ensuite Kafka pour utiliser un deserializer JSON :

```properties
mp.messaging.incoming.my-kafka-channel.value.deserializer=io.quarkus.kafka.client.serialization.ObjectMapperDeserializer
quarkus.kafka.consumer.my-kafka-channel.value.deserializer.json-class=path.to.User
```

#### 4.2. Consommer des Objets JSON

Ensuite, consommez les objets JSON dans votre consumer :

```java
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMessageConsumer {

    @Incoming("my-kafka-channel")
    public void consume(User user) {
        System.out.println("Received User: " + user.name + ", Email: " + user.email);
        // Traitez l'objet User ici
    }
}
```

### 5. Gestion des Erreurs

Dans les systèmes de messagerie, la gestion des erreurs est cruciale. Vous pouvez gérer les erreurs de plusieurs façons :

#### 5.1. Retraitement des Messages

Vous pouvez spécifier un comportement de retraitement (retry) en utilisant des annotations telles que `@Retry` pour tenter de retraiter un message en cas d'échec.

```java
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResilientKafkaConsumer {

    @Incoming("my-kafka-channel")
    @Retry(maxRetries = 3)
    public void consume(String message) {
        // Simule une erreur pour montrer le retry
        if (message.contains("fail")) {
            throw new RuntimeException("Failed to process message");
        }
        System.out.println("Successfully processed message: " + message);
    }
}
```

#### 5.2. Fallback en cas d'échec

Utilisez `@Fallback` pour définir un comportement de secours si le traitement échoue après plusieurs tentatives :

```java
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KafkaConsumerWithFallback {

    @Incoming("my-kafka-channel")
    @Fallback(fallbackMethod = "fallbackHandler")
    public void consume(String message) {
        if (message.contains("fail")) {
            throw new RuntimeException("Processing failed");
        }
        System.out.println("Processed message: " + message);
    }

    public void fallbackHandler(String message) {
        System.out.println("Fallback: Could not process message: " + message);
    }
}
```

### 6. Consommation Asynchrone

Si vous voulez que votre consumer soit non-bloquant et gère les messages de manière asynchrone, vous pouvez utiliser `CompletionStage` ou `Uni` (de SmallRye Mutiny) pour retourner des opérations non-bloquantes.

Exemple avec `CompletionStage` :

```java
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AsyncKafkaConsumer {

    @Incoming("my-kafka-channel")
    public CompletionStage<Void> consumeAsync(String message) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("Asynchronously processing message: " + message);
            // Traitement asynchrone
        });
    }
}
```

Exemple avec `Uni` :

```java
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MutinyKafkaConsumer {

    @Incoming("my-kafka-channel")
    public Uni<Void> consumeAsync(String message) {
        return Uni.createFrom().voidItem()
                  .onItem().invoke(() -> {
                      System.out.println("Asynchronously processing message: " + message);
                      // Traitement asynchrone
                  });
    }
}
```

### 7. Conclusion

La consommation de messages via Kafka avec Quarkus et SmallRye Reactive Messaging simplifie la création de microservices réactifs et scalables. Quarkus permet d'intégrer facilement des consumers Kafka dans vos applications, en prenant en charge divers scénarios, comme la consommation de messages complexes, la gestion des erreurs, et l'asynchronisme. Grâce à ces fonctionnalités, vous pouvez construire des systèmes de traitement de messages robustes qui répondent aux exigences des architectures modernes basées sur des événements.