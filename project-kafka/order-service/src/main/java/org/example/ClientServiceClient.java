package org.example;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.example.dto.ClientDTO;

@Path("/clients")
@RegisterRestClient(configKey = "client-service")
public interface ClientServiceClient {

    @GET
    @Path("/{id}")
    ClientDTO getClientById(@PathParam("id") Long id);
}

