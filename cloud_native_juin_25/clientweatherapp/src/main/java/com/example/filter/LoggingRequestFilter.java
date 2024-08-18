package com.example.filter;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(1)
public class LoggingRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        //Logique métier du filtre
    }
}
