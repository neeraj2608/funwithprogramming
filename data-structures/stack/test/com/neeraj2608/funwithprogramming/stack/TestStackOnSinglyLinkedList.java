package com.neeraj2608.funwithprogramming.stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestStackOnSinglyLinkedList{
  StackOnSinglyLinkedList sll;
  int stackSize = 5;
  
  @Before
  public void setup(){
    sll = new StackOnSinglyLinkedList(stackSize);
  }
  
  @Test
  public void testIsEmpty(){
    assertTrue(sll.isEmpty());
    sll.push(new Integer(1));
    assertFalse(sll.isEmpty());
  }
  
  @Test
  public void testIsFull(){
    assertFalse(sll.isFull());
    sll.push(new Integer(1));
    sll.push(new Integer(1));
    sll.push(new Integer(1));
    sll.push(new Integer(1));
    sll.push(new Integer(1));
    assertTrue(sll.isFull());
  }
  
  @Test(expected=RuntimeException.class)
  public void testPushThrowsException(){
    sll.push(new Integer(1));
    sll.push(new Integer(1));
    sll.push(new Integer(1));
    sll.push(new Integer(1));
    sll.push(new Integer(1));
    sll.push(new Integer(1));
  }
  
  @Test
  public void testPop(){
    sll.push(new Integer(1));
    sll.push(new Integer(2));
    assertEquals(2, sll.pop());
    assertEquals(1, sll.pop());
  }
  
  @Test
  public void testPeek(){
    sll.push(new Integer(1));
    assertEquals(1, sll.peek());
    assertFalse(sll.isEmpty());
  }
  
  @Test(expected=RuntimeException.class)
  public void testPopThrowsException(){
    sll.pop();
  }
  
  @Test(expected=RuntimeException.class)
  public void testPeekThrowsException(){
    sll.peek();
  }

}
