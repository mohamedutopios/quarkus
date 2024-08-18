package com.example.domain;

import com.example.domain.entity.Author;
import com.example.domain.spi.port.AuthorRepository;

public class AuthorService {


    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author save(String firstname, String lastname) {
        Author author = new Author(firstname, lastname);
        authorRepository.save(author);
        return author;
    }

    public Author findById(Long id) {
        return authorRepository.findById(id);
    }
}
