package org.agoncal.quarkus.starting.resource;

import org.agoncal.quarkus.starting.singleton.CounterService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resource1")
public class Resource1 {

  @Inject
  CounterService counterService;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String incrementAndGet() {
    return "Resource 1: Counter = " + counterService.incrementAndGet();
  }
}
