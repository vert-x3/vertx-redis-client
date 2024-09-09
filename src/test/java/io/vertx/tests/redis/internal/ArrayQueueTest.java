package io.vertx.tests.redis.internal;

import io.vertx.redis.client.impl.ArrayQueue;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArrayQueueTest {

  @Test
  @Ignore("This test is very CPU intensive and causes trouble on CI")
  public void testOverflow() {
    ArrayQueue arrayQueue = new ArrayQueue(10);

    arrayQueue.offer(0);
    for (int i = 0; i < Integer.MAX_VALUE; i++) {
      arrayQueue.offer(0);
      arrayQueue.poll();
      arrayQueue.offer(0);
      arrayQueue.poll();
    }
    assertEquals(9, arrayQueue.freeSlots());
    assertEquals(0, (int) arrayQueue.poll());
    assertEquals(10, arrayQueue.freeSlots());
    //Overflow for int back
    arrayQueue.offer(1);
    //Overflow for int front
    assertEquals(1, (int) arrayQueue.poll());
  }

  @Test
  public void testPollNSE() {
    ArrayQueue arrayQueue = new ArrayQueue(10);
    arrayQueue.poll();
    arrayQueue.offer("a");
    assertNotNull(arrayQueue.poll());
  }
}
