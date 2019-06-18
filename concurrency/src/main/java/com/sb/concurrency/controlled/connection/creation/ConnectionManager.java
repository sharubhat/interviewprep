package com.sb.concurrency.controlled.connection.creation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionManager {

  private static Logger log = LoggerFactory.getLogger(ConnectionManager.class);
  private AtomicBoolean requestingNewInstance = new AtomicBoolean(false);
//  private Lock createInstanceLock = new ReentrantLock();
  private Lock waitForInstanceLock = new ReentrantLock(true);
  private Condition waitCondition = waitForInstanceLock.newCondition();
  private Connection connection = new Connection();

  public Optional<Integer> getNewInstance() {
    if (!requestingNewInstance.getAndSet(true)) {
//      createInstanceLock.lock();
      try {
        Optional<Integer> instance =  Optional.of(connection.create());
        waitForInstanceLock.lock();
        waitCondition.signalAll();
        return instance;
      } finally {
        requestingNewInstance.set(false);
//        createInstanceLock.unlock();
        waitForInstanceLock.unlock();
      }
    } else {
      waitForInstanceLock.lock();
      log.debug(Thread.currentThread().getName() + " acquired lock");
      try {
        waitCondition.awaitUninterruptibly();
      } finally{
        log.debug(Thread.currentThread().getName() + " released lock");
        waitForInstanceLock.unlock();
      }
    }
    return Optional.empty();
  }
}
