package com.neeraj2608.funwithprogramming.programmingpuzzles.strings;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestPalindromesWithManachersAlgorithm{
  PalindromesWithManachersAlgorithm l;
  
  @Before
  public void setup(){
    l = new PalindromesWithManachersAlgorithm();
  }
  
  @Test(expected=RuntimeException.class)
  public void testFindLongestPalindromeThrowsException(){
    l.findLongestPalindrome("");
  }
  
  @Test
  public void testFindLongestPalindrome(){
    String result = l.findLongestPalindrome("abc");
    assertEquals("a",result);
    result = l.findLongestPalindrome("ababa");
    assertEquals("ababa",result);
  }
  
  @Test(expected=RuntimeException.class)
  public void testFindAllPalindromesThrowsException(){
    l.findAllPalindromes("");
  }
  
  @Test
  public void testFindAllPalindromes(){
    List<String> results = l.findAllPalindromes("abc");
    assertEquals(0,results.size());
    results = l.findAllPalindromes("ababa");
    assertEquals(3,results.size());
    results = l.findAllPalindromes("ababaca");
    assertEquals(4,results.size());
  }

}
