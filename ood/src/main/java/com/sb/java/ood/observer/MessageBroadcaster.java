package com.sb.java.ood.observer;

import java.util.ArrayList;
import java.util.List;

public class MessageBroadcaster implements Observable {
  List<Observer> listeners;

  public MessageBroadcaster() {
    this.listeners = new ArrayList<>();
  }

  @Override
  public void registerObserver(Observer observer) {
    listeners.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    listeners.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (Observer o:listeners) {
      o.update();
    }
  }

  public String getState() {
    return "Result";
  }
}
