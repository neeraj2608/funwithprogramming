package com.neeraj2608.funwithprogramming.algorithms.sort;

public class BubbleSort{
  public static void bubbleSort(int[] arr){
    int n = arr.length;
    while(n>1){
      for(int i=1;i<n;++i){
        if(arr[i]<arr[i-1])
          swapInt(arr,i,i-1);
      }
      n--;
    }
  }
  
  public static void modifiedBubbleSort(int[] arr){
    int n = arr.length;
    while(n>1){
      boolean flag = false;
      for(int i=1;i<n;++i){
        if(arr[i]<arr[i-1]){
          swapInt(arr,i,i-1);
          flag = true;
        }
      }
      if(!flag) break; //break early if no swaps
      n--;
    }
  }
  
  private static void swapInt(int[] arr, int start, int end){
    int temp = arr[start];
    arr[start] = arr[end];
    arr[end] = temp;
  }
}
