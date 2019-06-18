package com.sb.concurrency.synchronizationobjects.semaphores;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreDemo {
  public static void main(String[] args) {
    Semaphore sem =  new Semaphore(1);
    new Thread(new Incrementor(sem, "Inc")).start();
    new Thread(new Decrementor(sem, "Dec")).start();
  }

  static class Shared {
    static int count;
  }

  private static class Incrementor implements Runnable {
    private String name;
    private Semaphore sem;

    Incrementor(Semaphore sem, String name) {
      this.name = name;
      this.sem = sem;
    }

    @Override
    public void run() {
      System.out.println("Starting " + name);
      try {
        // get permit, equivalent of synchronized
        sem.acquire();
        IntStream.rangeClosed(1, 5).forEach(i -> Shared.count++);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        sem.release();
      }
    }
  }

  private static class Decrementor implements Runnable {
    private String name;
    private Semaphore sem;

    Decrementor(Semaphore sem, String name) {
      this.sem = sem;
      this.name = name;
    }

    @Override
    public void run() {
      try {
        sem.acquire();
        IntStream.rangeClosed(1, 5).forEach(i -> Shared.count--);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally{
        sem.release();

      }
    }
  }
}
