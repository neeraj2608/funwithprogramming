package com.neeraj2608.funwithprogramming.linkedlist;

public class DLLNode{
  private Object data;
  private DLLNode next;
  private DLLNode prev;

  public void setNext(DLLNode n){
    this.next = n;
  }

  public DLLNode getNext(){
    return next;
  }

  public void setData(Object o){
    this.data = o;
  }

  public Object getData(){
    return this.data;
  }

  public DLLNode getPrev(){
    return prev;
  }

  public void setPrev(DLLNode prev){
    this.prev = prev;
  }
}