package org.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GET
    @Path("/{id}")
    public Order getOrderById(@PathParam("id") Long id) {
        return orderService.getOrderById(id);
    }

    @POST
    public Order createOrder(Order order) {
        return orderService.createOrder(order);
    }

    @PUT
    @Path("/{id}")
    public Order updateOrder(@PathParam("id") Long id, Order order) {
        return orderService.updateOrder(id, order);
    }

    @DELETE
    @Path("/{id}")
    public void deleteOrder(@PathParam("id") Long id) {
        orderService.deleteOrder(id);
    }

/*    @GET
    @Path("/client/{clientId}/period")
    public List<Order> getOrdersByClientAndPeriod(@PathParam("clientId") Long clientId,
                                                  @QueryParam("start") String start,
                                                  @QueryParam("end") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);

        return orderService.getOrdersByClientAndPeriod(clientId, startDate, endDate);
    }*/


    @GET
    @Path("/client/{clientId}")
    public List<Order> getOrdersByClientId(@PathParam("clientId") Long clientId) {
        return orderService.getOrdersByClientId(clientId);
    }


}
