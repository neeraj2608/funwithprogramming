package com.neeraj2608.funwithprogramming.suffixtree;

import java.util.HashMap;

public class SuffixTree{
  private SuffixTreeNode root;
  
  public SuffixTree(){
    root = new SuffixTreeNode("");
  }
  
  /**
   * Inserts a new string into the suffix tree. Uses a naive O(string_length^2)
   * algorithm.
   * @param s string to insert
   */
  public void insert(String s){
    if(s.length()==0)
      return;
    
    if(root.getMap().containsKey(s.substring(0,1))){
      if(s.length()==1){ //this is the only character in the string
        root.getMap().get(s.substring(0,1)).setEnd(true);
        return;
      }
      ins(root.getMap().get(s.substring(0,1)), s.substring(1));
    }
    else{
      SuffixTreeNode n1 = new SuffixTreeNode(s.substring(0,1));
      if(s.length()==1){ //this is the only character in the string
        n1.setEnd(true);
        root.getMap().put(s.substring(0,1), n1);
        return;
      }
      //the string has more characters. these must be added to n1's map.
      SuffixTreeNode n2 = new SuffixTreeNode(s.substring(1));
      n2.setParent(n1);
      n2.setEnd(true);
      n1.getMap().put(s.substring(1), n2);
      n1.setParent(root);
      root.getMap().put(s.substring(0,1), n1);
    }
  }
  
  private void ins(SuffixTreeNode node, String s){
    for(String k: node.getMap().keySet()){
      int n = numberOfSameChars(k, s);
      //if n == 0; no overlap
      if(n>0){
        //because of the way numberOfSameChars() is written, n can either
        //Case 1. stop at s's boundary
        //Case 2. stop at k's boundary
        //Case 3. stop somewhere before either s's or k's boundary
        if(n==s.length()){ //Case 1: we reached the end of the string being inserted
          if(k.length()>s.length()){
            //k is longer than s.
            //split k into two parts - one of length = s and the other the remaining string. 
            //the length s one (call it n1) replaces node.getMap().get(k). 
            //its end is set to true. its parent is set to node.
            //it is added to node's map.
            //in addition, the remaining string part is added to a new node n2.
            //node.getMap().get(k)'s map is assigned to n2. n2's parent is set to n1.
            //n2 is added to n1's map.
            splitOff(node, k, n, true);
            return;
          }
          else {//k and s were the same length. so, *this* is the node we were looking for. set its end to true 
            node.getMap().get(k).setEnd(true);
            return;
          }
        }
        else if(n == k.length()){ //Case 2: we reached the end of the key
          //we must search node.getMap.get(k)'s map for the rest of s, so call this same method recursively.
          ins(node.getMap().get(k),s.substring(n));
          return;
        }
        else{ //Case 3: we've stopped somewhere in between
          //split k into two parts - one of length = overlap with s and the other the remaining string. 
          //the first one (call it n1) replaces node.getMap().get(k).
          //its end is set to false. this is DIFFERENT from Case 1.
          //its parent is set to node and it is added to node's map.
          //the second one (call it n2) has the remaining part of the string.
          //node.getMap().get(k)'s map is assigned to n2.
          //n2's parent is set to n1 and it is added to n1's map.
          SuffixTreeNode n1 = splitOff(node, k, n, false);
          //the remaining part of s (which had no overlap) is added to a brand new leaf node, which is then
          //added to n1.
          addNewLeafNode(n1,s.substring(n));
          return;
        }
      }
    }
    //no overlap was found in any of this node's entries
    //create a brand new node with label = s.
    //set its parent to node.
    //set its end to true.
    //add to node's map
    addNewLeafNode(node, s);
  }

  private SuffixTreeNode splitOff(SuffixTreeNode node, String k, int length, boolean isEnd){
    SuffixTreeNode n1 = new SuffixTreeNode(k.substring(0,length));
    SuffixTreeNode n2 = new SuffixTreeNode(k.substring(length));
    SuffixTreeNode nodeToReplace = node.getMap().get(k);
    n2.setMap(nodeToReplace.getMap()); //we mustn't lose this information!
    n2.setEnd(nodeToReplace.isEnd()); //we mustn't lose this information!
    n2.setParent(n1);
    n1.setEnd(isEnd);
    n1.getMap().put(k.substring(length), n2);
    n1.setParent(node);
    node.getMap().remove(k);
    node.getMap().put(k.substring(0,length), n1);
    return n1;
  }
  
  private void addNewLeafNode(SuffixTreeNode node, String s){
    SuffixTreeNode n = new SuffixTreeNode(s);
    n.setEnd(true);
    n.setParent(node);
    node.getMap().put(s, n);
  }
  
  private int numberOfSameChars(String s1, String s2){ //assume s1.length < s2.length
    if(s1.length() > s2.length())
      numberOfSameChars(s2, s1);
    
    int i = 0;
    while(i<s1.length() && s1.charAt(i)==s2.charAt(i)){
      i++;
    }
    
    return i;
  }
  
  private class SuffixTreeNode{
    private String label;
    private HashMap <String, SuffixTreeNode> map;
    private SuffixTreeNode parent; //useful for applications like palindromes
    private boolean end;
    
    public SuffixTreeNode(String s){
      label = s;
      map = new HashMap<String, SuffixTreeNode>();
    }
    
    public String getLabel(){
      return label;
    }
    public void setLabel(String label){
      this.label = label;
    }
    public HashMap<String, SuffixTreeNode> getMap(){
      return map;
    }

    public void setMap(HashMap<String, SuffixTreeNode> map){
      this.map = map;
    }

    public SuffixTreeNode getParent(){
      return parent;
    }

    public void setParent(SuffixTreeNode parent){
      this.parent = parent;
    }

    public boolean isEnd(){
      return end;
    }

    public void setEnd(boolean end){
      this.end = end;
    }
  }
}
