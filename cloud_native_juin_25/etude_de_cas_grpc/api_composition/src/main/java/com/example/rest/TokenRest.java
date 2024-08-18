package com.example.rest;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.*;


@Path("api/token")
public class TokenRest {

    @ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration") public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer") public String issuer;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response token () {
        Set<Role> roles = Collections.singleton(Role.ADMIN);

            try {
                return Response.ok(TokenUtils.generateToken("toto", roles,duration, issuer)).build();
            } catch (Exception e) {
                return Response.ok(e.getMessage()).build();
            }

    }

    @GET
    @Path("extend")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response extendToken (String token) {
        Set<Role> roles = Collections.singleton(Role.ADMIN);

            try {
                return Response.ok(TokenUtils.generateToken("toto", roles,duration, issuer)).build();
            } catch (Exception e) {
                return Response.ok(e.getMessage()).build();
            }

    }

}
