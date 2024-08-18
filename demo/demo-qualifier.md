L'annotation `@Qualifier` est utilisée dans CDI (Contexts and Dependency Injection) pour distinguer entre plusieurs implémentations d'une même interface ou d'un même type, afin de choisir celle qui doit être injectée. Voici un exemple complet utilisant un scénario avec une classe `Book` et différentes implémentations d'un `BookRepository`.

### Scénario

Supposons que vous avez deux implémentations différentes d'un `BookRepository` :
1. `InMemoryBookRepository` - Stocke les livres en mémoire.
2. `DatabaseBookRepository` - Stocke les livres dans une base de données.

Vous souhaitez injecter l'implémentation appropriée en utilisant un `@Qualifier`.

### Étape 1 : Créer l'interface `BookRepository`

```java
import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    void addBook(Book book);
}
```

### Étape 2 : Créer deux Qualifiers

```java
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, PARAMETER})
public @interface InMemory {
}

@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, PARAMETER})
public @interface Database {
}
```

### Étape 3 : Créer les implémentations de `BookRepository`

**Implémentation en mémoire :**

```java
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@InMemory
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

**Implémentation basée sur la base de données :**

```java
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@Database
@ApplicationScoped
public class DatabaseBookRepository implements BookRepository {

    private List<Book> books = new ArrayList<>(); // Simule une base de données

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

### Étape 4 : Créer la classe `BookService` qui utilise le `@Qualifier`

```java
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class BookService {

    private final BookRepository bookRepository;

    @Inject
    public BookService(@Database BookRepository bookRepository) {  // Choisir l'implémentation ici
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        bookRepository.addBook(book);
    }
}
```

### Étape 5 : Utiliser le `BookService`

Dans une ressource REST par exemple :

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

### Conclusion

Dans cet exemple :
- **`@Qualifier`** est utilisé pour différencier les deux implémentations de `BookRepository`.
- **`@InMemory`** et **`@Database`** sont des annotations personnalisées qui permettent de spécifier laquelle des deux implémentations doit être injectée.
- **`BookService`** est configuré pour utiliser l'implémentation `DatabaseBookRepository` via l'injection `@Database`.

Cela permet de changer facilement d'implémentation en fonction des besoins, sans modifier le code de `BookService` ou de ses consommateurs.