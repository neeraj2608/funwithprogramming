package com.neeraj2608.funwithprogramming.minheap;

import static org.junit.Assert.assertEquals;

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
  
  //TODO: add tests for remaining methods

}
