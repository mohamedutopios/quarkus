package com.example.infra.perspanache;

import com.example.infra.perspanache.entity.QuoteEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class QuoteEntityRepository implements PanacheRepository<QuoteEntity> {

    public QuoteEntity findRandom() {
        long rowIndex  = count();
        long rowNumber = (long) Math.ceil(Math.random() * rowIndex);
        PanacheQuery<QuoteEntity> quoteEntityPanacheQuery= findAll();
        //return findAll().page(rowNumber, 1).firstResult();
        return findAll().firstResult();
    }
}
