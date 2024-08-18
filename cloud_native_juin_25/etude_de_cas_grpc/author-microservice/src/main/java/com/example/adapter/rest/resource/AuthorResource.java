package com.example.adapter.rest.resource;


import com.example.domain.AuthorService;
import com.example.domain.entity.Author;
import com.example.domain.spi.port.AuthorRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/author")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private final AuthorRepository authorRepository;
    private final AuthorService authorService;

    public AuthorResource(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.authorService = new AuthorService(this.authorRepository);
    }

    @POST
    @Transactional
    public Author post(Author author) {
        return authorService.save(author.getFirstname(), author.getLastname());
    }
    @GET
    @Path("/{id}")
    public Author get(@PathParam("id") Long id) {
        return authorService.findById(id);
    }
}
