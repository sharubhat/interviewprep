package com.sb.callbacks.async;

@FunctionalInterface
public interface Task {
  void perform() throws Exception;
}
