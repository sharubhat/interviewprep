package com.sb.callbacks.async;

import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    TaskManager manager = new TaskManager();
    IntStream.range(1, 4).forEach(i -> new Thread(new TaskProducer(manager)).start());
    System.out.println("Finished submitting all tasks");
  }
}
