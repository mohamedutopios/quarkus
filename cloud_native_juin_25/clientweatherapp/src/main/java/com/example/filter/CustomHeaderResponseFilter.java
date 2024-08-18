package com.example.filter;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(1000)
public class CustomHeaderResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().add("custom-header", "value of custom header");
    }
}
