package com.neeraj2608.funwithprogramming.stack;

import com.neeraj2608.funwithprogramming.linkedlist.SinglyLinkedList;

public class StackOnSinglyLinkedList{
  private SinglyLinkedList sll;
  protected int size, maxSize;
  
  public StackOnSinglyLinkedList(int maxSize){
    this.maxSize = maxSize;
    this.size = 0;
    sll = new SinglyLinkedList();
  }
  
  public void push(Object o){
    if(isFull())
      throw new RuntimeException("Stack is full!");
    
    size++;
    sll.addInFront(o);
  }
  
  public Object pop(){
    if(isEmpty())
      throw new RuntimeException("Stack is empty!");
    
    size--;
    return sll.remove();
  }
  
  public Object peek(){
    if(isEmpty())
      throw new RuntimeException("Stack is empty!");
    
    Object o = sll.remove();
    sll.addInFront(o);
    return o;
  }
  
  public boolean isEmpty(){
    return size == 0;
  }
  
  public boolean isFull(){
    return size >= maxSize;
  }
}
