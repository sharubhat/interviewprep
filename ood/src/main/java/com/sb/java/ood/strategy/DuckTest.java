package com.sb.java.ood.strategy;

public class DuckTest {
  public static void main(String[] args) {
    Duck duck = new MullardDuck();
    duck.setFlyBehavior(new FlyWithWings());
    duck.setQuackBehavior(new Squeak());
    duck.performFly();
    duck.performQuack();
    duck.display();
  }
}
