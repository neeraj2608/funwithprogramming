package com.neeraj2608.funwithprogramming.suffixtree;

import java.util.HashMap;
import java.util.Stack;

/**
 * Suffix tree with linear time construction using Ukkonen's algorithm.
 * Based on: <a href="http://www.cs.duke.edu/courses/fall12/compsci260/resources/suffix.trees.in.detail.pdf">
 * http://www.cs.duke.edu/courses/fall12/compsci260/resources/suffix.trees.in.detail.pdf</a>
 * 
 * @author Raj
 */
public class SuffixTreeUkkonen{
  private SuffixTreeNode root;
  private String orgS;
  int endIndex; //the end of all leaves
  private Stack<SuffixTreeNode> suffixLinksToProcess;
  
  public SuffixTreeUkkonen(){
    root = new SuffixTreeNode(-1, -1);
    root.setDepth(0);
    suffixLinksToProcess = new Stack<SuffixTreeNode>();
  }
  
  public void insert(String s){
    orgS = s;
    ins(root,0,0);
  }
  
  private void ins(SuffixTreeNode n, int ext, int ph){
    if(ext>ph){ //all extensions for this phase done; try next phase
      while(!suffixLinksToProcess.isEmpty()){
        createSuffixLink(suffixLinksToProcess.pop());
      }
      ins(n,0,ph+1);
      return;
    }
    
    if(ph == orgS.length()) return; //all phases done
    
    endIndex = ph+1;
    
    String sti = orgS.substring(ext, endIndex);
    
    for(String k: n.getMap().keySet()){
      //EXTENSION RULE 3
      if(k.startsWith(sti)){ //also covers the case where k is the same as sti e.g. k = ab, sti = ab; k = ab, sti = a
        while(!suffixLinksToProcess.isEmpty()){
          createSuffixLink(suffixLinksToProcess.pop());
        }
        ins(n,0,ph+1); //all the remaining extensions are already present, skip to the next phase
        return;
      }
      
      if(sti.startsWith(k)){ //we've already covered the "sti is the same as k" case above,
                             //so we could only come here if sti length > k length e.g. sti = ab, k = a
        //if we're at a leaf, do nothing to the leaf node itself since endIndex has already taken care of the label augment.
        //however, we need to update the map so the "edge" label (the key in the map is updated to the augmented string)
        //once this is done, go on to the next extension
        if(n.getMap().get(k).isEnd()){
          SuffixTreeNode n1 = n.getMap().get(k);
          n.getMap().remove(k);
          n.getMap().put(k+sti.substring(k.length(),k.length()+1), n1);
          ins(n,ext+1,ph);
          return;
        }
        //EXTENSION RULE 2
        //if we're not at a leaf, we have to examine k's children to see if the next characters of sti are present in any of them
        //                        if not, we have to make a split
        if(!n.getMap().get(k).isEnd()){
          ins(n.getMap().get(k),ext+k.length(),ph);
          ins(n,ext+1,ph);
          return;
        }
      }
      
      //sti and k might have some chars in common e.g. sti = ab, k = aab;
      int x = numberOfSameChars(k, sti);
      if(x>0){
        SuffixTreeNode n1 = split(n,k,sti, x);
        SuffixTreeNode n2 = new SuffixTreeNode(ext+x,endIndex);
        n2.setParent(n1);
        n2.setDepth(n1.getDepth()+1);
        n2.setEnd(true);
        n1.getMap().put(orgS.substring(n2.getStart(),n2.getFinish()), n2);
        ins(n,ext+1,ph);
        return;
      }
    }
    
    //EXTENSION RULE 1
    //create a new leaf since we found no matches in n's map
    SuffixTreeNode n1 = new SuffixTreeNode(ext, endIndex);
    n1.setDepth(n.getDepth()+1);
    n1.setParent(n);
    n1.setEnd(true);
    n.getMap().put(orgS.substring(ext,endIndex), n1);
    ins(n,ext+1,ph);
  }
  
  /**
   * This method creates a new internal node!
   * @return
   */
  private SuffixTreeNode split(SuffixTreeNode n, String k, String sti, int x){
    SuffixTreeNode nodeToReplace = n.getMap().get(k);
    SuffixTreeNode n1 = new SuffixTreeNode(nodeToReplace.getStart(),nodeToReplace.getStart()+x);
    n1.setMap(nodeToReplace.getMap());
    n1.setDepth(nodeToReplace.getDepth());
    n1.setParent(n);
    n1.setSuffixLinkSearchString(sti.substring(x,x+1));
    suffixLinksToProcess.push(n1);
    n.getMap().remove(k);
    n.getMap().put(orgS.substring(n1.getStart(),n1.getFinish()), n1);
    
    SuffixTreeNode n2 = new SuffixTreeNode(n1.getFinish(), endIndex);
    n2.setEnd(true);
    n2.setParent(n1);
    n2.setDepth(n1.getDepth()+1);
    n1.getMap().put(orgS.substring(n2.getStart(),n2.getFinish()), n2);
    return n1;
  }

  private void createSuffixLink(SuffixTreeNode n){
    if(n.getParent()!=root)
      n.setSuffixLinkNode(n.getParent());
    else{
      for(String k: root.getMap().keySet()){
        if(k.startsWith(n.getSuffixLinkSearchString())){
          n.setSuffixLinkNode(root.getMap().get(k));
          n.setSuffixLinkSearchString("");
          break;
        }
      }
    }
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
    private SuffixTreeNode suffixLinkNode;
    private SuffixTreeNode parent; //useful for applications like palindromes
    private boolean end; //if end is true, ignore finish and look straight at SuffixTreeUkkonen.endIndex
    private int depth;
    private String suffixLinkSearchString;
    
    public SuffixTreeNode(int start, int finish){
      this.start = start;
      this.finish = finish;
      map = new HashMap<String, SuffixTreeNode>();
    }
    
    public int getLength(){
      return getFinish() - start;
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
      return isEnd()? SuffixTreeUkkonen.this.endIndex: finish;
    }

    public SuffixTreeNode getSuffixLinkNode(){
      return suffixLinkNode;
    }

    public void setSuffixLinkNode(SuffixTreeNode suffixLinkNode){
      this.suffixLinkNode = suffixLinkNode;
    }

    public String getSuffixLinkSearchString(){
      return suffixLinkSearchString;
    }

    public void setSuffixLinkSearchString(String suffixLinkSearchString){
      this.suffixLinkSearchString = suffixLinkSearchString;
    }
  }
}
