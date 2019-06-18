package com.sb.callbacks.observableway.sync;

import com.sb.callbacks.observableway.Listener;
import com.sb.callbacks.observableway.EventListener;
import com.sb.callbacks.observableway.Task;

import java.util.concurrent.TimeUnit;

public class SyncTask implements Task {
  private Listener listener;

  @Override
  public void registerEventListener(Listener listener) {
    this.listener = listener;
  }

  @Override
  public void perform() {
    System.out.println("Performing task before synchronous callback.");

    try {
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      // do nothing
    }
    // check if listener is registered.
    if (this.listener != null) {

      // invoke the callback method of Listener
      listener.onEvent();
    }
  }

  public static void main(String[] args) {
    Task task = new SyncTask();
    Listener listener = new EventListener();
    task.registerEventListener(listener);
    task.perform();
    System.out.println("Continuing task after synchronous callback.");
  }
}
