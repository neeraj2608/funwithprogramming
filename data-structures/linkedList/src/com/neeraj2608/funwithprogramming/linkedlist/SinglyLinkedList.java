package com.neeraj2608.funwithprogramming.linkedlist;

public class SinglyLinkedList{
  protected SLLNode head;
  
  public void insert(Object o){ //adds to head of list
    if(isEmpty()){
      head = new SLLNode();
      head.setData(o);
      return;
    }
    SLLNode currentNode = new SLLNode();
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
    
    SLLNode previous = null;
    
    while(head!=null){
      SLLNode temp = head.getNext();
      head.setNext(previous);
      previous = head;
      head = temp;
    }
    
    head = previous;
  }
  
  public boolean isEmpty(){
    return (head == null);
  }

  public SLLNode getHead(){
    return head;
  }

  public void setHead(SLLNode head){
    this.head = head;
  }

}