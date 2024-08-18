La gestion des transactions est un aspect crucial de la gestion des bases de données dans toute application Java, y compris celles développées avec Quarkus. Les transactions permettent de s'assurer que les opérations sur la base de données sont complètes, cohérentes, isolées et durables (conformément aux propriétés ACID). Quarkus, avec son intégration de JPA/Hibernate et Panache, fournit des mécanismes simples pour gérer les transactions.

### 1. Introduction à la Gestion des Transactions

Dans Quarkus, les transactions sont gérées via l'API Java Transaction (JTA) et peuvent être facilement contrôlées avec l'annotation `@Transactional`. Quarkus s'assure que toutes les opérations effectuées dans une méthode annotée avec `@Transactional` sont effectuées dans le cadre d'une transaction unique.

### 2. Utilisation de l'Annotation `@Transactional`

#### 2.1. Gestion de Base des Transactions

Pour indiquer que les opérations dans une méthode doivent être effectuées dans une transaction, vous pouvez annoter la méthode avec `@Transactional`. Par exemple, si vous avez un service qui manipule des entités, vous pouvez procéder comme suit :

```java
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Transactional
    public void createUser(String username, String email, String password) {
        User user = new User();
        user.username = username;
        user.email = email;
        user.password = password;
        user.persist();
    }
}
```

Dans cet exemple, toutes les opérations au sein de la méthode `createUser` seront exécutées dans une seule transaction. Si une exception est levée, la transaction sera annulée (rollback).

#### 2.2. Rollback et Gestion des Exceptions

Par défaut, Quarkus effectue un rollback de la transaction si une exception non vérifiée (non-checked) est levée. Vous pouvez contrôler ce comportement en utilisant des attributs sur l'annotation `@Transactional` :

```java
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@ApplicationScoped
public class UserService {

    @Transactional(rollbackOn = Exception.class)
    public void createUser(String username, String email, String password) throws Exception {
        User user = new User();
        user.username = username;
        user.email = email;
        user.password = password;
        user.persist();
        // Si une exception survient ici, un rollback sera effectué
        if (someConditionFails()) {
            throw new Exception("User creation failed!");
        }
    }
    
    private boolean someConditionFails() {
        // Logic that returns true or false
        return true;
    }
}
```

- **`rollbackOn = Exception.class`** : Indique que la transaction doit être annulée (rollback) si une `Exception` est levée.
- **`dontRollbackOn = SomeSpecificException.class`** : Permet de ne pas effectuer de rollback sur certaines exceptions.

#### 2.3. Contrôle du Type de Transaction

Vous pouvez spécifier le type de transaction avec l'attribut `TxType` de l'annotation `@Transactional` :

```java
@Transactional(TxType.REQUIRES_NEW)
public void createUserInNewTransaction(String username, String email, String password) {
    // Cette méthode est exécutée dans une nouvelle transaction, indépendante de toute transaction existante.
}
```

- **`TxType.REQUIRED`** : (par défaut) Joindre une transaction existante ou en démarrer une nouvelle si aucune n'existe.
- **`TxType.REQUIRES_NEW`** : Démarrer une nouvelle transaction, suspendant toute transaction existante.
- **`TxType.MANDATORY`** : Requiert une transaction existante, lève une exception s'il n'y a pas de transaction en cours.
- **`TxType.SUPPORTS`** : Rejoindre une transaction si elle existe, sinon continuer sans transaction.
- **`TxType.NOT_SUPPORTED`** : Suspendre toute transaction en cours.
- **`TxType.NEVER`** : Lève une exception si une transaction est en cours.

### 3. Transactions Manuelles

Dans certains cas, vous pourriez avoir besoin de gérer les transactions manuellement. Cela peut être fait en utilisant l'API `UserTransaction`.

```java
import javax.inject.Inject;
import javax.transaction.UserTransaction;

@ApplicationScoped
public class ManualTransactionService {

    @Inject
    UserTransaction userTransaction;

    public void createUserManually(String username, String email, String password) {
        try {
            userTransaction.begin();
            User user = new User();
            user.username = username;
            user.email = email;
            user.password = password;
            user.persist();
            userTransaction.commit();
        } catch (Exception e) {
            try {
                userTransaction.rollback();
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
```

### 4. Transactions avec Panache

Panache simplifie également la gestion des transactions en permettant l'utilisation de `@Transactional` sur les méthodes de repository ou sur les services qui les utilisent.

#### 4.1. Transactions avec Repositories Panache

Voici un exemple où les méthodes d'un repository Panache sont utilisées dans une transaction :

```java
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    @Transactional
    public void updateUserEmail(Long id, String newEmail) {
        User user = findById(id);
        if (user != null) {
            user.email = newEmail;
            persist(user);
        }
    }
}
```

#### 4.2. Transactions avec des Services Utilisant Panache

Un service peut utiliser un repository Panache et gérer les transactions de manière transparente :

```java
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public void createUser(String username, String email, String password) {
        User user = new User();
        user.username = username;
        user.email = email;
        user.password = password;
        userRepository.persist(user);
    }

    @Transactional
    public void updateUser(Long id, String newUsername) {
        User user = userRepository.findById(id);
        if (user != null) {
            user.username = newUsername;
            userRepository.persist(user);
        }
    }
}
```

### 5. Transactions Distribuées

Quarkus supporte également les transactions distribuées via JTA, ce qui permet d'exécuter des transactions impliquant plusieurs ressources (comme plusieurs bases de données ou un message broker). Cependant, les transactions distribuées sont complexes et peuvent entraîner des problèmes de performance, donc elles doivent être utilisées avec prudence.

### 6. Transactions dans des Applications Réactives

Dans les applications réactives, les transactions doivent être gérées différemment, souvent en utilisant un modèle de coordination plutôt que de verrouiller des ressources. Quarkus offre une prise en charge des transactions réactives via des extensions comme `quarkus-reactive-pg-client`.

### Conclusion

La gestion des transactions avec Quarkus est à la fois puissante et flexible. En utilisant l'annotation `@Transactional`, vous pouvez facilement contrôler la portée des transactions dans votre application, gérer les exceptions et spécifier différents types de comportements transactionnels. Que vous préfériez une gestion automatique ou manuelle, ou que vous travailliez dans un environnement réactif ou traditionnel, Quarkus offre les outils nécessaires pour assurer que vos opérations sur la base de données soient cohérentes et fiables.