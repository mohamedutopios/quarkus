package org.agoncal.quarkus.starting.resource;

import org.agoncal.quarkus.starting.service.ProductService;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

  @Inject
  private ProductService productService;

  @GET
  @Path("mysql")
  public Response getProductsFromMySQL() {
    try {
      productService.printMySQLProducts();
      return Response.ok().entity("Products fetched from MySQL").build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("Error fetching products from MySQL")
        .build();
    }
  }

  @GET
  @Path("postgres")
  public Response getProductsFromPostgres() {
    try {
      productService.printPostgresProducts();
      return Response.ok().entity("Products fetched from PostgreSQL").build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("Error fetching products from PostgreSQL")
        .build();
    }
  }
}

