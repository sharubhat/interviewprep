package com.sb.concurrency.producerconsumer.waitnotify;

public class Consumer implements Runnable {
  private Queue queue;

  public Consumer(Queue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    for (int i = 0 ; i < 1000; i++) {
      System.out.println(Thread.currentThread().getName() + " - Removed : " + queue.remove());
    }
  }
}
