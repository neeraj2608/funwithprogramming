package com.neeraj2608.funwithprogramming.redblacktree;

import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRedBlackTree{

  RedBlackTree rbt;
  
  @Before
  public void setup(){
    rbt = new RedBlackTree();
  }
  
  @Test
  public void testSearch(){
    rbt.insert(5);
    rbt.insert(3);
    rbt.insert(8);
    rbt.insert(6);
    rbt.insert(9);
    assertTrue(rbt.search(5));
    assertTrue(rbt.search(9));
    assertFalse(rbt.search(0));
    assertFalse(rbt.search(-5));
  }
  
  @Test
  public void testDelete(){
    rbt.insert(5);
    rbt.insert(3);
    rbt.insert(-8);
    rbt.insert(81);
    rbt.insert(90);
    rbt.insert(108);
    rbt.insert(65);
    rbt.insert(43);
    rbt.insert(12);
    assertTrue(rbt.search(108));
    rbt.delete(108);
    assertFalse(rbt.search(108));
    assertTrue(rbt.search(-8));
    rbt.delete(-8);
    assertFalse(rbt.search(-8));
    assertTrue(rbt.search(43));
    rbt.delete(43);
    assertFalse(rbt.search(43));
    assertTrue(rbt.search(5));
    rbt.delete(5);
    assertFalse(rbt.search(5));
  }
  
  @Test
  public void testPseudoRandomInsertDeleteSequence() throws InterruptedException{
    Random random = new Random();
    
    int[] inputArray = new int[100];
    for(int i=0;i<inputArray.length;++i){
      inputArray[i] = random.nextInt(100);
      System.out.println(inputArray[i]);
      rbt.insert(inputArray[i]);
    }
    
    for(int i=0;i<inputArray.length;++i){
      int index = (i*100)%100;
      rbt.delete(inputArray[index]);
      assertFalse(rbt.search(inputArray[index]));
    }
  }
  
  @Test
  public void testDeleteRedPreLeafWithTwoSentinels(){
    rbt.insert(5);
    rbt.insert(3);
    rbt.insert(8);
    rbt.insert(6);
    rbt.insert(9);
    assertTrue(rbt.search(9));
    rbt.delete(9);
    assertFalse(rbt.search(9));
    assertTrue(rbt.search(6));
    rbt.delete(6);
    assertFalse(rbt.search(6));
  }
  
  @Test
  public void testDeleteBlackPreLeafWithOneSentinel(){
    rbt.insert(5);
    rbt.insert(3);
    rbt.insert(8);
    rbt.insert(6);
    rbt.insert(9);
    rbt.insert(10);
    assertTrue(rbt.search(8));
    rbt.delete(8);
    assertFalse(rbt.search(8));
  }
  
  @Test
  public void testDeleteEmptyTree(){
    rbt.delete(10);
  }
  
  @Test
  public void testDeleteNonExistingValue(){
    rbt.insert(5);
    rbt.delete(10);
  }
  
  @Test
  public void testDeleteRootFromSingletonTree(){
    rbt.insert(5);
    assertTrue(rbt.search(5));
    rbt.delete(5);
    assertFalse(rbt.search(5));
  }
  
  @Test
  @Ignore
  public void testIsEmpty(){
    assertTrue(rbt.isEmpty());
    rbt.insert(5);
    assertFalse(rbt.isEmpty());
    rbt.delete(5);
    assertTrue(rbt.isEmpty());
  } 
  
  @Test
  public void testMax(){
    rbt.insert(5);
    rbt.insert(3);
    rbt.insert(8);
    rbt.insert(6);
    rbt.insert(7);
    rbt.insert(9);
    assertEquals(9, rbt.max());
    rbt.delete(9);
    assertEquals(8, rbt.max());
  }
  
  @Test
  public void testMin(){
    rbt.insert(5);
    rbt.insert(3);
    rbt.insert(8);
    rbt.insert(6);
    rbt.insert(7);
    rbt.insert(9);
    assertEquals(3, rbt.min());
    rbt.delete(3);
    assertEquals(5, rbt.min());
  }

}
