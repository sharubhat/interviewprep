package com.sb.callbacks.observableway;

public class EventListener implements Listener {

  @Override
  public void onEvent() {
    System.out.println("Performing callback after task completion.");
  }
}
