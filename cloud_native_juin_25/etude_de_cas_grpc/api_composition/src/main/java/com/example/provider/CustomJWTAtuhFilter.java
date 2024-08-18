package com.example.provider;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;


@Provider
@Priority(Priorities.AUTHENTICATION)
public class CustomJWTAtuhFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        //Récupération du token

        String token = containerRequestContext.getHeaderString("Authorization").split(" ")[1];

        //Vérification du token en utilisant la clé public
    }
}
