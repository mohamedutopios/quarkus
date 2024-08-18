package com.example.grpc;

import authorProto.AuthorGet;
import authorProto.AuthorGetById;
import authorProto.AuthorGrpc;
import com.example.dto.AuthorDTO;
import com.example.dto.Quote;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import quoteProto.QuoteGet;
import quoteProto.QuoteGetById;

@Path("api/author")
public class AuthorGrpcClient {

    @GrpcClient
    AuthorGrpc authorGrpc;

    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Uni<Response> getAuthorById (@PathParam("id")long id){
        return authorGrpc.getAuthorById(AuthorGetById.newBuilder().setId(id).build()).onItem().transform((AuthorGet a)->{
            return Response.ok(
                    AuthorDTO.builder()
                            .id(a.getId())
                            .firstname(a.getFirstname())
                            .lastname(a.getLastname()).build()).build();

        });
    }

}
