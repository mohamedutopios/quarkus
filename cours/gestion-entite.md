La création et la gestion des entités dans Quarkus, en utilisant JPA (Java Persistence API) et Hibernate, sont des étapes essentielles pour interagir avec une base de données relationnelle. Une entité est une classe Java qui est mappée à une table de base de données. Voici un guide complet sur la création et la gestion des entités dans Quarkus.

### 1. Création d'une Entité

#### 1.1. Définir une Classe d'Entité

Une entité est définie comme une classe Java ordinaire, mais elle est annotée avec `@Entity` pour indiquer à JPA qu'elle représente une table dans la base de données.

Exemple d'entité `User` :

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "users")  // optionnel, pour spécifier un nom de table différent
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

- **`@Entity`** : Indique que cette classe est une entité JPA.
- **`@Table(name = "users")`** : (Optionnel) Spécifie le nom de la table. Si omis, la table prendra le nom de la classe.
- **`@Id`** : Spécifie la clé primaire de l'entité.
- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`** : Indique que la valeur de l'identifiant est générée automatiquement par la base de données.
- **`@Column`** : Mappe un champ Java à une colonne de la table. Vous pouvez spécifier des contraintes telles que `nullable`, `unique`, etc.

#### 1.2. Types de Données Supportés

JPA et Hibernate supportent une large gamme de types de données, y compris les types primitifs (`int`, `long`, `boolean`, etc.), `String`, `Date`, `BigDecimal`, et plus encore. Vous pouvez également utiliser des types personnalisés en créant des convertisseurs (via `@Converter`).

### 2. Relations Entre Entités

Les entités peuvent être liées entre elles de différentes manières : `OneToOne`, `OneToMany`, `ManyToOne`, et `ManyToMany`. Voici comment les relations peuvent être définies.

#### 2.1. Relation `OneToMany` et `ManyToOne`

Prenons l'exemple d'une relation entre une entité `User` et une entité `Post`, où un utilisateur peut avoir plusieurs publications (`OneToMany`), et chaque publication appartient à un seul utilisateur (`ManyToOne`).

```java
import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    // Autres champs, getters et setters
}

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Autres champs, getters et setters
}
```

- **`@OneToMany`** : Indique une relation `OneToMany`. Le paramètre `mappedBy` spécifie l'attribut dans l'autre entité qui est responsable de la relation.
- **`@ManyToOne`** : Indique une relation `ManyToOne`. `@JoinColumn` spécifie la colonne dans la base de données qui représente cette relation.
- **`cascade = CascadeType.ALL`** : Indique que toutes les opérations de persistance (insertion, mise à jour, suppression) doivent être propagées à l'entité liée.
- **`orphanRemoval = true`** : Indique que les entités orphelines (non référencées) doivent être supprimées.

#### 2.2. Relation `ManyToMany`

Voici un exemple d'une relation `ManyToMany` entre `User` et une entité `Group` :

```java
import javax.persistence.*;
import java.util.Set;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "user_groups",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    // Autres champs, getters et setters
}
```

- **`@ManyToMany`** : Indique une relation `ManyToMany`.
- **`@JoinTable`** : Spécifie la table de jointure pour la relation `ManyToMany`. Les colonnes `joinColumns` et `inverseJoinColumns` définissent les colonnes pour les relations respectives.

### 3. Gestion des Entités

#### 3.1. CRUD (Create, Read, Update, Delete) avec le `EntityManager`

Le `EntityManager` est utilisé pour gérer le cycle de vie des entités.

- **Créer une entité** :

```java
@Inject
EntityManager em;

@Transactional
public void createUser(User user) {
    em.persist(user);
}
```

- **Lire une entité** :

```java
public User findUserById(Long id) {
    return em.find(User.class, id);
}
```

- **Mettre à jour une entité** :

```java
@Transactional
public void updateUser(User user) {
    em.merge(user);
}
```

- **Supprimer une entité** :

```java
@Transactional
public void deleteUser(Long id) {
    User user = em.find(User.class, id);
    if (user != null) {
        em.remove(user);
    }
}
```

#### 3.2. Requêtes JPQL

JPQL (Java Persistence Query Language) est un langage de requête orienté objet pour interroger des entités JPA.

Exemple de requête JPQL pour trouver des utilisateurs par nom d'utilisateur :

```java
public List<User> findUsersByUsername(String username) {
    return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
             .setParameter("username", username)
             .getResultList();
}
```

### 4. Utilisation de Panache pour Simplifier la Gestion des Entités

Panache est une API simplifiée offerte par Quarkus qui réduit le code boilerplate nécessaire pour gérer les entités.

#### 4.1. Entités avec Panache

Les entités peuvent étendre `PanacheEntity` pour bénéficier de méthodes prêtes à l'emploi comme `persist()`, `delete()`, etc.

```java
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

@Entity
public class User extends PanacheEntity {

    public String username;
    public String email;
    public String password;

    // Pas besoin de getters/setters pour les champs publics
}
```

#### 4.2. Repositories avec Panache

Vous pouvez également utiliser des repositories avec Panache pour gérer les entités :

```java
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
```

### 5. Conclusion

La création et la gestion des entités dans Quarkus avec JPA/Hibernate offrent une manière puissante de modéliser et interagir avec les données dans une application Java. Quarkus simplifie également ce processus avec Panache, réduisant le besoin de code boilerplate et facilitant la gestion des entités et des opérations CRUD. En combinant ces techniques, vous pouvez construire des applications robustes et performantes, avec une gestion efficace des données.