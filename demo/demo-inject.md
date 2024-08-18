Voici un exemple complet utilisant l'annotation `@Inject` dans un contexte de Quarkus avec une classe `Book` et un service `BookService`. Cet exemple montre comment injecter des dépendances dans un service à l'aide de `@Inject` et comment utiliser ce service dans une ressource REST.

### Scénario

Supposons que vous avez une interface `BookRepository` avec une implémentation concrète `InMemoryBookRepository`. Vous avez également un service `BookService` qui utilise `BookRepository` pour gérer les livres. Ce service est ensuite injecté dans une ressource REST pour exposer les opérations CRUD sur les livres.

### Étape 1 : Créer l'interface `BookRepository`

```java
import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    void addBook(Book book);
}
```

### Étape 2 : Créer l'implémentation de `BookRepository`

**Implémentation en mémoire :**

```java
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InMemoryBookRepository implements BookRepository {

    private List<Book> books = new ArrayList<>();

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }
}
```

### Étape 3 : Créer le `BookService` qui utilise `@Inject`

```java
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class BookService {

    @Inject
    InMemoryBookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        bookRepository.addBook(book);
    }
}
```

### Étape 4 : Créer la classe `BookResource` qui utilise le `BookService`

```java
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    BookService bookService;

    @GET
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @POST
    public Response addBook(Book book) {
        bookService.addBook(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }
}
```

### Explication

- **`@Inject`** est utilisé pour injecter les dépendances dans le `BookService` et la ressource `BookResource`.
- **`InMemoryBookRepository`** est injecté dans `BookService`, ce qui signifie que Quarkus gère la création et la gestion du cycle de vie de cette dépendance.
- **`BookService`** est à son tour injecté dans `BookResource` pour gérer les opérations sur les livres.

### Étape 5 : Exemple de la classe `Book`

Voici un exemple simple de la classe `Book` utilisée dans l'application :

```java
public class Book {
    private String title;
    private String author;
    private String isbn;
    private double price;

    // Constructeurs, getters et setters
    public Book() {}

    public Book(String title, String author, String isbn, double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
```

### Étape 6 : Tester avec Postman

Vous pouvez maintenant tester l'API en utilisant Postman :

1. **Ajouter un livre :**
   - Méthode HTTP : `POST`
   - URL : `http://localhost:8080/books`
   - Corps de la requête :
     ```json
     {
         "title": "Effective Java",
         "author": "Joshua Bloch",
         "isbn": "9780134685991",
         "price": 45.0
     }
     ```
   - Cliquez sur "Send".

2. **Récupérer tous les livres :**
   - Méthode HTTP : `GET`
   - URL : `http://localhost:8080/books`
   - Cliquez sur "Send".

### Conclusion

Cet exemple montre comment utiliser `@Inject` pour gérer les dépendances dans une application Quarkus. L'injection de dépendances permet de décorréler les différentes parties de votre application, facilitant ainsi la maintenance, les tests, et l'évolution du code. Grâce à `@Inject`, Quarkus s'occupe de la création des instances et de leur cycle de vie, simplifiant ainsi le développement d'applications modulaires et maintenables.