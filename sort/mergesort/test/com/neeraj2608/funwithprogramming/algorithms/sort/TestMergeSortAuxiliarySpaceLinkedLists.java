package com.neeraj2608.funwithprogramming.algorithms.sort;

import java.util.ArrayList;
import static org.junit.Assert.assertArrayEquals;
import java.util.List;

import org.junit.Test;

public class TestMergeSortAuxiliarySpaceLinkedLists{
  
  @Test
  public void testMergeSortInPlace(){
    List<Integer> list1 = new ArrayList<Integer>();
    list1.add(3);
    list1.add(2);
    list1.add(1);
    list1.add(5);
    list1.add(4);
    Integer[] expected = new Integer[]{1,2,3,4,5};
    list1 = MergeSortAuxiliarySpaceLinkedLists.mergeSortAuxiliarySpace(list1);
    assertArrayEquals(list1.toArray(),expected);
  }

  @Test
  public void testMergeSortInPlaceWithIterators(){
    List<Integer> list1 = new ArrayList<Integer>();
    list1.add(3);
    list1.add(2);
    list1.add(1);
    list1.add(5);
    list1.add(4);
    Integer[] expected = new Integer[]{1,2,3,4,5};
    list1 = MergeSortAuxiliarySpaceLinkedLists.mergeSortAuxiliarySpaceWithIterators(list1);
    assertArrayEquals(list1.toArray(),expected);
  }

}
