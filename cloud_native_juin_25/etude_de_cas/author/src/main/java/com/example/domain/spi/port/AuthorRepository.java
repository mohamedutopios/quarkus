package com.example.domain.spi.port;

import com.example.domain.entity.Author;

public interface AuthorRepository {

    void save(Author author);
    Author findById(Long id);

}
