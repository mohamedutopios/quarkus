package com.example.adapter.grpc;

import authorProto.AuthorGet;
import authorProto.AuthorGetById;
import authorProto.AuthorGrpc;
import authorProto.AuthorPost;
import com.example.domain.AuthorService;
import com.example.domain.entity.Author;
import com.example.domain.spi.port.AuthorRepository;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class AuthorGrpcService implements AuthorGrpc {

    private final AuthorService authorService;

    public AuthorGrpcService(AuthorRepository authorRepository) {
        this.authorService = new AuthorService(authorRepository);
    }

    @Override
    public Uni<AuthorGet> getAuthorById(AuthorGetById request) {
        Author author = authorService.findById(request.getId());
        return Uni.createFrom().item(()->setAuthorGet(author));
    }

    @Override
    public Uni<AuthorGet> postAuthor(AuthorPost request) {
        Author author = authorService.save(request.getFirstname(),request.getLastname());
        return Uni.createFrom().item(()->setAuthorGet(author));
    }

    private AuthorGet setAuthorGet (Author author){
        return AuthorGet.newBuilder()
                .setId(author.getId())
                .setFirstname(author.getFirstname())
                .setLastname(author.getLastname())
                .build();
    }
}
