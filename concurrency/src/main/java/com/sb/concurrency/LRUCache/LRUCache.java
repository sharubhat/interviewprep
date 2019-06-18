package com.sb.concurrency.LRUCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<K, V> {

  private ConcurrentLinkedQueue<K> concurrentLinkedQueue = new ConcurrentLinkedQueue<K>();

  private ConcurrentHashMap<K, V> concurrentHashMap = new ConcurrentHashMap<K, V>();

  private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  private Lock readLock = readWriteLock.readLock();

  private Lock writeLock = readWriteLock.writeLock();

  int maxSize = 0;

  public LRUCache(final int MAX_SIZE) {
    this.maxSize = MAX_SIZE;
  }

  public V get(K key) {
    V v = null;
    readLock.lock();
    try {
      v = concurrentHashMap.get(key);
      if (v != null) {
        concurrentLinkedQueue.remove(key);
        concurrentLinkedQueue.add(key);
      }
    } finally {
      readLock.unlock();
    }
    return v;
  }

  public V remove(K key) {
    V v = null;
    writeLock.lock();
    try {
      if (concurrentHashMap.contains(key)) {
        v = concurrentHashMap.remove(key);
        concurrentLinkedQueue.remove(key);
      }
    } finally {
      writeLock.unlock();
    }
    return v;
  }

  public V add(K key, V value) {
    writeLock.lock();
    try {
      if (concurrentHashMap.contains(key)) {
        concurrentLinkedQueue.remove(key);
      }
      while (concurrentLinkedQueue.size() >= maxSize) {
        K queueKey = concurrentLinkedQueue.poll();
        concurrentHashMap.remove(queueKey);
      }
      concurrentLinkedQueue.add(key);
      concurrentHashMap.put(key, value);
    } finally {
      writeLock.unlock();
    }
    return value;
  }
}
