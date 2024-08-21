package org.agoncal.quarkus.starting.configproperty;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ServerConfigService {

  @Inject
  @ConfigProperty(name = "app.nested.config.host")
  String host;

  @Inject
  @ConfigProperty(name = "app.nested.config.port")
  int port;

  public String getServerDetails() {
    return "Server is running on " + host + ":" + port;
  }
}
