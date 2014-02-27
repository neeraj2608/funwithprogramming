package com.neeraj2608.funwithprogramming.algorithms.sort;

public class SelectionSort{
  public static void selectionSort(int[] arr){
    for(int i=0;i<arr.length-1;++i){
      int min = arr[i];
      int swapWith=i;
      for(int j=swapWith;j<arr.length;++j){
        if(arr[j]<min){
          min = arr[j];
          swapWith = j;
        }
      }
      swapInt(arr,i,swapWith);
    }
  }
  
  private static void swapInt(int[] arr, int start, int end){
    int temp = arr[start];
    arr[start] = arr[end];
    arr[end] = temp;
  }
}
