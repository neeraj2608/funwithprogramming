package com.neeraj2608.funwithprogramming.linkedlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class TestDoublyLinkedList{

  DoublyLinkedList dll;
  
  @Before
  public void setup(){
    dll = new DoublyLinkedList();
  }
  
  @Test
  public void testisEmpty(){
    assertTrue(dll.isEmpty());
    dll.insert(new Integer(1));
    assertFalse(dll.isEmpty());
  }
  
  @Test
  public void testRemove(){
    dll.insert(new String("a"));
    dll.insert(new String("b"));
    dll.insert(new String("c"));
    assertEquals("c",dll.remove());
    assertEquals("b",dll.remove());
    assertEquals("a",dll.remove());
  }
  
  @Test(expected=RuntimeException.class)
  public void testRemoveThrowsException(){
    dll.remove();
  }

}
