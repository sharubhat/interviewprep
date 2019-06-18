package com.sb.java.ood.strategy;

public class MullardDuck extends Duck {
  public MullardDuck() {
    quackBehavior = new Squeak();
    flyBehavior = new FlyWithWings();
  }

  public void display() {
    System.out.println("Looks like mullard duck");
  }
}
