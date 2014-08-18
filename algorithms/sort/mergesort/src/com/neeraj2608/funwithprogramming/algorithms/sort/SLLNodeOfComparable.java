package com.neeraj2608.funwithprogramming.algorithms.sort;

public class SLLNodeOfComparable<T extends Comparable<? super T>>{
  private T data;
  private SLLNodeOfComparable<T> next;

  public void setNext(SLLNodeOfComparable<T> n){
    this.next = n;
  }

  public SLLNodeOfComparable<T> getNext(){
    return next;
  }

  public void setData(T o){
    this.data = o;
  }

  public T getData(){
    return this.data;
  }
}