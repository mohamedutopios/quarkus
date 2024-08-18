package org.agoncal.quarkus.starting;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@Path("/apis")
public class BookResource3 {

  @Inject
  BookRepository repository;
  @Inject
  Logger logger;


  @GET
  @Path("/author")
  public List<Book> getBooksByAuthor(@QueryParam("author") String author) {
    // /api/books?author=John
    logger.info("Fetching books by author: " + author);
    return repository.getBooksByAuthor(author);
  }

  @GET
  @Path("/authorization")
  public List<Book> getBooks(@HeaderParam("Authorization") String authToken) {
    logger.info("Authorization token: " + authToken);
    return repository.getAllBookAuth(authToken);
  }


  @POST
  @Path("/form")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response addBook(
    @FormParam("id") int id,
    @FormParam("title") String title,
    @FormParam("author") String author,
    @FormParam("date") int datePub,
    @FormParam("genre") String genre) {

    Book book = new Book(id,title, author,datePub ,genre);
    repository.addBook(book);
    return Response.status(Response.Status.CREATED).entity(book).build();
  }

  @GET
  @Path("/session")
  public List<Book> getBookss(@CookieParam("session_id") String sessionId) {
    logger.info("Session ID: " + sessionId);
    return repository.getAllBookss(sessionId);
  }

  @GET
  @Path("/autyear")
  public List<Book> getBooks(
    @QueryParam("author") @DefaultValue("Unknown") String author,
    @QueryParam("year") @DefaultValue("2023") int year) {

    logger.info("Fetching books by author: " + author + " and year: " + year);
    return repository.getBooksByAuthorAndYear(author, year);
  }

  @POST
  @Path("/books")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public List<Book> getBooks(@BeanParam Book book) {
    logger.info("Filtering books by author: " + book.getAuthor() + " and year: " + book.getYearOfPublication());
    repository.addBook(book);
    return repository.getAllBooks();
  }



}
