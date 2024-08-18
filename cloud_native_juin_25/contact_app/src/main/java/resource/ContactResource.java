package resource;

import dto.ContactDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import service.ContactService;
import java.util.List;
@Path("/api/v1/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactService contactService;


    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @GET
    public List<ContactDTO> get() {
        return contactService.get();
    }

    @GET
    @Path("/{id}")
    public ContactDTO get(@PathParam("id") Long id) {
        return contactService.getById(id);
    }


    @POST
    public ContactDTO post(ContactDTO contactDTO) {
        return contactService.saveContact(contactDTO);
    }


    @PUT
    public ContactDTO put(ContactDTO contactDTO) {
        return contactService.update(contactDTO);
    }
}
