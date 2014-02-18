package com.neeraj2608.funwithprogramming.queue;

import com.neeraj2608.funwithprogramming.linkedlist.SinglyLinkedList;

public class QueueOnSinglyLinkedList{
    private SinglyLinkedList sll;
    
    public QueueOnSinglyLinkedList(){
      sll = new SinglyLinkedList();
    }
    
    public void enQueue(Object o){
      sll.insert(o);
    }
    
    public Object deQueue(){
      if(isEmpty())
        throw new RuntimeException("queue is empty!");
      sll.reverse();
      Object o = sll.remove();
      sll.reverse();
      return o;
    }
    
    public boolean isEmpty(){
      return sll.isEmpty();
    }
}
