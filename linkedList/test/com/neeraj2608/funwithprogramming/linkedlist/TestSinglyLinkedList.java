package com.neeraj2608.funwithprogramming.linkedlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class TestSinglyLinkedList{

  SinglyLinkedList sll;
  SinglyLinkedList sll1;
  
  @Before
  public void setup(){
    sll = new SinglyLinkedList();
    sll1 = new SinglyLinkedList();
  }
  
  @Test
  public void testisEmpty(){
    assertTrue(sll.isEmpty());
    sll.insert(new Integer(1));
    assertFalse(sll.isEmpty());
  }
  
  @Test
  public void testRemove(){
    sll1.insert(new String("a"));
    sll1.insert(new String("b"));
    sll1.insert(new String("c"));
    assertEquals("c",sll1.remove());
    assertEquals("b",sll1.remove());
    assertEquals("a",sll1.remove());
  }
  
  @Test(expected=RuntimeException.class)
  public void testRemoveThrowsException(){
    sll1.remove();
  }
  
  @Test
  public void testReverse(){
    sll1.insert(new String("a"));
    sll1.insert(new String("b"));
    sll1.insert(new String("c"));
    sll1.reverse();
    assertEquals("a",sll1.remove());
    assertEquals("b",sll1.remove());
    assertEquals("c",sll1.remove());
  }

}
