package com.sb.callbacks.async;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class TaskProducer implements Runnable {
  private TaskManager manager;
  private Random random = new Random();

  public TaskProducer(TaskManager taskManager) {
    manager = taskManager;
  }

  @Override
  public void run() {
    IntStream.range(1, 3)
        .forEach(
            i -> {
              System.out.println(Thread.currentThread().getName() + " starting to perform.");
              manager.execute(
                  () -> {
                    System.out.println(Thread.currentThread().getName() + " performing.");
                    if (random.nextBoolean()) {
                      throw new Exception("Failed");
                    }
                  },
                  new Callback() {
                    @Override
                    public void onSuccess() {
                      System.out.println(Thread.currentThread().getName() + " succeeded.");
                    }

                    @Override
                    public void onError() {
                      System.out.println(Thread.currentThread().getName() + " failed.");
                    }
                  });
              System.out.println(Thread.currentThread().getName() + " done submitting.");
            });
  }
}
