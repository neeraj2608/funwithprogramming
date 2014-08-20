package com.neeraj2608.funwithprogramming.linkedlist;

public class SLLNode{
  private Object data;
  private SLLNode next;

  public void setNext(SLLNode n){
    this.next = n;
  }

  public SLLNode getNext(){
    return next;
  }

  public void setData(Object o){
    this.data = o;
  }

  public Object getData(){
    return this.data;
  }
}