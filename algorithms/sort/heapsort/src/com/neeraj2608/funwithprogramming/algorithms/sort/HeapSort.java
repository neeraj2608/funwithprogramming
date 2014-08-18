package com.neeraj2608.funwithprogramming.algorithms.sort;

import com.neeraj2608.funwithprogramming.minheap.MinHeap;

public class HeapSort{
  public static void heapSort(int[] arr){
    MinHeap minHeap = new MinHeap(arr.length);
    for(int i: arr){
      minHeap.insert(i);
    }
    for(int i=0;i<arr.length;++i)
      arr[i] = minHeap.deleteMinSiftDown();
  }
}
