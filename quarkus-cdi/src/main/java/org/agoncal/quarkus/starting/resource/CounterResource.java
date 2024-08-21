package org.agoncal.quarkus.starting.resource;

import org.agoncal.quarkus.starting.scoped.GlobalRequestCounterService;
import org.agoncal.quarkus.starting.scoped.RequestCounterService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/counter")
public class CounterResource {

  @Inject
  GlobalRequestCounterService globalRequestCounterService;

  @Inject
  RequestCounterService requestCounterService;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String count() {
    globalRequestCounterService.increment();
    requestCounterService.increment();

    return "Global Count: " + globalRequestCounterService.getCount() +
      "\nRequest Count: " + requestCounterService.getCount();
  }
}
