package com.example.apiservice;

import com.example.dto.WeatherInfo;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/weather")
public interface WeatherServiceClient {

    @GET
    WeatherInfo getWeatherInfo(@QueryParam("city") String cityname);
}
