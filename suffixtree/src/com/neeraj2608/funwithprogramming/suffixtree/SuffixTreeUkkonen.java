package com.neeraj2608.funwithprogramming.suffixtree;

import java.util.ArrayList;
import java.util.List;
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
  private Stack<SuffixTreeNode> pendingSuffixLinks;
  
  public SuffixTreeUkkonen(){
    root = new SuffixTreeNode(-1, -1);
    root.setDepth(0);
    pendingSuffixLinks = new Stack<SuffixTreeNode>();
  }
  
  public void insert(String s){
    orgS = s;
    ins(root,0,0);
  }
  
  private void ins(SuffixTreeNode n, int extension, int phase){
    if(extension>phase){ //all extensions for this phase done; try next phase
      processPendingSuffixLinksForThisPhase();
      ins(n, 0, phase + 1);
      return;
    }
    
    if(phase == orgS.length()) return; //all phases done
    
    endIndex = phase;
    
    String sti = orgS.substring(extension, phase+1);
    
    for(SuffixTreeNode currentNode: n.getMap()){
      String k = orgS.substring(currentNode.getStart(),currentNode.getFinish());
      int numCommon = numberOfSameChars(k, sti);
      if(numCommon > 0){
        if(numCommon == k.length()){
          //2 cases possible: k == sti OR k < sti
          //2nd case
          //if currentNode is NOT leaf,
          //recurse on currentNode's children to find the rest of sti
          //if(!currentNode.isLeaf() && numCommon < sti.length()){
            ins(currentNode, extension + numCommon, phase);
          //}
          ins(n, extension + 1, phase);
        }
        else if (numCommon == sti.length()){
          //1 case possible: k > sti
          //this suffix is already completely present in tree. all the other extensions for this phase are present too.
          //hence, skip to the next phase.
          processPendingSuffixLinksForThisPhase();
          ins(n , 0, phase+1);
        }
        else{
          //1 case possible: sti and k have some prefix in common but not the whole string
          SuffixTreeNode currentNodeReplacement = splitOff(currentNode, sti, numCommon);
          SuffixTreeNode newSuffixNode = new SuffixTreeNode(extension + numCommon, phase + 1);
          newSuffixNode.setParent(currentNodeReplacement);
          newSuffixNode.setDepth(currentNodeReplacement.getDepth()+1);
          newSuffixNode.setLeaf(true);
          currentNodeReplacement.getMap().add(newSuffixNode);
          ins(n, extension+1, phase);
        }
        return;
      }
    }
    
    //EXTENSION RULE 1
    //create a new leaf since we found no matches in n's map
    SuffixTreeNode n1 = new SuffixTreeNode(extension, phase+1);
    n1.setDepth(n.getDepth()+1);
    n1.setParent(n);
    n1.setLeaf(true);
    n.getMap().add(n1);
    ins(n,extension+1,phase);
  }

  private void processPendingSuffixLinksForThisPhase(){
    if(pendingSuffixLinks.isEmpty())
      return;
    
    SuffixTreeNode n1 = pendingSuffixLinks.pop();
    SuffixTreeNode n1Parent = n1.getParent();
    
    n1.setSuffixLinkNode(n1Parent);
    for(SuffixTreeNode currentNode: n1Parent.getMap()){
      String k = orgS.substring(currentNode.getStart(),currentNode.getFinish());
      if(k.startsWith(n1.getSuffixLinkSearchChar()) && !currentNode.isLeaf() && currentNode!=n1){
        n1.setSuffixLinkNode(currentNode);
        break;
      }
    }
    n1.setSuffixLinkSearchChar("");
    
    while(!pendingSuffixLinks.isEmpty()){
      SuffixTreeNode n2 = pendingSuffixLinks.pop();
      n2.setSuffixLinkNode(n1);
      n2.setSuffixLinkSearchChar("");
      n1 = n2;
    }
  }
  
  /**
   * This method creates a new internal node!
   * @return
   */
  private SuffixTreeNode splitOff(SuffixTreeNode nodeToReplace, String sti, int numCommon){
    //  n                                 n
    //  |                                 |
    //  nodeToReplace[start,finish)  =>   n1[start,start+x)
    //                                    |
    //                                    nodeToReplace[start+x,finish)
    SuffixTreeNode n = nodeToReplace.getParent();
    SuffixTreeNode n1 = new SuffixTreeNode(nodeToReplace.getStart(), nodeToReplace.getStart() + numCommon);
    n1.setDepth(n.getDepth()+1);
    n1.setParent(n);
    n1.setSuffixLinkSearchChar(sti.substring(1,2));
    n.getMap().remove(nodeToReplace);
    n.getMap().add(n1);
    
    nodeToReplace.setParent(n1);
    nodeToReplace.setStart(n1.getFinish());
    nodeToReplace.setDepth(n1.getDepth()+1);
    n1.getMap().add(nodeToReplace);
    
    pendingSuffixLinks.push(n1);
    return n1;
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
    private List<SuffixTreeNode> map;
    private SuffixTreeNode suffixLinkNode;
    private SuffixTreeNode parent; //useful for applications like palindromes
    private boolean leaf; //if end is true, ignore finish and look straight at SuffixTreeUkkonen.endIndex
    private int depth;
    private String suffixLinkSearchChar;
    
    public SuffixTreeNode(int start, int finish){
      this.start = start;
      this.finish = finish;
      map = new ArrayList<SuffixTreeNode>();
    }
    
    public int getLength(){
      return getFinish() - start;
    }

    public List<SuffixTreeNode> getMap(){
      return map;
    }

    public SuffixTreeNode getParent(){
      return parent;
    }

    public void setParent(SuffixTreeNode parent){
      this.parent = parent;
    }

    public boolean isLeaf(){
      return leaf;
    }

    public void setLeaf(boolean leaf){
      this.leaf = leaf;
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
      return isLeaf()? SuffixTreeUkkonen.this.endIndex: finish;
    }

    public SuffixTreeNode getSuffixLinkNode(){
      return suffixLinkNode;
    }

    public void setSuffixLinkNode(SuffixTreeNode suffixLinkNode){
      this.suffixLinkNode = suffixLinkNode;
    }

    public String getSuffixLinkSearchChar(){
      return suffixLinkSearchChar;
    }

    public void setSuffixLinkSearchChar(String suffixLinkSearchChar){
      this.suffixLinkSearchChar = suffixLinkSearchChar;
    }

    public void setStart(int start){
      this.start = start;
    }

    public void setFinish(int finish){
      this.finish = finish;
    }
  }
}
