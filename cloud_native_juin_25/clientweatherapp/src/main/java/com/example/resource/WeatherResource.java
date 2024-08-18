package com.example.resource;

import com.example.apiservice.WeatherServiceClient;
import com.example.dto.WeatherInfo;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/weather")
public class WeatherResource {

    @Inject
    @RestClient
    private WeatherServiceClient weatherServiceClient;

    @GET
    @Path("/{city}")
    @Fallback(fallbackMethod = "genericWeatherInfo")
    @CircuitBreaker(requestVolumeThreshold = 3, failureRatio = 1, delay = 120000)
    @WithSpan("WeatherResource.get")
    public WeatherInfo get(@PathParam("city") String cityname) {
        Log.debug("get weather info from api");
        return weatherServiceClient.getWeatherInfo(cityname);
    }

    @WithSpan("WeatherResource.genericWeatherInfo")
    public WeatherInfo genericWeatherInfo(String cityname) {
        Log.debug("get generic weather info");
        return WeatherInfo.builder().cityName(cityname).message("Informations non disponibles").build();
    }
}
