package com.neeraj2608.funwithprogramming.suffixtree;

import org.junit.Before;
import org.junit.Test;

public class TestSuffixTree{
  SuffixTree st;
  
  @Before
  public void setup(){
    st = new SuffixTree();
  }

  @Test
  public void testInsert(){
    st.insert("abac");
    //TODO: This test is not really a test coz it don't check nuthin'
  }
}
