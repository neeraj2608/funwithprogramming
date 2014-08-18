package com.neeraj2608.funwithprogramming.queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestQueueOnArray{
  QueueOnArray q;
  int queueSize;

  @Before
  public void setUp() throws Exception{
    queueSize = 5;
    q = new QueueOnArray(queueSize);
  }
  
  @Test(expected=RuntimeException.class)
  public void testEnQueueThrowsException(){
    q.enQueue(new Integer(1));
    q.deQueue();
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(1));
  }
  
  @Test
  public void testDeQueue(){
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(2));
    q.enQueue(new Integer(3));
    assertEquals(1, q.deQueue());
    assertEquals(2, q.deQueue());
    assertEquals(3, q.deQueue());
  }
  
  @Test(expected=RuntimeException.class)
  public void testDeQueueThrowsException(){
    q.deQueue();
  }
  
  @Test
  public void testIsEmpty(){
    assertTrue(q.isEmpty());
    q.enQueue(new Integer(1));
    assertFalse(q.isEmpty());
  }

  @Test
  public void testIsFull(){
    assertFalse(q.isFull());
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(1));
    assertTrue(q.isFull());
  }

}
