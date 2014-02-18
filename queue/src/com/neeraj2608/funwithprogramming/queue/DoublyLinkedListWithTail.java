package com.neeraj2608.funwithprogramming.queue;

import com.neeraj2608.funwithprogramming.linkedlist.DLLNode;
import com.neeraj2608.funwithprogramming.linkedlist.DoublyLinkedList;

public class DoublyLinkedListWithTail extends DoublyLinkedList{
  private DLLNode tail;
  private boolean firstInsertDone;
  
  public DoublyLinkedListWithTail(){
    firstInsertDone = false;
  }
  
  @Override
  public void insert(Object o){
    super.insert(o);
    if(!firstInsertDone){
      firstInsertDone = true;
      tail = head;
    }
  }
  
  public Object removeFromRear(){
    if(isEmpty())
      throw new RuntimeException("doubly linked list with tail is empty");
    
    Object o = tail.getData();
    tail = tail.getPrev();
    if(tail != null)
      tail.setNext(null);
    return o;
  }
  
  @Override
  public boolean isEmpty(){
    return super.isEmpty() || (tail == null);
  }
}
