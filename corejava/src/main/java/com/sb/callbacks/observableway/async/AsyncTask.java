package com.sb.callbacks.observableway.async;

import com.sb.callbacks.observableway.EventListener;
import com.sb.callbacks.observableway.Listener;
import com.sb.callbacks.observableway.Task;

import java.util.concurrent.TimeUnit;

public class AsyncTask implements Task {
  private Listener listener;

  @Override
  public void registerEventListener(Listener listener) {
    this.listener = listener;
  }

  @Override
  public void perform() {
    new Thread(() -> {
      System.out.println("Performing task before asynchronous callback");

      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        // do nothing
      }
      // check if listener is registered.
      if (this.listener != null) {

        // invoke the callback method of Listener
        listener.onEvent();
      }
    }).start();

  }

  public static void main(String[] args) {
    Task task = new AsyncTask();
    Listener listener = new EventListener();
    task.registerEventListener(listener);
    task.perform();
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      // do nothing
    }
    System.out.println("Not waiting for task to complete.");
  }
}
