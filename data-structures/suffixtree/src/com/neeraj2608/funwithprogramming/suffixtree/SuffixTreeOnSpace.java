package com.neeraj2608.funwithprogramming.suffixtree;

import java.util.HashMap;

/**
 * This is a modified version of the suffix tree that uses only O(n) space for 
 * representing the edges. We store the start and finish indices corresponding
 * to the suffix rather than the entire suffix itself.
 * 
 * @author Raj
 */
public class SuffixTreeOnSpace{
  private SuffixTreeNode root;
  private String originalString;
  
  public SuffixTreeOnSpace(){
    root = new SuffixTreeNode(-1, -1);
    root.setDepth(0);
  }
  
  /**
   * Inserts a new string into the suffix tree. Uses a naive O(string_length<SUP>2</SUP>)
   * algorithm.
   * @param s string to insert
   */
  public void insert(String s){
    originalString = s;
    ins(0);
  }
  
  private void ins(int start){
    if(start==originalString.length())
      return;
    
    if(root.getMap().containsKey(originalString.substring(start,start+1))){
      if(originalString.length()==1){ //this is the only character in the string
        root.getMap().get(originalString.substring(start,start+1)).setEnd(true);
        return;
      }
      ins(root.getMap().get(originalString.substring(start,start+1)), start+1);
    }
    else{
      SuffixTreeNode n1 = new SuffixTreeNode(start,start+1);
      if(originalString.length()==1){ //this is the only character in the string
        n1.setEnd(true);
        n1.setDepth(root.getDepth()+1);
        root.getMap().put(originalString.substring(start,start+1), n1);
        return;
      }
      //the string has more characters. these must be added to n1's map.
      SuffixTreeNode n2 = new SuffixTreeNode(start+1,originalString.length());
      n2.setParent(n1);
      n1.setDepth(root.getDepth()+1);
      n2.setDepth(n1.getDepth()+1);
      n2.setEnd(true);
      n1.getMap().put(originalString.substring(start+1), n2);
      n1.setParent(root);
      root.getMap().put(originalString.substring(start,start+1), n1);
    }
    
    ins(start+1); //insert the next suffix
  }
  
  private void ins(SuffixTreeNode node, int start){
    for(String k: node.getMap().keySet()){
      int n = numberOfSameChars(k, originalString.substring(start));
      //if n == 0; no overlap
      if(n>0){
        //because of the way numberOfSameChars() is written, n can either
        //Case 1. stop at s's boundary
        //Case 2. stop at k's boundary
        //Case 3. stop somewhere before either s's or k's boundary
        if(n==(originalString.length()-start)){ //Case 1: we reached the end of the string being inserted
          if(k.length()>(originalString.length()-start)){
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
          ins(node.getMap().get(k),start+n);
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
          //added to n1's map.
          addNewLeafNode(n1,n);
          return;
        }
      }
    }
    //no overlap was found in any of this node's entries
    //create a brand new node with label = s.
    //set its parent to node.
    //set its end to true.
    //add to node's map
    addNewLeafNode(node, start);
  }

  private SuffixTreeNode splitOff(SuffixTreeNode node, String k, int length, boolean isEnd){
    SuffixTreeNode n1 = new SuffixTreeNode(0,length);
    SuffixTreeNode n2 = new SuffixTreeNode(length, originalString.length());
    SuffixTreeNode nodeToReplace = node.getMap().get(k);
    n2.setMap(nodeToReplace.getMap()); //we mustn't lose this information!
    n2.setEnd(nodeToReplace.isEnd()); //we mustn't lose this information!
    n2.setDepth(nodeToReplace.getDepth()+1);
    n2.setParent(n1);
    n1.setEnd(isEnd);
    n1.getMap().put(k.substring(length), n2);
    n1.setParent(node);
    n1.setDepth(node.getDepth()+1);
    node.getMap().remove(k);
    node.getMap().put(k.substring(0,length), n1);
    return n1;
  }
  
  private void addNewLeafNode(SuffixTreeNode node, int start){
    SuffixTreeNode n = new SuffixTreeNode(start, originalString.length());
    n.setEnd(true);
    n.setParent(node);
    n.setDepth(node.getDepth()+1);
    node.getMap().put(originalString.substring(start), n);
  }
  
  public int findLCADepth(String s1, String s2){
    SuffixTreeNode n1 = findLeafNode(s1);
    SuffixTreeNode n2 = findLeafNode(s2);
    SuffixTreeNode lcaNode = findLCA(n1, n2);
    if(lcaNode == null)
      return -1;
    return lcaNode.getDepth();
  }
  
  private SuffixTreeNode findLCA(SuffixTreeNode n1, SuffixTreeNode n2){
    if(n1 == null || n2 == null)
      return null;
    
    return recurseLCA(n1, n2);
  }

  private SuffixTreeNode recurseLCA(SuffixTreeNode n1, SuffixTreeNode n2){ //assume n1 is deeper than n2 or at equal depth
    if(n1.getDepth()<n2.getDepth())
      return recurseLCA(n2, n1);
    
    if(n1 == n2)
      return n1;
    
    return recurseLCA(n1.getParent(),n2);
  }

  private SuffixTreeNode findLeafNode(String s){
    return findLeaf(root.getMap().get(s.substring(0,1)), s.substring(1));
  }
  
  private SuffixTreeNode findLeaf(SuffixTreeNode node, String s){
    if(node == null)
      return null;
    
    if(s.length()==0)
      if(node.isEnd())
        return node;
      else
        return null;
    
    for(SuffixTreeNode n: node.getMap().values()){
      if(s.startsWith(originalString.substring(n.getStart(),n.getFinish())))
        return findLeaf(n,s.substring(n.getFinish()-n.getStart()));
    }
    
    return null;
  }

  private int numberOfSameChars(String s1, String s2){ //assume s1.length < s2.length
    if(s1.length() > s2.length())
      return numberOfSameChars(s2, s1);
    
    int i = 0;
    while(i<s1.length() && s1.charAt(i)==s2.charAt(i)){
      i++;
    }
    
    return i;
  }
  
  private class SuffixTreeNode{
    private int start, finish;
    private HashMap <String, SuffixTreeNode> map;
    private SuffixTreeNode parent; //useful for applications like palindromes
    private boolean end;
    private int depth;
    
    public SuffixTreeNode(int start, int finish){
      this.start = start;
      this.finish = finish;
      map = new HashMap<String, SuffixTreeNode>();
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

    public int getDepth(){
      return depth;
    }

    public void setDepth(int depth){
      this.depth = depth;
    }

    public int getStart(){
      return start;
    }

    public int getFinish(){
      return finish;
    }
  }
}
