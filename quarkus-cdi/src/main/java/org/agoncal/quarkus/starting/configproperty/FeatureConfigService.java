package org.agoncal.quarkus.starting.configproperty;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FeatureConfigService {

  @Inject
  @ConfigProperty(name = "app.max-items")
  int maxItems;

  @Inject
  @ConfigProperty(name = "app.feature-enabled")
  boolean featureEnabled;

  public int getMaxItems() {
    return maxItems;
  }

  public boolean isFeatureEnabled() {
    return featureEnabled;
  }
}
