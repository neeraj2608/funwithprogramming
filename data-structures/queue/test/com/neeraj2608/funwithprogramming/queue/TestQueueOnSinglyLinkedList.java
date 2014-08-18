package com.neeraj2608.funwithprogramming.queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestQueueOnSinglyLinkedList{
  QueueOnSinglyLinkedList q;

  @Before
  public void setUp() throws Exception{
    q = new QueueOnSinglyLinkedList();
  }

  @Test
  public void testDeQueue(){
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(2));
    q.enQueue(new Integer(3));
    q.enQueue(new Integer(4));
    assertEquals(1, q.deQueue());
    assertEquals(2, q.deQueue());
    assertEquals(3, q.deQueue());
    assertEquals(4, q.deQueue());
  }
  
  @Test(expected=RuntimeException.class)
  public void testEmptyQueueThrowsException(){
    q.deQueue();
  }
  
  @Test(expected=RuntimeException.class)
  public void testDeQueueThrowsException(){
    q.enQueue(new Integer(1));
    q.enQueue(new Integer(2));
    q.deQueue();
    q.deQueue();
    q.deQueue();
  }
  
  @Test
  public void testIsEmpty(){
    assertTrue(q.isEmpty());
    q.enQueue(new Integer(1));
    assertFalse(q.isEmpty());
  }

}
