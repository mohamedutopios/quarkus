Quarkus, en tant que framework basé sur CDI (Contexts and Dependency Injection), propose plusieurs options pour l'injection de dépendances dans vos applications. Voici un aperçu des différentes options disponibles pour l'injection de dépendances avec Quarkus :

### 1. **`@Inject`**
   - L'annotation `@Inject` est la méthode la plus courante pour injecter des dépendances dans Quarkus.
   - Elle peut être utilisée pour injecter des beans CDI, des services, des instances de classes, etc.

   **Exemple :**
   ```java
   @ApplicationScoped
   public class BookService {
   
       @Inject
       Logger logger;

       @Inject
       BookRepository repository;

       public List<Book> getAllBooks() {
           logger.info("Fetching all books");
           return repository.findAll();
       }
   }
   ```

### 2. **`@Qualifier`**
   - Utilisé en combinaison avec `@Inject` pour différencier plusieurs implémentations du même type.
   - Les `@Qualifier` permettent d'injecter la bonne implémentation en fonction du contexte.

   **Exemple :**
   ```java
   @Qualifier
   @Retention(RUNTIME)
   @Target({FIELD, TYPE, METHOD})
   public @interface Preferred {
   }

   @Preferred
   @ApplicationScoped
   public class PreferredBookRepository implements BookRepository {
       // Implémentation préférée
   }

   @ApplicationScoped
   public class BookService {
   
       @Inject
       @Preferred
       BookRepository repository;

       public List<Book> getAllBooks() {
           return repository.findAll();
       }
   }
   ```

### 3. **`@Singleton`**
   - Indique que la classe annotée doit être traitée comme un singleton (une seule instance pour l'ensemble de l'application).
   - Peut être utilisé avec `@Inject` pour injecter ce singleton dans d'autres classes.

   **Exemple :**
   ```java
   @Singleton
   public class ConfigService {

       private final String configValue = "Some config";

       public String getConfigValue() {
           return configValue;
       }
   }

   @ApplicationScoped
   public class BookService {
   
       @Inject
       ConfigService configService;

       public String getConfig() {
           return configService.getConfigValue();
       }
   }
   ```

### 4. **`@Producer` et `@Produces`**
   - Les `@Producer` methods sont utilisées pour définir des méthodes qui produisent des instances d'objets, qui peuvent ensuite être injectées ailleurs.
   - `@Produces` est utilisé pour marquer une méthode ou un champ comme étant une source d'injection.

   **Exemple :**
   ```java
   public class LoggerProducer {

       @Produces
       public Logger produceLogger(InjectionPoint injectionPoint) {
           return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
       }
   }

   @ApplicationScoped
   public class BookService {
   
       @Inject
       Logger logger;

       public void logSomething() {
           logger.info("Logging something");
       }
   }
   ```

### 5. **`@ConfigProperty`**
   - Utilisé pour injecter des propriétés de configuration dans vos beans.
   - Les propriétés peuvent être définies dans `application.properties` ou via d'autres sources de configuration.

   **Exemple :**
   ```java
   @ApplicationScoped
   public class ConfigService {

       @Inject
       @ConfigProperty(name = "myapp.greeting")
       String greeting;

       public String getGreeting() {
           return greeting;
       }
   }
   ```

### 6. **`@ApplicationScoped`**
   - Spécifie que le bean est lié au cycle de vie de l'application. Il sera instancié une seule fois par application.
   - Peut être utilisé pour des services qui doivent être partagés à travers l'application entière.

   **Exemple :**
   ```java
   @ApplicationScoped
   public class BookRepository {

       private List<Book> books = new ArrayList<>();

       public List<Book> findAll() {
           return books;
       }
   }
   ```

### 7. **`@RequestScoped`**
   - Indique que le bean est instancié pour chaque requête HTTP et détruit à la fin de la requête.
   - Utilisé principalement pour des services qui gèrent des états spécifiques à une requête.

   **Exemple :**
   ```java
   @RequestScoped
   public class RequestLogger {

       private final long requestStartTime = System.currentTimeMillis();

       public long getRequestDuration() {
           return System.currentTimeMillis() - requestStartTime;
       }
   }

   @ApplicationScoped
   public class BookService {
   
       @Inject
       RequestLogger requestLogger;

       public long logRequestDuration() {
           return requestLogger.getRequestDuration();
       }
   }
   ```

### 8. **`@SessionScoped`**
   - Indique que le bean est lié au cycle de vie d'une session utilisateur, utilisé principalement dans des applications web où une session utilisateur est maintenue.
   - Le bean est partagé au sein de la même session utilisateur.

   **Exemple :**
   ```java
   @SessionScoped
   public class ShoppingCart implements Serializable {
       private List<Item> items = new ArrayList<>();

       public void addItem(Item item) {
           items.add(item);
       }

       public List<Item> getItems() {
           return items;
       }
   }
   ```

### 9. **`@Alternative`**
   - Permet de déclarer une alternative à une implémentation de bean existante.
   - Utilisé pour la gestion des environnements de test ou la substitution de composants.

   **Exemple :**
   ```java
   @Alternative
   @Priority(1)
   @ApplicationScoped
   public class MockBookRepository implements BookRepository {
       // Implémentation pour les tests
   }
   ```

### 10. **`@PostConstruct` et `@PreDestroy`**
   - `@PostConstruct` est utilisé pour exécuter du code immédiatement après que l'injection a eu lieu, typiquement pour de l'initialisation.
   - `@PreDestroy` est utilisé pour exécuter du code avant que le bean ne soit détruit, pour faire du nettoyage.

   **Exemple :**
   ```java
   @ApplicationScoped
   public class BookService {

       @PostConstruct
       public void init() {
           // Code d'initialisation
       }

       @PreDestroy
       public void cleanup() {
           // Code de nettoyage
       }
   }
   ```

### Conclusion

Quarkus offre une large gamme d'options pour l'injection de dépendances, ce qui permet une grande flexibilité et des possibilités variées pour structurer votre application. Que ce soit pour injecter des configurations, gérer le cycle de vie des objets ou produire des instances complexes, ces annotations vous aident à créer des applications Java modernes, modulaires et maintenables.