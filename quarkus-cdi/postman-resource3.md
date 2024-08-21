Tester vos endpoints REST dans Postman est un excellent moyen de valider le bon fonctionnement de votre API. Voici comment vous pouvez tester chaque type de paramètre utilisé dans vos services.

### 1. **Tester `@QueryParam`**

**Endpoint :**
```java
@GET
@Path("/books")
public List<Book> getBooksByAuthor(@QueryParam("author") String author) {
    logger.info("Fetching books by author: " + author);
    return repository.getBooksByAuthor(author);
}
```

**Test dans Postman :**

1. **Méthode HTTP** : `GET`
2. **URL** : `http://localhost:8097/api/books?author=John`
  - **Explication** : Le paramètre de requête `author` est ajouté à l'URL comme `?author=John`.
3. **Exécution** : Cliquez sur "Send" pour envoyer la requête.
4. **Vérifiez** : Vous devriez recevoir une liste de livres écrits par "John".

### 2. **Tester `@HeaderParam`**

**Endpoint :**
```java
@GET
@Path("/books")
public List<Book> getBooks(@HeaderParam("Authorization") String authToken) {
    logger.info("Authorization token: " + authToken);
    return repository.getAllBooks();
}
```

**Test dans Postman :**

1. **Méthode HTTP** : `GET`
2. **URL** : `http://localhost:8097/apis/authorization`
3. **Ajouter un Header** :
  - Cliquez sur l'onglet `Headers`.
  - Ajoutez un en-tête clé/valeur : `Authorization` : `valid-token`.
4. **Exécution** : Cliquez sur "Send" pour envoyer la requête.
5. **Vérifiez** : La réponse doit contenir tous les livres, en supposant que le token est valide.

### 3. **Tester `@FormParam`**

**Endpoint :**
```java
@POST
@Path("/books")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public Response addBook(
    @FormParam("title") String title,
    @FormParam("author") String author,
    @FormParam("price") double price) {
    
    Book book = new Book(title, author, price);
    repository.addBook(book);
    return Response.status(Response.Status.CREATED).entity(book).build();
}
```

**Test dans Postman :**

1. **Méthode HTTP** : `POST`
2. **URL** : `http://localhost:8097/apis/form`
3. **Ajouter des Body Parameters** :
  - Cliquez sur l'onglet `Body`.
  - Sélectionnez `x-www-form-urlencoded`.
  - Ajoutez les paires clé/valeur :
    - `title` : `Sur la route`
    - `author` : `Author Name`
    - `genre` : `SF`
4. **Exécution** : Cliquez sur "Send" pour envoyer la requête.
5. **Vérifiez** : La réponse doit indiquer que le livre a été créé (code 201) avec les détails du livre.

### 4. **Tester `@CookieParam`**

**Endpoint :**
```java
@GET
@Path("/books")
public List<Book> getBooks(@CookieParam("session_id") String sessionId) {
    logger.info("Session ID: " + sessionId);
    return repository.getAllBooks();
}
```

**Test dans Postman :**

1. **Méthode HTTP** : `GET`
2. **URL** : `http://localhost:8097/api/books`
3. **Ajouter un Cookie** :
  - Cliquez sur l'onglet `Headers`.
  - Ajoutez un en-tête clé/valeur : `Cookie` : `session_id=sess-12345`.
4. **Exécution** : Cliquez sur "Send" pour envoyer la requête.
5. **Vérifiez** : La réponse doit contenir tous les livres si le `session_id` est valide.

### 5. **Tester `@MatrixParam`**

**Endpoint :**
```java
@GET
@Path("/books")
public List<Book> getBooksByFilters(@MatrixParam("author") String author, @MatrixParam("year") int year) {
    logger.info("Fetching books by author: " + author + " and year: " + year);
    return repository.getBooksByAuthorAndYear(author, year);
}
```

**Test dans Postman :**

1. **Méthode HTTP** : `GET`
2. **URL** : `http://localhost:8097/api/books;author=John;year=2023`
  - **Explication** : Les paramètres de matrices sont inclus après `;` dans l'URL.
3. **Exécution** : Cliquez sur "Send" pour envoyer la requête.
4. **Vérifiez** : Vous devriez recevoir une liste de livres correspondant à l'auteur et l'année spécifiés.

### 6. **Tester `@BeanParam`**

**Endpoint :**
```java
@POST
@Path("/books")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public List<Book> getBooks(@BeanParam Book book) {
  logger.info("Filtering books by author: " + book.getAuthor() + " and year: " + book.getYearOfPublication());
  repository.addBook(book);
  return repository.getAllBooks();
}
```

**Test dans Postman :**

1. **Méthode HTTP** : `GET`
2. **URL** : `http://localhost:8097/api/books?author=John&year=2023`
  - **Explication** : Les paramètres sont passés comme `QueryParam` et sont automatiquement injectés dans `BookFilterBean`.
3. **Exécution** : Cliquez sur "Send" pour envoyer la requête.
4. **Vérifiez** : La réponse doit filtrer les livres par auteur et année selon les paramètres donnés.

### 7. **Tester `@DefaultValue`**

**Endpoint :**
```java
@GET
@Path("/books")
public List<Book> getBooks(
    @QueryParam("author") @DefaultValue("Unknown") String author, 
    @QueryParam("year") @DefaultValue("2023") int year) {
    
    logger.info("Fetching books by author: " + author + " and year: " + year);
    return repository.getBooksByAuthorAndYear(author, year);
}
```

**Test dans Postman :**

1. **Méthode HTTP** : `GET`
2. **URL** : `http://localhost:8097/api/books`
  - **Explication** : Les valeurs par défaut seront utilisées car aucun paramètre n'est passé.
3. **Exécution** : Cliquez sur "Send" pour envoyer la requête.
4. **Vérifiez** : La réponse doit utiliser les valeurs par défaut "Unknown" pour l'auteur et 2023 pour l'année.

### Conseils généraux pour tester avec Postman

- **Vérifiez les réponses** : Assurez-vous que les réponses de l'API correspondent à vos attentes en termes de format et de contenu.
- **Utilisez des Collections Postman** : Organisez vos tests en collections pour les exécuter plus facilement et maintenir vos requêtes bien organisées.
- **Sauvegardez les tests** : Ajoutez des assertions dans les tests Postman pour vérifier automatiquement les réponses, comme le statut HTTP ou le contenu JSON.

En suivant ces étapes, vous pouvez tester tous les aspects de votre API avec Postman, vérifier que les différents types de paramètres fonctionnent correctement, et garantir que votre API REST est robuste et fiable.
