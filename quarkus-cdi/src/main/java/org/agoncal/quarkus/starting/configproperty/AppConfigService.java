package org.agoncal.quarkus.starting.configproperty;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AppConfigService {

  @Inject
  @ConfigProperty(name = "app.message")
  String message;

  @Inject
  @ConfigProperty(name = "app.version")
  String version;

  public String getMessage() {
    return message;
  }

  public String getVersion() {
    return version;
  }
}
