package org.agoncal.quarkus.starting;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@Path("/api")
public class BookResource2 {

  @Inject
  BookRepository repository;

  @GET
  @Path("/health")
  @Produces(MediaType.TEXT_PLAIN)
  public String checkHealth() {

    return "API is up and running";
  }


  @GET
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Optional<Book> getBook(@PathParam("id") int id) {
    return repository.getBook(id);
  }


  @GET
  @Path("/test/{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
  public Response getBookId(@PathParam("id") int id) {
    Optional<Book> book = repository.getBook(id);
    if (book.isPresent()) {
      return Response.ok(book.get()).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
    }
  }

  @GET
  @Path("/about")
  @Produces(MediaType.TEXT_HTML)
  public String getAboutPage() {
    return "<html><body><h1>About the Book API</h1><p>This API allows you to manage a collection of books.</p></body></html>";
  }


}
