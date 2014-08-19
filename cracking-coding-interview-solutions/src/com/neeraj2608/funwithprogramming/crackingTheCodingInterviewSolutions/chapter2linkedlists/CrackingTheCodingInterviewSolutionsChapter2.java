package com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions.chapter2linkedlists;

import com.neeraj2608.funwithprogramming.linkedlist.Data;
import com.neeraj2608.funwithprogramming.linkedlist.SLLNode;
import com.neeraj2608.funwithprogramming.linkedlist.SinglyLinkedList;

/**
 * @author Raj
 * Chapter 2: Linked Lists
 */
public class CrackingTheCodingInterviewSolutionsChapter2{
  public static class LL extends SinglyLinkedList{
    /**
     * QUESTION: CTCI 2.1 (page 50)
     * TODO: Write code to remove duplicates from an unsorted linked list
     * FOLLOW UP
     * How would you solve this problem if a temporary buffer is not allowed?
     */
    public void remDupsNoTemporaryBuffer(){ //O(n^2)
      SLLNode start = head;
      
      while(start != null){
        SLLNode current = start.getNext();
        SLLNode previous = start;
        while(current != null){
          if(start.getData() == current.getData())
            previous.setNext(current.getNext());
          previous = current;
          current = current.getNext();
        }
        start = start.getNext();
      }
    }
    
    /**
     * QUESTION: CTCI 2.2 (page 50)
     * Implement an algorithm to find the nth to last element of a singly linked list
     * @param n
     * @return element we were looking for
     */
    public Object nthToLastElement(int n){
      if(isEmpty())
        throw new RuntimeException("list is empty!");
      if(n < 0)
        throw new RuntimeException("n cannot be less than 0");
      
      SLLNode start = head;
      SLLNode o = head;
      int count = -1;
      
      while(start!=null){
        start = start.getNext();
        if(count < n)
          count ++;
        else
          o = o.getNext();
      }
      
      if(count < n)
        throw new RuntimeException("list wasn't long enough");
      else
        return o.getData();
    }
    
    /**
     * QUESTION: CTCI 2.3 (page 50)
     * Implement an algorithm to delete a node in the middle of a single linked list, given 
     * only access to that node
     * EXAMPLE
     * Input: the node ‘c’ from the linked list a->b->c->d->e
     * Result: nothing is returned, but the new linked list looks like a->b->d->e
     * @param d node to delete
     */
    public void deleteFromMiddle(Data d){
      if(isEmpty())
        return;
      
      if(head.getData()==d){
        head = head.getNext();
        return;
      }
      
      SLLNode start = head.getNext();
      SLLNode previous = head;
      
      while(start!=null){
        if(start.getData()==d){
          previous.setNext(start.getNext());
          return;
        }
        previous = start;
        start = start.getNext();
      }
    }
    
    /**
     * QUESTION: CTCI 2.5 (page 50)
     * Given a circular linked list, implement an algorithm which returns node at the beginning of the loop
     * DEFINITION
     * Circular linked list: A (corrupt) linked list in which a node’s next pointer points to an 
     * earlier node, so as to make a loop in the linked list
     * EXAMPLE
     * input: A -> B -> C -> D -> E -> C [the same C as earlier]
     * output: C
     * @return node at start of loop
     */
    public Object startOfLoop(){
      SLLNode slow = head;
      SLLNode fast = head;
      
      while(true){
        slow = slow.getNext();
        fast = fast.getNext().getNext();
        
        if(null == fast)
          throw new RuntimeException("no loop in this list!");
        
        if(slow==fast)
          break;
      }
      
      slow = head;
      while(slow!=fast){
        slow = slow.getNext();
        fast = fast.getNext();
      }
      
      return slow.getData();
    }
    
    public void corruptThisList(int n){ //used to test CTCI 2.5 50. will create loopback from tail to (n-1)th element
      SLLNode current = head;
      SLLNode loopBackTo = head;
      int count = 0;
      while(current.getNext()!=null){
        if(count == n)
          loopBackTo = current;
        current = current.getNext();
        count++;
      }
      
      current.setNext(loopBackTo);
    }
     
  }
  
  /**
   * QUESTION: CTCI 2.4 (page 50)
   * You have two numbers represented by a linked list, where each node contains a single digit The digits are stored in reverse order, such that the 1’s digit is at the head of 
   * the list Write a function that adds the two numbers and returns the sum as a linked list
   * EXAMPLE 
   * Input: (3 -> 1 -> 5) + (5 -> 9 -> 2)
   * Output: 8 -> 0 -> 8
   * @param first linked list representing first number
   * @param second linked list representing second number
   * @return linked list representing the sum
   */
  private static LL add2NumLLs(LL first, LL second){
    LL result = new LL();
    int carry = 0;
    
    while(!first.isEmpty() || !second.isEmpty()){
      int s = 0;
      if(!first.isEmpty())
        s += ((Data)first.remove()).getId();
      if(!second.isEmpty())
        s +=((Data)second.remove()).getId();
      s+=carry;
      Data d = new Data();
      d.setId(s%10);
      result.insert(d);
      carry = s/10;
    }
    
    if(carry>0){
      Data d = new Data();
      d.setId(carry);
      result.insert(d);
    }
    
    result.reverse();
    
    return result;
  }
  
  public static void main (String[] args){
    LL ll = new LL();
    Data d0 = new Data();
    d0.setId(0);
    ll.insert(d0);
    Data d1 = new Data();
    d1.setId(1);
    ll.insert(d1);
    Data d2 = new Data();
    d2.setId(2);
    ll.insert(d2);
    Data testData = new Data();
    testData.setId(9);
    ll.insert(testData);
    ll.insert(d0);
    ll.insert(d1);
    ll.insert(d2);
    
    ll.deleteFromMiddle(testData);
    
    System.out.println("2nd last element is "+((Data)ll.nthToLastElement(2)).getId());
    
    ll.remDupsNoTemporaryBuffer();
    
    ll.reverse();
    
    while(!ll.isEmpty()){
      Data d = (Data) ll.remove();
      System.out.println(d.getId());
    }
    
    LL first = new LL();
    LL second = new LL();
    
    Data firstD1 = new Data();
    firstD1.setId(9);
    first.insert(firstD1);
    Data firstD2 = new Data();
    firstD2.setId(9);
    first.insert(firstD2);
    Data firstD3 = new Data();
    firstD3.setId(9);
    first.insert(firstD3);
    
    Data secondD1 = new Data();
    secondD1.setId(9);
    second.insert(secondD1);
    Data secondD2 = new Data();
    secondD2.setId(9);
    second.insert(secondD2);
    Data secondD3 = new Data();
    secondD3.setId(9);
    second.insert(secondD3);
    
    LL result = add2NumLLs(first, second);
    while(!result.isEmpty()){
      Data d = (Data) result.remove();
      System.out.println(d.getId());
    }
    
    LL ll2 = new LL();
    Data d = new Data();
    d.setId(6);
    ll2.insert(d);
    d = new Data();
    d.setId(5);
    ll2.insert(d);
    d = new Data();
    d.setId(4);
    ll2.insert(d);
    d = new Data();
    d.setId(3);
    ll2.insert(d);
    d = new Data();
    d.setId(2);
    ll2.insert(d);
    d = new Data();
    d.setId(1);
    ll2.insert(d);
    d = new Data();
    d.setId(0);
    ll2.insert(d);
    ll2.corruptThisList(2);
    
    System.out.println("loop starts at "+((Data)ll2.startOfLoop()).getId());
  }
  
}
