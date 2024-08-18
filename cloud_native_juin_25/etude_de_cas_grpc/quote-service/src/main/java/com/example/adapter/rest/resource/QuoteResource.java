package com.example.adapter.rest.resource;

import com.example.adapter.rest.clientservice.ClientAuthorService;
import com.example.adapter.rest.dto.AuthorDTO;
import com.example.adapter.rest.dto.QuoteDTO;
import com.example.domain.QuoteService;
import com.example.domain.entity.Quote;
import com.example.domain.spi.port.QuoteRepository;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/v1/quote")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuoteResource {

    private final QuoteRepository quoteRepository;
    private final QuoteService quoteService;

    @RestClient
    @Inject
    private ClientAuthorService clientAuthorService;

    public QuoteResource(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
        this.quoteService = new QuoteService(quoteRepository);
    }

    @POST
    @Transactional
    public QuoteDTO post(Quote quote) {
       Quote savedQuote = quoteService.save(quote.getContent(), quote.getAuthorId());
       return QuoteDTO.builder().quote(savedQuote).authorDTO(getAuthor(quote.getAuthorId())).build();
    }
    @GET
    @Path("/{id}")
    public QuoteDTO get(@PathParam("id") Long id) {
        Quote quote = quoteService.findById(id);
        QuoteDTO quoteDTO = QuoteDTO.builder().quote(quote).authorDTO(getAuthor(quote.getAuthorId())).build();
        return quoteDTO;
    }

    @CircuitBreaker(requestVolumeThreshold = 3, failureRatio = 0.5, delay = 120000)
    @Fallback(fallbackMethod = "getGenericAuthorDto")
    public AuthorDTO getAuthor(Long authorId) {
        return clientAuthorService.get(authorId);
    }

    public AuthorDTO getGenericAuthorDto(Long authorId) {
        return AuthorDTO.builder().firstname("generic").firstname("generic").id(authorId).build();
    }

    @Path("/all")
    @GET
    public Multi<QuoteDTO> get() {
        return quoteService.getAll().onItem().transform(q -> {
            return QuoteDTO.builder().quote(q).authorDTO(getAuthor(q.getAuthorId())).build();
        });
    }
}
