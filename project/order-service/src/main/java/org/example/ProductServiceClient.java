package org.example;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.example.dto.ProductDTO;

@Path("/products")
@RegisterRestClient(configKey = "product-service")
public interface ProductServiceClient {

    @GET
    @Path("/{id}")
    ProductDTO getProductById(@PathParam("id") Long id);
}
