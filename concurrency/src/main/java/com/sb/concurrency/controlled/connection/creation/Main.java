package com.sb.concurrency.controlled.connection.creation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main implements Runnable {
  private static Logger logger = LoggerFactory.getLogger(Main.class);

  private ConnectionManager rm;

  public Main(ConnectionManager rm) {
    this.rm = rm;
  }

  @Override
  public void run() {
    Optional<Integer> resource = rm.getNewInstance();
    if (resource.isPresent())
      logger.debug("Got connection " + resource.get() + " : " + Thread.currentThread().getName());
    else {
      logger.debug("Someone else created a connection " + Thread.currentThread().getName());
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ExecutorService e = Executors.newFixedThreadPool(5);
    ConnectionManager rm = new ConnectionManager();

    for (int i = 0; i < 10; i++) {
      e.execute(new Main(rm));
      TimeUnit.SECONDS.sleep(1);
    }

    e.awaitTermination(5000, TimeUnit.MILLISECONDS);
    e.shutdown();
  }
}
