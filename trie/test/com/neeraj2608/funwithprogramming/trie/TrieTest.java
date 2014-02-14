package com.neeraj2608.funwithprogramming.trie;

import static org.junit.Assert.assertEquals;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TrieTest{
  private Trie t;
  
  @Before
  public void setup(){
    t = new Trie();
  }

  @Test
  public void testInsertSuccess(){
    t.insert("abc");
    List<String> results = t.findPrefixMatches("abc");
    assertEquals(1, results.size());
  }
  
  @Test
  public void testFindPrefixMatchesSuccess(){
    t.insert("abc");
    t.insert("ac");
    t.insert("acd");
    List<String> results = t.findPrefixMatches("a");
    assertEquals(5, results.size());
  }

  @Test
  public void testFindPrefixMatchesFailure(){
    t.insert("abc");
    t.insert("ac");
    t.insert("acd");
    List<String> results = t.findPrefixMatches("z");
    assertEquals(0, results.size());
  }

  @Test
  public void testRemove(){
    t.insert("abc");
    t.remove("abc");
    List<String> results = t.findPrefixMatches("ab");
    assertEquals(1, results.size());
    results = t.findPrefixMatches("abc");
    assertEquals(0, results.size());
  }

}
