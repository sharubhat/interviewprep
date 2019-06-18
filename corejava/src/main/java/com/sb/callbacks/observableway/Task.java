package com.sb.callbacks.observableway;

public interface Task {
  void registerEventListener(Listener listener);
  void perform();
}
