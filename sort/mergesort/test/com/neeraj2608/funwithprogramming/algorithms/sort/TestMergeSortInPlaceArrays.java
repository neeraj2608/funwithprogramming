package com.neeraj2608.funwithprogramming.algorithms.sort;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMergeSortInPlaceArrays{

  @Test
  public void testMergeSortInPlace(){
    int[] arr1 = {1,2,3,4,5};
    int[] arr3 = {1,2,3,4,5};
    MergeSortInPlaceArrays.mergeSortInPlace(arr3);
    assertArrayEquals(arr1, arr3);
    
    arr3 = new int[]{5,4,3,2,1};
    MergeSortInPlaceArrays.mergeSortInPlace(arr3);
    assertArrayEquals(arr1, arr3);
    
    arr1 = new int[]{-2,-1,0,3,5};
    arr3 = new int[]{5,3,-1,0,-2};
    MergeSortInPlaceArrays.mergeSortInPlace(arr3);
    
    arr1 = new int[]{-6,-5,0,2,2,7,9};
    arr3 = new int[]{-5,2,-6,2,7,9,0};
    MergeSortInPlaceArrays.mergeSortInPlace(arr3);
    assertArrayEquals(arr1, arr3);
    
    arr1 = new int[]{-17,-2,6,12,20,45};
    arr3 = new int[]{6,45,-2,20,-17,12};
    MergeSortInPlaceArrays.mergeSortInPlace(arr3);
    assertArrayEquals(arr1, arr3);
  }

}
