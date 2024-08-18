package net.utopios;


import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import net.utopios.entity.DataContent;
import net.utopios.service.DataService;
import org.jboss.logging.annotations.Pos;

@Path("api/v1/data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DemoResource {

    //@Inject
    private final DataService dataService;

    public DemoResource(DataService dataService) {
        this.dataService = dataService;
    }

    @GET
    public List<DataContent> get() {
        return dataService.getData();
    }

    @GET
    @Path("{id}")
    //PathParam => à partir du path, QueryParam => à partir de la requete, par defaut c'est à partir du body
    public int get(@PathParam("id") int id) {
        return id;
    }


    @POST
    public Object post(DataContent data) {
        return dataService.post(data);
    }
}
