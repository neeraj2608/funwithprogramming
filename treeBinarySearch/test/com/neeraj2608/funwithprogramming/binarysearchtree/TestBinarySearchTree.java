package com.neeraj2608.funwithprogramming.binarysearchtree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBinarySearchTree{

  BinarySearchTree bst;
  
  @Before
  public void setup(){
    bst = new BinarySearchTree();
  }
  
  @Test
  public void testSearch(){
    bst.insert(5);
    bst.insert(3);
    bst.insert(8);
    bst.insert(6);
    bst.insert(9);
    assertTrue(bst.search(5));
    assertTrue(bst.search(9));
    assertFalse(bst.search(0));
    assertFalse(bst.search(-5));
  }
  
  @Test
  public void testDelete(){
    bst.insert(5);
    bst.insert(3);
    bst.insert(8);
    bst.insert(6);
    bst.insert(7);
    bst.insert(9);
    assertTrue(bst.search(5));
    bst.delete(5);
    assertFalse(bst.search(5));
    bst.delete(6);
    assertFalse(bst.search(6));
    assertTrue(bst.search(3));
  }
  
  @Test
  public void testIsEmpty(){
    assertTrue(bst.isEmpty());
    bst.insert(5);
    assertFalse(bst.isEmpty());
    bst.delete(5);
    assertTrue(bst.isEmpty());
  } 
  
  @Test
  public void testMax(){
    bst.insert(5);
    bst.insert(3);
    bst.insert(8);
    bst.insert(6);
    bst.insert(7);
    bst.insert(9);
    assertEquals(9, bst.max());
    bst.delete(9);
    assertEquals(8, bst.max());
  }
  
  @Test
  public void testMin(){
    bst.insert(5);
    bst.insert(3);
    bst.insert(8);
    bst.insert(6);
    bst.insert(7);
    bst.insert(9);
    assertEquals(3, bst.min());
    bst.delete(3);
    assertEquals(5, bst.min());
  }

}
