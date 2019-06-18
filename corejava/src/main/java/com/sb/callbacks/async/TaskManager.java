package com.sb.callbacks.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskManager {
  ExecutorService executor = Executors.newWorkStealingPool();

  public void execute(Task task, Callback cb) {
    executor.submit(() -> {
      try {
        task.perform();
        cb.onSuccess();
      } catch (Exception e) {
        cb.onError();
      }
    });
  }
}
