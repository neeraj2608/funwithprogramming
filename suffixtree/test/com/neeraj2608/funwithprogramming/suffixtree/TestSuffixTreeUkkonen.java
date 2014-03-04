package com.neeraj2608.funwithprogramming.suffixtree;

import org.junit.Before;
import org.junit.Test;

public class TestSuffixTreeUkkonen{
  SuffixTreeUkkonen st;
  
  @Before
  public void setup(){
    st =  new SuffixTreeUkkonen();
  }
  
  @Test
  public void testInsert(){
    st.insert("cdddcdc");
  }

}
