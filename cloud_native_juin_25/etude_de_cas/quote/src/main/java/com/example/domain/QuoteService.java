package com.example.domain;


import com.example.domain.entity.Quote;
import com.example.domain.spi.port.QuoteRepository;
import io.smallrye.mutiny.Multi;

public class QuoteService {


    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public Quote save(String content, Long authorId) {
        Quote quote = new Quote(content, authorId);
        quoteRepository.save(quote);
        return quote;
    }

    public Quote findRandom() {
        return quoteRepository.findRandom();
    }
    public Quote findById(Long id) {
        return quoteRepository.findById(id);
    }

    public Multi<Quote> getAll() {
        return quoteRepository.findAll();
    }

}
