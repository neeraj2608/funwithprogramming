package com.neeraj2608.funwithprogramming.queue;

public class QueueOnDoublyLinkedList{
    private DoublyLinkedListWithTail dll;
    
    public QueueOnDoublyLinkedList(){
      dll = new DoublyLinkedListWithTail();
    }
    
    public void enQueue(Object o){
      dll.insert(o);
    }
    
    public Object deQueue(){
      if(isEmpty())
        throw new RuntimeException("queue is empty");
      
      return dll.removeFromRear();
    }
    
    public boolean isEmpty(){
      return dll.isEmpty();
    }
}
