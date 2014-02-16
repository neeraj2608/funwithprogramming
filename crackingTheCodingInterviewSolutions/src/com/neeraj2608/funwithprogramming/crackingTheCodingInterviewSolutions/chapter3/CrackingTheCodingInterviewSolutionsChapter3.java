package com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions.chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.neeraj2608.funwithprogramming.linkedlist.SinglyLinkedList;
import com.neeraj2608.funwithprogramming.stack.StackOnLinkedList;

/**
 * @author Raj
 * Chapter 3: Stacks
 */
public class CrackingTheCodingInterviewSolutionsChapter3{
  /**
   * QUESTION: CTCI 3.1 (page 52)
   * Describe how you could use a single array to implement three stacks.
   * @author Raj
   */
  private static class MultipleStacksOnSameArray{
    private Object[] arr;
    private int size, maxSize, index;
    
    public MultipleStacksOnSameArray(Object[] arr, int maxSize, int start){
      this.arr = arr;
      this.maxSize = maxSize;
      this.index = start;
      this.size = 0;
    }
    
    public void push(Object o){
      if(isFull())
        throw new RuntimeException("stack is full");
      
      arr[index] = o;
      index--;
      size++;
    }
    
    public Object pop(){
      if(isEmpty())
        throw new RuntimeException("stack is empty");
      
      index++;
      size--;
      return arr[index];
    }
    
    public Object peek(){
      if(isEmpty())
        throw new RuntimeException("stack is empty");
      
      index++;
      Object o = arr[index];
      index--;
      return o;
    }
    
    public boolean isEmpty(){
      return size == 0;
    }
    
    public boolean isFull(){
      return size >= maxSize;
    }
  }

  /**
   * QUESTION: CTCI 3.2 (page 52)
   * How would you design a stack which, in addition to push and pop, also has a function 
   * min which returns the minimum element? Push, pop and min should all operate in 
   * O(1) time.
   * @author Raj
   */
  private static class MinStacksOnLL extends StackOnLinkedList{ //CTCI 3.2 52
    private SinglyLinkedList llMin;
    
    public MinStacksOnLL(int maxSize){
      super(maxSize);
      llMin = new SinglyLinkedList();
    }
    
    @Override
    public void push(Object o){
      if(!(o instanceof Integer))
        throw new RuntimeException("this stack only takes integers");
      Integer oInt = (Integer) o;
      super.push(oInt);
      if(llMin.isEmpty())
        llMin.addInFront(oInt);
      else{
        Integer cmp = (Integer)llMin.remove();
        llMin.addInFront(cmp);
        if(cmp > oInt)
          llMin.addInFront(o);
      }
    }
    
    @Override
    public Object pop(){
      Integer o = (Integer) super.pop();
      Integer cmp = (Integer) llMin.remove();
      if(cmp != o)
        llMin.addInFront(cmp);
      return o;
    }
    
    public Object min(){
      if(size == 0)
        return Integer.MAX_VALUE;
      Object min = llMin.remove();
      llMin.addInFront(min);
      return min;
    }
  }
    
  /**
   * QUESTION: CTCI 3.3 (page 52)
   * Imagine a (literal) stack of plates If the stack gets too high, it might topple Therefore, in real life,
   * we would likely start a new stack when the previous stack exceeds 
   * some threshold Implement a data structure SetOfStacks that mimics this SetOfStacks should be composed of
   * several stacks, and should create a new stack once the previous one exceeds capacity SetOfStacks push()
   * and SetOfStacks pop() should behave identically to a single stack (that is, pop() should return the same
   * values as it would if there were just a single stack)
   * FOLLOW UP
   * Implement a function popAt(int index) which performs a pop operation on a specific 
   * sub-stack
   * @author Raj
   *
   */
  private static class SetOfStacks{
    private int capacity;
    private List<StackOnLinkedList> listOfStacks;
    private StackOnLinkedList stacksOnLL;
    
    public SetOfStacks(int capacity){
      this.capacity = capacity;
      listOfStacks = new ArrayList<StackOnLinkedList>();
      stacksOnLL = new StackOnLinkedList(capacity);
      listOfStacks.add(stacksOnLL);
    }
    
    public void push(Object o){
      if(!stacksOnLL.isFull())
        stacksOnLL.push(o);
      else{
        stacksOnLL = new StackOnLinkedList(capacity);
        listOfStacks.add(stacksOnLL);
        stacksOnLL.push(o);
      }
    }
    
    public Object pop(){
      if(!stacksOnLL.isEmpty())
        return stacksOnLL.pop();
      else{
        listOfStacks.remove(listOfStacks.size()-1);
        if(listOfStacks.isEmpty())
          throw new RuntimeException("stack is empty!");
        stacksOnLL = listOfStacks.get(listOfStacks.size()-1);
        return pop();
      }
    }
    
    public Object popAt(int index){
      if(index > listOfStacks.size()-1)
        throw new RuntimeException("index too large!");
      StackOnLinkedList s = listOfStacks.get(index);
      return s.pop();
    }
  }
  
  /**
   * QUESTION: CTCI 3.5 (page 52)
   * Implement a MyQueue class which implements a queue using two stacks.
   * @author Raj
   */
  private static class MyQueue {
    private Stack<Integer> ping, pong;
    
    public MyQueue(){
      ping = new Stack<Integer>();
      pong = new Stack<Integer>();
    }
    
    public void queue(Integer i){
      while(!ping.isEmpty())
        pong.push(ping.pop());
      pong.push(i);
      while(!pong.isEmpty())
        ping.push(pong.pop());
    }
    
    public Integer deQueue(){
      if(ping.isEmpty())
        throw new RuntimeException("queue is empty!");
      
      return ping.pop();
    }
  }

  /**
   * QUESTION: CTCI 3.6 (page 52)
   * Write a program to sort a stack in ascending order You should not make any assumptions
   * about how the stack is implemented The following are the only functions that should be
   * used to write this program: push | pop | peek | isEmpty
   * @param s stack to sort
   */
  private static void sortStack(Stack<Integer> s){
    Stack<Integer> s1, s2, l, r;
    s1 = new Stack<Integer>();
    s2 = new Stack<Integer>();
    
    while(!s.isEmpty())
      s1.push(s.pop());
    
    while(!s1.isEmpty() || !s2.isEmpty()){
      if(s1.isEmpty()){
        l = s2;
        r = s1;
      } else {
        l = s1;
        r = s2;
      }
      
      Integer cmp = l.pop();
      while(!l.isEmpty()){
        Integer cmp2 = l.pop();
        if(cmp > cmp2)
          r.push(cmp2);
        else {
          r.push(cmp);
          cmp = cmp2;
        }
      }
      s.push(cmp);
    }
  }
  
  /**
   * QUESTION: CTCI 3.6 (page 52)
   * Write a program to sort a stack in ascending order You should not make any assumptions
   * about how the stack is implemented The following are the only functions that should be
   * used to write this program: push | pop | peek | isEmpty
   * @param s stack to sort
   */
  private static void sortStack2(Stack<Integer> s){ // more efficient than sortStack()
    Stack<Integer> s1 = new Stack<Integer>();
    
    while(!s.isEmpty()){
      Integer a = s.pop();
      while(!s1.isEmpty() && s1.peek()>a){
        s.push(s1.pop());
      }
      s1.push(a);
    }
    
    while(!s1.isEmpty())
      s.push(s1.pop());
  }
  
  public static void main(String[] args){
    Object[] arr = new Object[10];
    MultipleStacksOnSameArray s1 = new MultipleStacksOnSameArray(arr, 3, 2);
    MultipleStacksOnSameArray s2 = new MultipleStacksOnSameArray(arr, 3, 5);
    MultipleStacksOnSameArray s3 = new MultipleStacksOnSameArray(arr, 4, 9);
    StackOnLinkedList s4 = new StackOnLinkedList(3);
    MinStacksOnLL s5 = new MinStacksOnLL(10);
    
    for(int i=0;i<3;i++){
      s1.push(new Integer(i));
      s2.push(new Integer(2-i));
      s4.push(new Integer(i));
    }
    
    for(int i=0;i<2;i++){
      System.out.println("s1 peek "+s1.peek());
      System.out.println("s4 peek "+s4.peek());
    }
    
    for(int i=0;i<4;i++){
      s3.push(new Integer(i));
    }
    
    for(int i=0;i<3;i++){
      System.out.println("s1 "+s1.pop());
      System.out.println("s4 "+s4.pop());
      System.out.println("s2 "+s2.pop());
    }
    
    for(int i=0;i<4;i++){
      System.out.println("s3 "+s3.pop());
    }
    
    for(int i=0;i<10;i++){
      s5.push(new Integer(-i));
      System.out.println("s5 min "+s5.min());
    }
    
    SetOfStacks s6 = new SetOfStacks(3);
    
    for(int i=0;i<10;i++){
      s6.push(new Integer(i));
    }
    
    System.out.println("s6 "+s6.popAt(2));
    
    for(int i=0;i<9;i++){
      System.out.println("s6 "+s6.pop());
    }
    
    MyQueue q1 = new MyQueue();
    
    for(int i=0;i<10;i++){
      q1.queue(new Integer(i));
    }
    
    for(int i=0;i<10;i++){
      System.out.println("q1 "+q1.deQueue());
    }
    
    Stack<Integer> s = new Stack<Integer>();
    s.push(4);
    s.push(6);
    s.push(2);
    s.push(0);
    s.push(-10);
    //sortStack(s);
    sortStack2(s);
    
    while(!s.isEmpty())
      System.out.println(s.pop());
  }
}
