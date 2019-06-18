package com.sb.concurrency.producerconsumer.waitnotify;

public interface Queue<E> {
  void add(E e);
  E remove();
}
