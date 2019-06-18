package com.sb.concurrency.alternativeprinting;

/**
 * This is only good if there are strictly only two threads printing alternatively.
 * Also, wait and notify should be called on same object. That's the reason behind using 'monitor' object.
 * If `WaitNotifyPrinter.class` is used for synchronization, it may appear as if the code would work,
 * but will result in IllegalMonitorState exception gets thrown because each thread will acquire it's own
 * intrinsic lock but not the same lock.
 */
public class WaitNotifyPrinter implements Runnable {
  private boolean isEvenPrinter;
  private final int stopCount;

  // this works because the number is static and is shared by both threads.
  private static volatile int number = 1;
  private static final Object monitor = new Object();

  public WaitNotifyPrinter(boolean isEvenPrinter, int stopCount) {
    this.isEvenPrinter = isEvenPrinter;
    this.stopCount = stopCount;
  }

  @Override
  public void run() {
    String currentThread = Thread.currentThread().getName();
    while (number <= stopCount) {
      synchronized (monitor) {
        if (number <= stopCount) {
          if ((isEvenPrinter && number % 2 == 0) || (!isEvenPrinter && number % 2 != 0)) {
            System.out.printf("%4s - %02d %n", currentThread, number);
            number++;
            monitor.notify();
          } else {
            try {
              monitor.wait();
            } catch (InterruptedException e) {
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    Thread t1 = new Thread(new WaitNotifyPrinter(false, 50), "Odd ");
    Thread t2 = new Thread(new WaitNotifyPrinter(true, 50), "Even");

    t1.start();
    t2.start();
  }
}
