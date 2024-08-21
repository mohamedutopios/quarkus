package org.agoncal.quarkus.starting.resource;

import org.agoncal.quarkus.starting.configproperty.AppConfigService;
import org.agoncal.quarkus.starting.configproperty.FeatureConfigService;
import org.agoncal.quarkus.starting.configproperty.GreetingService;
import org.agoncal.quarkus.starting.configproperty.ServerConfigService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/config")
public class ConfigResource {

  @Inject
  AppConfigService appConfigService;

  @Inject
  FeatureConfigService featureConfigService;

  @Inject
  GreetingService greetingService;

  @Inject
  ServerConfigService serverConfigService;

  @GET
  @Path("/message")
  @Produces(MediaType.TEXT_PLAIN)
  public String getMessage() {
    return "Message: " + appConfigService.getMessage() + "\nVersion: " + appConfigService.getVersion();
  }

  @GET
  @Path("/feature")
  @Produces(MediaType.TEXT_PLAIN)
  public String getFeatureStatus() {
    return "Max Items: " + featureConfigService.getMaxItems() +
      "\nFeature Enabled: " + featureConfigService.isFeatureEnabled();
  }

  @GET
  @Path("/greeting")
  @Produces(MediaType.TEXT_PLAIN)
  public String getGreeting() {
    return greetingService.getGreeting();
  }

  @GET
  @Path("/server")
  @Produces(MediaType.TEXT_PLAIN)
  public String getServerDetails() {
    return serverConfigService.getServerDetails();
  }
}
