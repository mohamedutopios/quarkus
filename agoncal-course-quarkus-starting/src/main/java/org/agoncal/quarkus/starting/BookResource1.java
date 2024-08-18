package org.agoncal.quarkus.starting;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@Path("/api/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource1 {

  @Inject
  BookRepository repository;

  @Inject
  Logger logger;

  @GET
  public List<Book> getAllBooks() {
    logger.info("Returns all the books");
    return repository.getAllBooks();
  }

  @GET
  @Path("/count")
  @Produces(MediaType.TEXT_PLAIN)
  public int countAllBooks() {
    logger.info("Returns the number of books");
    return repository.getAllBooks().size();
  }

  @GET
  @Path("{id}")
  public Optional<Book> getBook(@PathParam("id") int id) {
    logger.info("Returns book with id " + id);
    return repository.getBook(id);
  }


  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addBook(Book book) {
    logger.info("Adding a new book: " + book.getTitle());
    repository.addBook(book);
    return Response.status(Response.Status.CREATED).entity(book).build();
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateBook(@PathParam("id") int id, Book updatedBook) {
    logger.info("Updating book with id " + id);
    Optional<Book> existingBook = repository.getBook(id);
    if (existingBook.isPresent()) {
      repository.updateBook(id, updatedBook);
      return Response.ok(updatedBook).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @DELETE
  @Path("{id}")
  public Response deleteBook(@PathParam("id") int id) {
    logger.info("Deleting book with id " + id);
    if (repository.deleteBook(id)) {
      return Response.noContent().build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }




}
