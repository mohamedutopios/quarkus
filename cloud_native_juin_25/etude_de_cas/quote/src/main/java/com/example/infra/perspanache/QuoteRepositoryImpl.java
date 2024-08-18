package com.example.infra.perspanache;


import com.example.domain.entity.Quote;

import com.example.domain.spi.port.QuoteRepository;
import com.example.infra.perspanache.entity.QuoteEntity;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class QuoteRepositoryImpl implements QuoteRepository {

    @Inject
    private QuoteEntityRepository quoteEntityRepository;


    @Override
    public void save(Quote quote) {
        quoteEntityRepository.persistAndFlush(QuoteEntity.builder().content(quote.getContent()).authorId(quote.getAuthorId()).build());
    }

    @Override
    public Quote findRandom() {
        QuoteEntity quoteEntity = quoteEntityRepository.findRandom();
        return new Quote(quoteEntity.getId(), quoteEntity.getContent(), quoteEntity.getAuthorId());
    }

    @Override
    public Quote findById(Long Id) {
        QuoteEntity quoteEntity = quoteEntityRepository.findById(Id);
        return new Quote(quoteEntity.getId(), quoteEntity.getContent(), quoteEntity.getAuthorId());
    }

    @Override
    public Multi<Quote> findAll() {
        return Multi.createFrom().iterable(quoteEntityRepository.streamAll().map(q -> new Quote(q.getId(), q.getContent(), q.getAuthorId())).collect(Collectors.toList()));
    }
}
