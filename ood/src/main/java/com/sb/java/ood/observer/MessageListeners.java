package com.sb.java.ood.observer;

public class MessageListeners implements Observer {
  private MessageBroadcaster observable;

  public MessageListeners(MessageBroadcaster observable) {
    this.observable = observable;
  }

  @Override
  public void update() {
    observable.getState();
  }
}
