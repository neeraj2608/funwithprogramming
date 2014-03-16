package com.neeraj2608.funwithprogramming.algorithms.sort;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestMergeSortInPlaceLinkedLists{
  
  @Test
  public void testMergeSortInPlacePowerOfTwoLengthArray(){
    SinglyLinkedListOfComparables<Integer> list1 = new SinglyLinkedListOfComparables<Integer>();
    list1.insert(2);
    list1.insert(4);
    list1.insert(9);
    list1.insert(-20);
    list1.insert(5);
    list1.insert(1);
    list1.insert(-1);
    list1.insert(3);
    Integer[] expected = new Integer[]{-20,-1,1,2,3,4,5,9};
    MergeSortInPlaceLinkedLists.mergeSortInPlace(list1);
    assertArrayEquals(toArray(list1),expected);
  }
  
  @Test
  public void testMergeSortInPlaceOddLengthArray(){
    SinglyLinkedListOfComparables<Integer> list1 = new SinglyLinkedListOfComparables<Integer>();
    list1.insert(2);
    list1.insert(4);
    list1.insert(9);
    list1.insert(5);
    list1.insert(1);
    list1.insert(-1);
    list1.insert(3);
    Integer[] expected = new Integer[]{-1,1,2,3,4,5,9};
    MergeSortInPlaceLinkedLists.mergeSortInPlace(list1);
    assertArrayEquals(toArray(list1),expected);
  }
  
  private Object[] toArray(SinglyLinkedListOfComparables<Integer> list){
    List<Integer> l = new ArrayList<Integer>();
    while(!list.isEmpty()){
      l.add(list.remove());
    }
    return l.toArray();
  }

}
