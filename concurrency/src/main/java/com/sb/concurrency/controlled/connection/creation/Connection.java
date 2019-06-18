package com.sb.concurrency.controlled.connection.creation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Connection {
  private static Logger log = LoggerFactory.getLogger(Connection.class);

  public Integer create() {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    log.debug("Creating connection : " +
        Thread.currentThread().getName());
    Random r = new Random();
    Integer num = r.nextInt();

    log.debug("Returning connection " + num + " : " +
        Thread.currentThread().getName());
    return num;
  }
}
