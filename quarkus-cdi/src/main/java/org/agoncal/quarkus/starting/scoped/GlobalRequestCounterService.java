package org.agoncal.quarkus.starting.scoped;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GlobalRequestCounterService {
  private int globalCounter = 0;

  public void increment() {
    globalCounter++;
  }

  public int getCount() {
    return globalCounter;
  }
}
