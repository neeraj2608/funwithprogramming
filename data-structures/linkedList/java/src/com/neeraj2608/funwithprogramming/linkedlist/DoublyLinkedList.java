package com.neeraj2608.funwithprogramming.linkedlist;

public class DoublyLinkedList{
  protected DLLNode head;
  
  public void insert(Object o){
    if(head == null){
      head = new DLLNode();
      head.setData(o);
      return;
    }
    DLLNode n = new DLLNode();
    n.setData(o);
    n.setNext(head);
    head.setPrev(n);
    head = n;
  }
  
  public Object remove(){
    if(isEmpty())
      throw new RuntimeException("doubly linked list is empty!");
    Object o = head.getData();
    head = head.getNext();
    if(head != null)
      head.setPrev(null);
    return o;
  }
  
  public boolean isEmpty(){
    return head == null;
  }
}
