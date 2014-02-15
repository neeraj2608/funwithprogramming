package com.neeraj2608.funwithprogramming.linkedlist;

public class Node{
  private Object data;
  private Node next;

  public void setNext(Node n){
    this.next = n;
  }

  public Node getNext(){
    return next;
  }

  public void setData(Object o){
    this.data = o;
  }

  public Object getData(){
    return this.data;
  }
}