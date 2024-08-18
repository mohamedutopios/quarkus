package com.example.infra.perspanache.repository.impl;

import com.example.domain.entity.Author;
import com.example.domain.spi.port.AuthorRepository;
import com.example.infra.perspanache.entity.AuthorEntity;
import com.example.infra.perspanache.repository.AuthorEntityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class AuthorRepositoryImpl implements AuthorRepository {

    @Inject
    private AuthorEntityRepository authorEntityRepository;

    @Override
    public void save(Author author) {
        AuthorEntity authorEntity = AuthorEntity.builder().firstname(author.getFirstname()).lastname(author.getLastname()).build();
        authorEntityRepository.persistAndFlush(authorEntity);
        author.setId(authorEntity.getId());
    }

    @Override
    public Author findById(Long id) {
        AuthorEntity authorEntity = authorEntityRepository.findById(id);
        return new Author(authorEntity.getId(), authorEntity.getFirstname(), authorEntity.getLastname());
    }
}
