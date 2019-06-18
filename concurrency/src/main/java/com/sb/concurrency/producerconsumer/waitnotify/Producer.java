package com.sb.concurrency.producerconsumer.waitnotify;

import java.util.Random;

public class Producer implements Runnable {
  private Queue queue;

  public Producer(Queue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    Random random = new Random();
    for (int i = 0; i < 1000; i++) {
      queue.add(i);
    }
  }
}
