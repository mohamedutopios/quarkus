package com.example.adapter.rest.clientservice;

import com.example.adapter.rest.dto.AuthorDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/api/v1/author")
public interface ClientAuthorService {

    @Path("/{id}")
    @GET
    public AuthorDTO get(@PathParam("id") Long id);
}
