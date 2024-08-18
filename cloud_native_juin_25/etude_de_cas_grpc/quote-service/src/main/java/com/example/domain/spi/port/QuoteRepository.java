package com.example.domain.spi.port;
import java.util.List;
import com.example.domain.entity.Quote;
import io.smallrye.mutiny.Multi;

public interface QuoteRepository {

    void save(Quote quote);
    Quote findRandom();

    Quote findById(Long Id);

    Multi<Quote> findAll();

}
