package org.agoncal.quarkus.starting.singleton;

import javax.inject.Singleton;

@Singleton
public class CounterService {
  private int counter = 0;

  public int incrementAndGet() {
    counter++;
    return counter;
  }

  public int getCounter() {
    return counter;
  }
}
