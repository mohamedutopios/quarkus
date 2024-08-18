package org.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;


import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {


    @Inject
    ProductService productService;

    @GET
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GET
    @Path("/{id}")
    public Product getProductById(@PathParam("id") Long id) {
        return productService.getProductById(id);
    }

    @POST
    public Product createProduct(Product product) {
        return productService.createProduct(product);
    }

    @PUT
    @Path("/{id}")
    public Product updateProduct(@PathParam("id") Long id, Product product) {
        return productService.updateProduct(id, product);
    }

    @DELETE
    @Path("/{id}")
    public void deleteProduct(@PathParam("id") Long id) {
        productService.deleteProduct(id);
    }
}

