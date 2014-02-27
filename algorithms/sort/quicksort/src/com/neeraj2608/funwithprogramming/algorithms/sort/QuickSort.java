package com.neeraj2608.funwithprogramming.algorithms.sort;

public class QuickSort{
  public static void quicksort(int[] arr){
    //choose pivot
    //move pivot to end of array so it doesn't interfere with the rest of the algorithm
    //we will assume for the moment that the pivot will rest here once this pass is done. whether this will actually be the pivot's
    //resting place depends on what happens below. 
    //starting from end-1 downto 0:
    //1. compare each element with the pivot's value (remember pivot is now at the end of the array!!)
    //2. if a given element is greater than or equal to the pivot, move it to just before the pivot. decrement the pivot's resting place
    //   by 1 since this is now the pivot's new prospective resting place. (to see why, note that the element we just moved into this
    //   location is greater than or equal to the pivot. this means that when we're done, we want it to lie to the RIGHT of the pivot. this
    //   can be accomplished by swapping this resting place element with the pivot element (which is currently at the end of the array).
    //   this means that the resting element will then end up on the pivot's right, which is what we want.
    //at the end of the loop above, we will have the pivot's final actual resting place. swap the pivot (which is at the end of the array)
    //with this resting place element.
    //recurse into the two halves of the array (apart from the pivot element).
    sort(arr,0,arr.length-1);
  }
  
  private static void sort(int[] arr, int start, int end){
    if(start>=end) return;
    
    int pivot = (start+end)>>1;
    swapInt(arr,pivot,end);
    int pivotrestingplace = end;
    
    for(int i=end-1;i>=0;--i){
      if(arr[i]>=arr[end]){
        swapInt(arr,i,--pivotrestingplace);
      }
    }
    swapInt(arr,pivotrestingplace,end);
    
    sort(arr,start,pivotrestingplace-1);
    sort(arr,pivotrestingplace+1,end);
  }
  
  private static void swapInt(int[] arr, int start, int end){
    int temp = arr[start];
    arr[start] = arr[end];
    arr[end] = temp;
}
}
