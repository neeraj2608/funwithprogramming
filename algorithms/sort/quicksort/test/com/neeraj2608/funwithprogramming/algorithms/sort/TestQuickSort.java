package com.neeraj2608.funwithprogramming.algorithms.sort;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.neeraj2608.funwithprogramming.algorithms.sort.QuickSort;


public class TestQuickSort{
  
  @Test
  public void testQuicksort(){
    int[] arr1 = {1,2,3,4,5};
    int[] arr3 = {1,2,3,4,5};
    QuickSort.quicksort(arr3);
    assertArrayEquals(arr1, arr3);
    
    arr3 = new int[]{5,4,3,2,1};
    QuickSort.quicksort(arr3);
    assertArrayEquals(arr1, arr3);
    
    arr1 = new int[]{-6,-5,0,2,2,7,9};
    arr3 = new int[]{-5,2,-6,2,7,9,0};
    QuickSort.quicksort(arr3);
    assertArrayEquals(arr1, arr3);
  }

}
