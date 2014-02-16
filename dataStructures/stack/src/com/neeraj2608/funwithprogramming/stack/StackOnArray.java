package com.neeraj2608.funwithprogramming.stack;

public class StackOnArray{ //CTCI 3.1 52
  private Object[] arr;
  private int index;
  
  public StackOnArray(int size){
    arr = new Object[size];
    index = 0;
  }
  
  public void push(Object o){
    if(isFull())
      throw new RuntimeException("stack is full");
    
    arr[index++] = o;
  }
  
  public Object pop(){
    if(isEmpty())
      throw new RuntimeException("stack is empty");
    
    return arr[--index];
  }
  
  public Object peek(){
    if(isEmpty())
      throw new RuntimeException("stack is empty");
    
    return arr[index-1];
  }
  
  public boolean isEmpty(){
    return index == 0;
  }
  
  public boolean isFull(){
    return index == arr.length;
  }
}
