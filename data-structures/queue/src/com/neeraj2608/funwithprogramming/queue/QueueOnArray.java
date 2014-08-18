package com.neeraj2608.funwithprogramming.queue;

public class QueueOnArray{
  Object[] data;
  int head, tail, curElems;
  
  public QueueOnArray(int size){
    data = new Object[size];
    head = tail = curElems = 0;
  }
  
  public void enQueue(Object o){
    if(isFull())
      throw new RuntimeException("Queue is full!");
    
    data[tail] = o;
    tail = (tail+1)%(data.length);
    curElems++;
  }
  
  public Object deQueue(){
    if(isEmpty())
      throw new RuntimeException("Queue is empty!");
    
    Object o = data[head];
    head = (head+1)%(data.length);
    curElems--;
    return o;
  }
  
  public boolean isEmpty(){
    return curElems == 0;
  }
  
  public boolean isFull(){
    return curElems == data.length;
  }
}
