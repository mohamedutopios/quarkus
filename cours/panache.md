Panache est une bibliothèque fournie par Quarkus qui simplifie la gestion des entités et des opérations de base de données dans les applications Java. Panache réduit le besoin de code répétitif (boilerplate) en fournissant des méthodes prêtes à l'emploi pour effectuer des opérations courantes comme la création, la lecture, la mise à jour et la suppression (CRUD) sur les entités. Panache peut être utilisé de deux manières principales : avec des entités actives (qui héritent de `PanacheEntity`) ou avec des repositories (qui implémentent `PanacheRepository`).

### 1. Utilisation des Entités avec Panache

#### 1.1. Création d'une Entité avec Panache

Lorsque vous utilisez Panache, une entité peut hériter de `PanacheEntity`. Cela rend les champs `id` et les méthodes CRUD de base disponibles directement sur l'entité.

```java
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

@Entity
public class User extends PanacheEntity {

    public String username;
    public String email;
    public String password;

    // Pas besoin de getters et setters pour les champs publics
}
```

Dans cet exemple :
- **`PanacheEntity`** : Rend la gestion de l'entité plus simple en fournissant un `id` généré automatiquement et des méthodes CRUD de base.

#### 1.2. Opérations CRUD avec PanacheEntity

Avec une entité Panache, vous pouvez effectuer des opérations CRUD directement :

```java
// Créer un utilisateur
User user = new User();
user.username = "john_doe";
user.email = "john.doe@example.com";
user.password = "password123";
user.persist(); // Sauvegarde l'utilisateur dans la base de données

// Lire un utilisateur par ID
User user = User.findById(1L);

// Mettre à jour un utilisateur
user.username = "john_doe_updated";
user.persist();

// Supprimer un utilisateur
user.delete();
```

#### 1.3. Requêtes Simplifiées

Panache offre également des méthodes pour exécuter des requêtes simplifiées :

```java
// Trouver tous les utilisateurs
List<User> users = User.listAll();

// Trouver des utilisateurs par champ
User user = User.find("username", "john_doe").firstResult();

// Trouver avec plusieurs paramètres
List<User> users = User.find("email like ?1", "%@example.com").list();
```

### 2. Utilisation des Repositories avec Panache

Si vous préférez utiliser un modèle plus orienté repository (ou DAO), Panache offre une autre approche via `PanacheRepository`. Cette approche permet de mieux séparer la logique métier et la gestion des données.

#### 2.1. Création d'un Repository

Vous pouvez créer un repository en implémentant l'interface `PanacheRepository` ou `PanacheRepositoryBase` (si vous avez un type d'identifiant différent de `Long`).

```java
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    // Méthode personnalisée pour trouver un utilisateur par nom d'utilisateur
    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
```

#### 2.2. Utilisation du Repository

Une fois le repository défini, vous pouvez l'utiliser dans vos services ou contrôleurs :

```java
import javax.inject.Inject;
import javax.transaction.Transactional;

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

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void updateUser(Long id, String newUsername) {
        User user = userRepository.findById(id);
        if (user != null) {
            user.username = newUsername;
            userRepository.persist(user);
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
```

#### 2.3. Méthodes PanacheRepository

`PanacheRepository` fournit un ensemble de méthodes pratiques, comme :

- **`listAll()`** : Récupère toutes les entités.
- **`find()`** : Effectue des requêtes simplifiées.
- **`persist()`** : Persiste une nouvelle entité.
- **`delete()`** : Supprime une entité.
- **`count()`** : Compte le nombre d'entités.

Exemple :

```java
List<User> allUsers = userRepository.listAll();
User user = userRepository.find("email", email).firstResult();
long count = userRepository.count();
userRepository.delete("username", "john_doe");
```

### 3. Gestion des Transactions

Panache gère les transactions de manière transparente avec l'annotation `@Transactional`. Vous pouvez annoter les méthodes de votre service ou repository pour qu'elles soient exécutées dans une transaction.

```java
@Transactional
public void updateUser(User user) {
    userRepository.persist(user);
}
```

### 4. Utilisation des Repositories Réactifs avec Panache

Quarkus supporte également les bases de données réactives via `PanacheRepositoryBase` pour les applications réactives. Vous pouvez utiliser cette interface pour créer des repositories qui interagissent avec des bases de données non bloquantes.

```java
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserReactiveRepository implements PanacheRepository<User> {

    // Méthode réactive personnalisée
    public Uni<User> findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
```

### 5. Conclusion

Panache simplifie considérablement la gestion des entités et des opérations de base de données dans Quarkus. Que vous préfériez utiliser des entités actives ou un modèle repository, Panache vous permet de minimiser le code répétitif et de concentrer vos efforts sur la logique métier. Avec des méthodes prêtes à l'emploi pour les opérations CRUD et des requêtes simplifiées, Panache est un outil puissant pour le développement d'applications Java modernes.