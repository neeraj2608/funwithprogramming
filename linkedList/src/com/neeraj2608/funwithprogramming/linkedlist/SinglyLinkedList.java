package com.neeraj2608.funwithprogramming.linkedlist;

public class SinglyLinkedList{
  protected Node head;
  
  public void addInFront(Object o){ //adds to head of list
    if(isEmpty()){
      head = new Node();
      head.setData(o);
      return;
    }
    Node currentNode = new Node();
    currentNode.setNext(head);
    currentNode.setData(o);
    head = currentNode;
  }
  
  public Object remove(){ //removes from head of list
    if(isEmpty()){
      throw new RuntimeException("list is empty!");
    }
    
    Object o = head.getData();
    head = head.getNext();
    return o;
  }

  public void reverse(){
    if(isEmpty() || head.getNext()==null)
      return;
    
    Node previous = null;
    
    while(head!=null){
      Node temp = head.getNext();
      head.setNext(previous);
      previous = head;
      head = temp;
    }
    
    head = previous;
  }
  
  public boolean isEmpty(){
    return (head == null);
  }

}