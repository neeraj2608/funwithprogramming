package com.neeraj2608.funwithprogramming.algorithms.sort;

public class InsertionSort{
  public static void insertionSort(int[] arr){
    for(int i=1;i<arr.length;++i){
      for(int j=i-1;j>=0;--j){
        if(arr[j]>arr[j+1])
          swapInt(arr,j,j+1);
      }
    }
  }
  
  private static void swapInt(int[] arr, int start, int end){
    int temp = arr[start];
    arr[start] = arr[end];
    arr[end] = temp;
  }
}
