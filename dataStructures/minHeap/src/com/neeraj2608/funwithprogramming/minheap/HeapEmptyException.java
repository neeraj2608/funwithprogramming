package com.neeraj2608.funwithprogramming.minheap;

class HeapEmptyException extends RuntimeException{
  private static final long serialVersionUID = -7423145010485856712L;
  
  public HeapEmptyException(String message){
    super(message);
  }
}