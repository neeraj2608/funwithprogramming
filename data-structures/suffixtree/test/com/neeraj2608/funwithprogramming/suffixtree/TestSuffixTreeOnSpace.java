package com.neeraj2608.funwithprogramming.suffixtree;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestSuffixTreeOnSpace{
  SuffixTreeOnSpace st;
  
  @Before
  public void setup(){
    st = new SuffixTreeOnSpace();
  }

  @Test
  public void testFindLCADepth(){
    st.insert("abac$");
    assertEquals(-1,st.findLCADepth("ac$", "burra$"));
    assertEquals(0,st.findLCADepth("ac$", "c$"));
    assertEquals(1,st.findLCADepth("abac$", "ac$"));
  }
}
