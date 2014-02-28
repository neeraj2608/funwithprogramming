package com.neeraj2608.funwithprogramming.algorithms.sort;

public class MergeSortAuxiliarySpace{
  public static int[] mergeSortAuxiliarySpace(int[] arr){
    return m(arr,0,arr.length-1);
  }
  
  private static int[] m(int[] arr, int start, int end){
    if(start>=end){
      int[] result = new int[1];
      result[0] = arr[start];
      return result;
    }
    
    int half = (start+end)>>1;
    return merge(m(arr,start,half),m(arr,half+1,end));
  }

  private static int[] merge(int[] a, int[] b){
    int[] result = new int[a.length+b.length];
    int i=0,j=0,k=0;
    while(k<result.length){
      if(i==a.length)
        result[k++] = b[j++];
      else if(j==b.length)
        result[k++] = a[i++];
      else{
        if(a[i]<b[j])
          result[k++] = a[i++];
        else
          result[k++] = b[j++];
      }
    }
    return result;
  }
}
