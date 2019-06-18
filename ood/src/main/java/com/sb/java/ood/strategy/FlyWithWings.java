package com.sb.java.ood.strategy;

public class FlyWithWings implements FlyBehavior {
  @Override
  public void fly() {
    System.out.println("Flying with wings");
  }
}
