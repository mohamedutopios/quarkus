package org.agoncal.quarkus.starting.configproperty;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GreetingService {

  @Inject
  @ConfigProperty(name = "app.default.greeting", defaultValue = "Hello, World!")
  String greeting;

  public String getGreeting() {
    return greeting;
  }
}
