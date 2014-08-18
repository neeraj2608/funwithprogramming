package com.neeraj2608.funwithprogramming.algorithms.sort;

public class SinglyLinkedListOfComparables<T extends Comparable<? super T>>{
  protected SLLNodeOfComparable<T> head;
  
  public void insert(T o){ //adds to head of list
    if(isEmpty()){
      head = new SLLNodeOfComparable<T>();
      head.setData(o);
      return;
    }
    SLLNodeOfComparable<T> currentNode = new SLLNodeOfComparable<T>();
    currentNode.setNext(head);
    currentNode.setData(o);
    head = currentNode;
  }
  
  public T remove(){ //removes from head of list
    if(isEmpty()){
      throw new RuntimeException("list is empty!");
    }
    
    T o = head.getData();
    head = head.getNext();
    return o;
  }

  public void reverse(){
    if(isEmpty() || head.getNext()==null)
      return;
    
    SLLNodeOfComparable<T> previous = null;
    
    while(head!=null){
      SLLNodeOfComparable<T> temp = head.getNext();
      head.setNext(previous);
      previous = head;
      head = temp;
    }
    
    head = previous;
  }
  
  public boolean isEmpty(){
    return (head == null);
  }

  public SLLNodeOfComparable<T> getHead(){
    return head;
  }

  public void setHead(SLLNodeOfComparable<T> head){
    this.head = head;
  }

}