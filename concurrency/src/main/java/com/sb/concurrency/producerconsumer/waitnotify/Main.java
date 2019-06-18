package com.sb.concurrency.producerconsumer.waitnotify;

public class Main {
  public static void main(String[] args) {
    Queue queue = new Queue2();

    Thread t1 = new Thread(new Producer(queue));
    Thread t2 = new Thread(new Consumer(queue));
    Thread t3 = new Thread(new Consumer(queue));

    t1.setName("Producer");
    t2.setName("Consumer1");
    t3.setName("Consumer2");

    t2.start();
    t3.start();
    t1.start();
  }
}
