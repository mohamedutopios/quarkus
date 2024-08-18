package com.example;

import com.example.clientapi.ContactApiService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Arrays;
import java.util.List;

@Path("/client-contacts")
public class ContactClientResource {

    @Inject
    @RestClient
    private ContactApiService contactApiService;

    @Retry(maxRetries = 5)
    @Fallback(fallbackMethod = "defaultContact")
    @CircuitBreaker(requestVolumeThreshold = 1, failureRatio = 1, delay = 20000)
    @GET
    public List<Object> get() {
        return contactApiService.getContacts();
    }

    public List<Object> defaultContact() {
        return Arrays.asList("test", "test 2");
    }
}
