package com.sb.concurrency.producerconsumer.waitnotify;

/*
  Here it's okay to call wait notify directly because both producer and consumer are sharing the same
  queue. This is different from WaitNotifyPrinter where the client who is creating the thread is unaware
  and should not be concerned with how the synchronization is handled within the printer. However,
  synchronizing on a private static object is much safer option.
 */
public class Queue1<E> implements Queue<E> {
  private final E[] buffer;
  private int removeIndex;
  int size = 0;

  public Queue1(int size) {
    buffer = (E[])new Object[size];
  }

  @Override
  public synchronized void add(E e) {
    if (size < buffer.length) {
      addElement(e);
      notifyAll();
    } else {
      try {
        wait();
      } catch (InterruptedException e1) {
      }
      add(e);
    }
  }

  @Override
  public synchronized E remove() {
    if (size > 0) {
      return removeElement();
    } else {
      try {
        wait();
      } catch (InterruptedException e1) {
      }
      return remove();
    }
  }

  private void addElement(E e) {
    int insertAt = (removeIndex + size) % buffer.length;
    buffer[insertAt] = e;
    size++;
  }

  private E removeElement() {
    E e = buffer[removeIndex];
    removeIndex = (removeIndex + 1) % buffer.length;
    size--;
    notifyAll();
    return e;
  }
}
