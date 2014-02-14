package com.neeraj2608.funwithprogramming.minheap;

/**
 * @author Raj
 * Min-Heap of ints with two different implementations of deleteMin.
 */
public class MinHeap{
  private int[] data;
  private int cursize;
  
  /**
   * Creates new min heap.
   * @param size maximum possible size of heap
   */
  public MinHeap(int size){
    data = new int[size];
    for(int i=0;i<size;i++) data[i] = Integer.MAX_VALUE;
    cursize = 0;
  }
  
  /**
   * Insert new element into heap while maintaining heap property.
   * @param i int to insert
   */
  public void insert(int i){
    if(cursize == data.length) throw new RuntimeException("Heap is full");
    int insAt = ++cursize;
    data[insAt-1] = i;
    while(insAt>1 && (data[(insAt>>1)-1]>data[insAt-1])){
      swapInt(data,(insAt>>1)-1,insAt-1);
      insAt>>=1;
    }
  }
  
  /**
   * Deletes the minimum element in the heap.
   * This is the sift up version. It basically works by replacing the deleted element with its smaller child
   * and then recursing on the smaller child. The base case for the recursion is a little tricky (see comments
   * inside method).
   * @return the minimum element in the heap
   */
  public int deleteMinSiftUp(){
    if(isEmpty()) throw new HeapEmptyException("Heap is empty");
    int retVal = data[0];
    int insAt = 1;
    while(insAt<=(data.length>>1)){ //we must go all the way to data.length>>1 (i.e., half of how many elements the heap can have)
                                    //and not just to cursize>>1
                                    //to see why, consider the following sequence of deletes
                                    //        0               2               3
                                    //      /   \           /   \           /   \
                                    //    3       2 ->    3     max ->     4    max
                                    //   / \             / \              /  \
                                    //  7   4           7   4            7   max
                                    //for the next delete, cursize is 3 (no. of elements currently on the heap). However, if we
                                    //restricted our search for 3's successor to 3>>1 = 1, we'd never find 7 (which is at insAt = 4)
                                    //we must go as far down as possible
      int newInsAt = chooseSmallerChildIndex(insAt);
      data[insAt-1]=data[newInsAt-1];
      data[newInsAt-1] = Integer.MAX_VALUE;
      insAt = newInsAt;
    }
    cursize--;
    return retVal;
  }
  
  /**
   * Deletes the minimum element in the heap.
   * This is the sift down version. It basically works by replacing the deleted element with the largest element in the heap at this point
   * and then sifting the largest element as far down as it needs to go (always exchanging it with its smaller child)
   * @return the minimum element in the heap
   */
  public int deleteMinSiftDown(){
    if(isEmpty()) throw new HeapEmptyException("Heap is empty");
    int retVal = data[0];
    data[0] = data[cursize-1];
    data[cursize-1] = Integer.MAX_VALUE;
    int insAt = 1;
    while(insAt<=(cursize>>1)){
      int j = chooseSmallerChildIndex(insAt);
      if(data[insAt-1]>data[j-1]){
        swapInt(data,j-1,insAt-1);
        insAt = j;
      }
      else break;
    }
    cursize--;
    return retVal;
  }
  
  private int chooseSmallerChildIndex(int i){
    if(data[(i<<1)-1]<data[i<<1])
      return i<<1;
    else
      return (i<<1) + 1;
  }
  
  /**
   * Check if heap is empty.
   * @return true is heap is empty; false otherwise
   */
  public boolean isEmpty(){
    if(cursize==0)
      return true;
    else
      return false;
  }
  
  private static void swapInt(int[] arr, int start, int end){
    int temp = arr[start];
    arr[start] = arr[end];
    arr[end] = temp;
  }
}