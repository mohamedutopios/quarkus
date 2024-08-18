package com.example.grpc;

import authorProto.AuthorGet;
import authorProto.AuthorGetById;
import authorProto.AuthorGrpc;
import com.example.dto.AuthorDTO;
import com.example.dto.Quote;
import com.example.dto.QuoteDTO;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.experimental.PackagePrivate;
import quoteProto.QuoteGet;
import quoteProto.QuoteGetById;
import quoteProto.QuoteGrpc;
import quoteProto.QuotePost;

@Path("api/quote")
public class QuotegrpcClient {

    @GrpcClient
    QuoteGrpc quoteGrpc;

    @GrpcClient
    AuthorGrpc authorGrpc;

    @GET
    @Path("/{id}")
    public Uni<Response> getQuoteById (@PathParam("id")long id){
        return quoteGrpc.getQuoteById(QuoteGetById
                .newBuilder()
                .setId(id)
                .build())
                .onItem().transform((QuoteGet q)->{
        return Response.ok(
            Quote.builder().id(q.getId())
                            .content(q.getContent())
                            .authorId(q.getAuthorId())
                    .build()
                ).build();
            });
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> postQuote (Quote quote){
        return quoteGrpc.postQuote(QuotePost
                .newBuilder()
                .setContent(quote.getContent())
                .setAuthorId(quote.getAuthorId())
                .build()).onItem().transform((QuoteGet q)->{
            return Response.ok(
                    Quote.builder().id(q.getId()).content(q.getContent()).authorId(q.getAuthorId()).build()
            ).build();
        });
    }
}
