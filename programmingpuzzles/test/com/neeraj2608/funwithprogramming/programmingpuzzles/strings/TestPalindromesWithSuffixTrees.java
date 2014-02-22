package com.neeraj2608.funwithprogramming.programmingpuzzles.strings;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TestPalindromesWithSuffixTrees{
  PalindromesWithSuffixTrees s;
  
  @Before
  public void setup(){
    s = new PalindromesWithSuffixTrees();
  }
  
  @Test
  public void testFindPalindromes(){
    List<String> palindromes = s.findPalindromes("aacaa");
    assertEquals(3, palindromes.size());
    palindromes = s.findPalindromes("abc");
    assertEquals(0, palindromes.size());
    palindromes = s.findPalindromes("aba");
    assertEquals(1, palindromes.size());
  }

}
