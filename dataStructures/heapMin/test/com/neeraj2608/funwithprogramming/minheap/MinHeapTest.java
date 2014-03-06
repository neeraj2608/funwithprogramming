package com.neeraj2608.funwithprogramming.minheap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MinHeapTest{
  MinHeap minHeap;
  int heapSize = 5;
  
  @Before
  public void setup(){
    minHeap = new MinHeap(heapSize);
  }
  
  @Test
  public void testInsert(){
    minHeap.insert(5);
    minHeap.insert(4);
    minHeap.insert(7);
    minHeap.insert(1);
    assertEquals(1, minHeap.deleteMinSiftDown());
  }
  
  @Test(expected=HeapEmptyException.class)
  public void deleteMinThrowsException(){
    minHeap.deleteMinSiftDown();
  }
  
  @Test
  public void testIsEmpty(){
    assertTrue(minHeap.isEmpty());
    minHeap.insert(1);
    assertFalse(minHeap.isEmpty());
  }
  
  @Test
  public void testDeleteMinSiftDown(){
    minHeap.insert(5);
    minHeap.insert(4);
    minHeap.insert(7);
    minHeap.insert(1);
    assertEquals(1, minHeap.deleteMinSiftDown());
    assertEquals(4, minHeap.deleteMinSiftDown());
    assertEquals(5, minHeap.deleteMinSiftDown());
    assertEquals(7, minHeap.deleteMinSiftDown());
  }
  
  @Test
  public void testDeleteMinSiftUp(){
    minHeap.insert(5);
    minHeap.insert(4);
    minHeap.insert(7);
    minHeap.insert(1);
    assertEquals(1, minHeap.deleteMinSiftUp());
    assertEquals(4, minHeap.deleteMinSiftUp());
    assertEquals(5, minHeap.deleteMinSiftUp());
    assertEquals(7, minHeap.deleteMinSiftUp());
  }
  
}
