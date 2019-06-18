package com.sb.concurrency.producerconsumer.waitnotify;

// Blocking queue using linkedlist with no bounds
public class Queue2<E> implements Queue<E> {
  private Node start;
  private Node end;
  int size = 0;

  private static class Node<E> {
    E e;
    Node next;
    Node prev;

    public Node() {
    }

    public Node(E e) {
      this.e = e;
    }
  }

  public Queue2() {
    start = new Node();
    end = new Node();
    start.next = end;
    end.prev = start;
  }

  @Override
  public synchronized void add(E e) {
    addElement(e);
    size++;
    notifyAll();
  }

  @Override
  public synchronized E remove() {
    if (size > 0) {
      E e = removeElement();
      size--;
      notifyAll();
      return e;
    } else {
      try {
        wait();
      } catch (InterruptedException e1) {
      }
      return remove();
    }
  }

  private void addElement(E e) {
    Node tmp = new Node(e);
    Node prev = end.prev;
    prev.next = tmp;
    tmp.prev = prev;
    tmp.next = end;
    end.prev = tmp;
  }

  private E removeElement() {
    E e = (E)start.next.e;
    Node next = start.next.next;
    start.next = next;
    next.prev = start;
    return e;
  }
}