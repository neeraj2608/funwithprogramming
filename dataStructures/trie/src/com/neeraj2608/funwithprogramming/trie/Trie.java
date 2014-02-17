package com.neeraj2608.funwithprogramming.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

class Trie{
  TrieNode root;
  
  public Trie(){
    root = new TrieNode("");
  }
  
  /**
   * Inserts a string into the trie. Complexity is O(n).
   * @param s string to insert
   */
  public void insert(String s){
    ins(root,s,0);
  }
  
  private void ins(TrieNode node, String s, int index){
    if(node.getMap().containsKey(s.substring(index,index+1))){
      node = node.getMap().get(s.substring(index,index+1));
    }
    else{
      TrieNode n = new TrieNode(s.substring(0,index+1));
      node.getMap().put(s.substring(index,index+1),n);
      node = n;
    }
    if(index != s.length()-1)
      ins(node, s, index+1);
  }
  
  /**
   * Returns all strings in the Trie that have s as a prefix.
   * @param s String that should be a prefix of the strings returned by this method
   * @return List of strings that have s as a prefix
   */
  public List<String> findPrefixMatches(String s){
    List<String> results = new ArrayList<String>();
    TrieNode n = findMatchingNode(root, s, 0);
    if(n == null) return results;
    return enumerateChildren(results, n);
  }
  
  private List<String> enumerateChildren(List<String> results, TrieNode node){
    results.add(node.getData());
    for(Entry<String, TrieNode> e: node.getMap().entrySet()){
      enumerateChildren(results, e.getValue());
    }
    return results;
  }

  private TrieNode findMatchingNode(TrieNode node, String s, int index){
    if(node.getData().equals(s))
      return node;
    if(index == s.length() || node.getMap().isEmpty())
      return null;
    if(node.getMap().containsKey(s.substring(index,index+1)))
      return findMatchingNode(node.getMap().get(s.substring(index,index+1)), s, index+1);
    return null;
  }
  
  /**
   * Removes string from trie.
   * @param s string to remove.
   */
  public void remove(String s){
    rem(root, s, 0);
  }

  private void rem(TrieNode node, String s, int index){
    if(index == s.length() || node.getMap().isEmpty())
      return;
    if(node.getMap().containsKey(s.substring(index,index+1))){
      if(index==s.length()-1){
        node.getMap().remove(s.substring(index,index+1));
        return;
      }
      rem(node.getMap().get(s.substring(index,index+1)),s,index+1);
    }
  }

  /**
   * @author Raj
   * Trie node.
   */
  private class TrieNode{
    private String data; 
    private HashMap<String, TrieNode> map;
    
    public TrieNode(String s){
      setData(s);
      map = new HashMap<String, TrieNode>();
    }
    
    public String getData(){
      return data;
    }
    
    public void setData(String data){
      this.data = data;
    }
    
    public HashMap<String, TrieNode> getMap(){
      return map;
    }
  }
}
