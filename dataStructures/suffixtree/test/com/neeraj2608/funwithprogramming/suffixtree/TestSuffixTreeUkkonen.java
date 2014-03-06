package com.neeraj2608.funwithprogramming.suffixtree;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestSuffixTreeUkkonen{
  SuffixTreeUkkonen st;
  
  @Before
  public void setup(){
    st =  new SuffixTreeUkkonen();
  }
  
  @Test
  public void testFindLCADepth(){
    st.insert("abac$");
    assertEquals(-1,st.findLCADepth("ac$", "burra$"));
    assertEquals(0,st.findLCADepth("ac$", "c$"));
    assertEquals(1,st.findLCADepth("abac$", "ac$"));
  }

}
