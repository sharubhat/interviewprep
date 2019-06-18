package com.sb.concurrency.diningphilosophers;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class DiningPhilosophers {
  private static class Chopstick {
    int index;
    private Lock lock = new ReentrantLock();

    public Chopstick(int index) {
      this.index = index;
    }

    public void pickup() {
      lock.lock();
    }

    public boolean tryPickUp() {
      return lock.tryLock();
    }

    public void putDown() {
      lock.unlock();
    }
  }

  private static class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;

    private int index;

    public Philosopher(Chopstick left, Chopstick right, int index) {
      this.left = left;
      this.right = right;
      this.index = index;
    }

    @Override
    public void run() {
      Random random = new Random();
      while (true) {
        try {
          int thinkTime = random.nextInt(500);
          print(index, "Thinking for " + thinkTime + " ms");
          TimeUnit.MILLISECONDS.sleep(thinkTime);
        } catch (InterruptedException e) {
          // do nothing
        }
        if (left.tryPickUp()) {
          try {
            print(index, "Acquired left chopstick " + left.index);
            if (right.tryPickUp()) {
              try {
                print(index, "Acquired right chopstick " + right.index);
                try {
                  int eatTime = random.nextInt(500);
                  print(index, "Eating for " + eatTime + " ms");
                  TimeUnit.MILLISECONDS.sleep(eatTime);
                } catch (InterruptedException e) {
                  print(index, "Woke up from day dreaming ");
                }
                print(index, "Putting down right chopstick " + right.index);
              } finally {
                right.putDown();
              }
            } else {
              print(index, "Failed to get right chopstick, giving up left");
            }
            print(index, "Putting down left chopstick " + left.index);
          } finally {
            left.putDown();
          }
        } else {
          print(index, "Failed to get left chopstick, starving");
        }
      }
    }
  }

  private static void print(int index, String s) {
    System.out.println(System.nanoTime() + "-" + index + ": " + s);
  }

  public static void main(String[] args) {
    Chopstick[] chops = new Chopstick[5];
    IntStream.range(0, 5).forEach(i -> chops[i] = new Chopstick(i + 1));
    Philosopher p1 = new Philosopher(chops[0], chops[1], 1);
    Philosopher p2 = new Philosopher(chops[1], chops[2], 2);
    Philosopher p3 = new Philosopher(chops[2], chops[3], 3);
    Philosopher p4 = new Philosopher(chops[3], chops[4], 4);
    Philosopher p5 = new Philosopher(chops[4], chops[0], 5);

    new Thread(p1).start();
    new Thread(p2).start();
    new Thread(p3).start();
    new Thread(p4).start();
    new Thread(p5).start();
  }
}
