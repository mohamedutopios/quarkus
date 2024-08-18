package org.example;



import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    ClientService clientService;

    @GET
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GET
    @Path("/{id}")
    public Client getClientById(@PathParam("id") Long id) {
        return clientService.getClientById(id);
    }

    @POST
    public Client createClient(Client client) {
        return clientService.createClient(client);
    }

    @PUT
    @Path("/{id}")
    public Client updateClient(@PathParam("id") Long id, Client client) {
        return clientService.updateClient(id, client);
    }

    @DELETE
    @Path("/{id}")
    public void deleteClient(@PathParam("id") Long id) {
        clientService.deleteClient(id);
    }
}

