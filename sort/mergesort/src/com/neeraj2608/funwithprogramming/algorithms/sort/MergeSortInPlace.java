package com.neeraj2608.funwithprogramming.algorithms.sort;

/**
 * In-place merge sort. Brainwave!
 * @author Raj
 */
public class MergeSortInPlace{
  public static void mergeSortInPlace(int[] arr){
    //in the first pass, we start with arrays of 1 and merge.
    //since we're merging in place, the merge is done by rotating the array. this needs auxiliary
    //space of O(1) (one variable, to be exact).
    //here's how the rotation looks:
    //5 3 1 2
    //Pass 1: we're merging arrays of length 1
    //5       3
    //start   j   stop
    //(compare arr[start] with arr[j]. 5 is larger than 3, so need to rotate [start,j] (j-start) times = once. increment j)
    //3       5
    //        start j
    //              stop
    //j has become >= stop so stop 
    //1       2
    //start   j   stop
    //(compare arr[start] with arr[j]. 1 is smaller than 2, increment start. after increment, start==j so increment j.)
    //1       2
    //        start  j
    //               stop
    //j has become >= stop so stop 
    //Pass 2: we merge arrays of length 2
    //3       5      1      2
    //start          j         stop
    //(compare arr[start] with arr[j]. 3 is larger than 1, so need to rotate [start,j] (j-start) times = twice. This jth position has thus been taken care of. Increment j)
    //5       1      3      2
    //1       3      5      2
    //        start         j  stop
    //(compare arr[start] with arr[j]. 3 is larger than 2, so need to rotate [start,j] (j-start) times = twice. This jth position has thus been taken care of. Increment j)
    //1       5      2      3
    //1       2      3      5
    //               start     j
    //                         stop
    //j has become >= stop so stop 
    //Pass 3: we merge arrays of length 4
    //j would be larger than the array, so we can stop.
    int length = 1; 
    while(true){
      for(int i=0;i<arr.length-length;i=i+(length<<1)){
        int start = i;
        int j = start + length;
        if(j>=arr.length) break;
        int stop = Math.min(start + (length<<1),arr.length);
        while(start<stop){
          if(arr[start]>arr[j]){
            rotate(arr,start,j,j-start);
            j++;
          }
          start++;
          if(start==j) j++;
          if(j==stop) break;
        }
      }
      length<<=1;
      if(length>=arr.length) break;
    }
  }
  
  private static void rotate(int[] arr, int start, int stop, int times){
    while(times>0){
      for(int i=start;i<stop;++i){
        int temp = arr[i];
        arr[i] = arr[i+1];
        arr[i+1] = temp;
      }
      times--;
    }
  }
}
