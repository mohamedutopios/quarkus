package com.example.clientapi;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;
@RegisterRestClient
@Path("/contacts")
public interface ContactApiService {
    @GET
    public List<Object> getContacts();
}
