package com.example.infra.perspanache.repository;

import com.example.infra.perspanache.entity.AuthorEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class AuthorEntityRepository implements PanacheRepository<AuthorEntity> {
}
