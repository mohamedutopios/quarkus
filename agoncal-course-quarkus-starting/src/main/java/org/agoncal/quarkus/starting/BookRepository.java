package org.agoncal.quarkus.starting;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookRepository {

  @ConfigProperty(name = "books.genre", defaultValue = "Sci-Fi")
  String genre;

  private List<Book> books = new ArrayList<>(List.of(new Book(1, "Understanding Quarkus", "Antonio", 2020, genre),
    new Book(2, "Practising Quarkus", "Antonio", 2020, genre),
    new Book(3, "Effective Java", "Josh Bloch", 2001, genre),
    new Book(4, "Thinking in Java", "Bruce Eckel", 1998, genre)));

  public List<Book> getAllBooks() {
    return books;
  }

  public Optional<Book> getBook(int id) {
    return getAllBooks().stream().filter(book -> book.id == id).findFirst();
  }

  public void addBook(Book book) {
    if (book == null || book.getTitle() == null || book.getTitle().isEmpty()) {
      throw new IllegalArgumentException("Book or book title cannot be null or empty");
    }
    books.add(book);
  }
  public void updateBook(int id, Book updatedBook) {
    Optional<Book> existingBook = getBook(id);
    if (existingBook.isPresent()) {
      Book bookToUpdate = existingBook.get();
      // Met à jour les champs du livre existant avec ceux du livre mis à jour
      bookToUpdate.setTitle(updatedBook.getTitle());
      bookToUpdate.setAuthor(updatedBook.getAuthor());
      bookToUpdate.setYearOfPublication(updatedBook.getYearOfPublication());
      bookToUpdate.setGenre(updatedBook.getGenre());

      // Vous pouvez ajouter d'autres champs à mettre à jour selon votre modèle de données
    } else {
      throw new NotFoundException("Book with ID " + id + " not found");
    }
  }

  public boolean deleteBook(int id) {
    Optional<Book> existingBook = getBook(id);
    if (existingBook.isPresent()) {
      books.remove(existingBook.get());
      return true;
    } else {
      return false;
    }
  }

// Pour bookresource3

  public List<Book> getBooksByAuthor(String author) {
    if (author == null || author.isEmpty()) {
      return books; // Retourne tous les livres si aucun auteur n'est spécifié
    }
    return books.stream()
      .filter(book -> book.getAuthor().equalsIgnoreCase(author))
      .collect(Collectors.toList());
  }

  // Pour bookresource3
  public List<Book> getAllBookAuth(String authToken) {
    if (validateToken(authToken)) {
      return books;
    } else {
      throw new SecurityException("Invalid authorization token");
    }
  }

  private boolean validateToken(String authToken) {
    // Logique pour valider le token
    return "valid-token".equals(authToken); // Simule une validation basique
  }

  // Pour bookresource3
  public void addBook(int id,String title, String author, int datePub ,String genre) {
    Book newBook = new Book(id,title, author,datePub ,genre);
    books.add(newBook);
  }

  // Pour bookresource3
  public List<Book> getAllBookss(String sessionId) {
    if (isValidSession(sessionId)) {
      return books;
    } else {
      throw new SecurityException("Invalid session ID");
    }
  }

  private boolean isValidSession(String sessionId) {
    // Logique de validation de session
    return sessionId != null && sessionId.startsWith("sess-"); // Simule une validation simple
  }

  public List<Book> getBooksByAuthorAndYear(String author, int year) {
    return books.stream()
      .filter(book -> "Unknown".equalsIgnoreCase(author) || book.getAuthor().equalsIgnoreCase(author))
      .filter(book -> book.getYearOfPublication() == year)
      .collect(Collectors.toList());
  }



}
