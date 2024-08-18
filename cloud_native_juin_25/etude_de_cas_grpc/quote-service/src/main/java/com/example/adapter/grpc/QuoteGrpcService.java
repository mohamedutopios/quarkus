package com.example.adapter.grpc;

import com.example.domain.QuoteService;
import com.example.domain.entity.Quote;
import com.example.domain.spi.port.QuoteRepository;
import com.google.protobuf.Empty;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import quoteProto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@GrpcService
public class QuoteGrpcService implements QuoteGrpc {

    private final QuoteService quoteService;


//    private ExecutorService executor = Executors.newFixedThreadPool(4);

    public QuoteGrpcService(QuoteRepository quoteRepository) {
        this.quoteService = new QuoteService(quoteRepository);
    }

    @Override
    public Uni<QuoteGet> getQuoteById(QuoteGetById request) {
        Quote quote = quoteService.findById(request.getId());
        return Uni.createFrom().item(()->setQuoteGet(quote));
    }

    @Override
    public Uni<QuoteGet> postQuote(QuotePost request) {
//        return Uni.createFrom().item(request).emitOn(executor).map((r)->{
//            Quote quote = quoteService.save(r.getContent(),r.getAuthorId());
//            return setQuoteGet(quote);
//        });
        Quote quote = quoteService.save(request.getContent(),request.getAuthorId());
        return Uni.createFrom().item(()->setQuoteGet(quote));
    }
    private QuoteGet setQuoteGet (Quote quote){
        return QuoteGet.newBuilder()
                .setId(quote.getId())
                .setContent(quote.getContent())
                .setAuthorId(quote.getAuthorId())
                .build();
    }
}
