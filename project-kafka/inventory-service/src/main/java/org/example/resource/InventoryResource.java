package org.example.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.ProductInventory;
import org.example.service.InventoryService;

import java.util.List;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryResource {

    @Inject
    InventoryService inventoryService;

    @GET
    public List<ProductInventory> getAllInventories() {
        return inventoryService.findAll();
    }

    @GET
    @Path("/{productId}")
    public Response getProductInventory(@PathParam("productId") Long productId) {
        return inventoryService.findByProductId(productId)
                .map(productInventory -> Response.ok(productInventory).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response createInventory(ProductInventory productInventory) {
        ProductInventory created = inventoryService.createInventory(productInventory.getProductId(), productInventory.getQuantity());
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateInventory(@PathParam("id") Long id, ProductInventory productInventory) {
        ProductInventory updated = inventoryService.updateInventory(id, productInventory.getQuantity());
        if (updated != null) {
            return Response.ok(updated).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteInventory(@PathParam("id") Long id) {
        boolean deleted = inventoryService.deleteInventory(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/check")
    public Response checkAndUpdateInventory(@QueryParam("productId") Long productId, @QueryParam("quantity") int quantity) {
        boolean success = inventoryService.checkAndUpdateInventory(productId, quantity);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Insufficient stock").build();
        }
    }

    @POST
    @Path("/{productId}/increase")
    public Response increaseInventory(@PathParam("productId") Long productId, @QueryParam("quantity") int quantity) {
        inventoryService.increaseInventory(productId, quantity);
        return Response.ok().build();
    }
}
