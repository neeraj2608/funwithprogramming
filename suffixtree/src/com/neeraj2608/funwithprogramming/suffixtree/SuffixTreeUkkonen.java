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
  private String originalString;
  private Stack<SuffixTreeNode> pendingSuffixLinks;
  
  public SuffixTreeUkkonen(){
    root = new SuffixTreeNode(-1, -1);
    root.setDepth(0);
    pendingSuffixLinks = new Stack<SuffixTreeNode>();
  }
  
  /**
   * Insert string s into tree
   * @param s string to insert
   */
  public void insert(String s){
    if(s.length()==0) return;
    
    originalString = s;
    addLeafNode(root, 0);
    for(int phase=1;phase<s.length();++phase){
      ins(root,0,phase);
      processPendingSuffixLinksForThisPhase();
    }
  }
 
  /**
   * Insert new suffix with a given extension and phase into the tree.
   * 
   * @param node node to start insertion at
   * @param extension extension of suffix
   * @param phase phase of insertion process
   */
  private void ins(SuffixTreeNode node, int extension, int phase){
    //System.out.println(extension+" "+phase);
    if(extension==phase){
      addLeafNodeOnlyIfUnique(node,phase);
      return;
    }
    
    String precedingString = originalString.substring(extension,phase);
    String toInsert = originalString.substring(phase,phase+1);
    //System.out.println(precedingString+" "+toInsert);
    
    //find matching path as far as possible
    boolean completeMatch = false;
    int overlap = 0;
    while(precedingString.length()!=0 && !node.isLeaf()){
      boolean stop = true;
      for(SuffixTreeNode c: node.getMap()){
        String cLabel = originalString.substring(c.getStart(),c.getFinish());
        if(cLabel.equals(precedingString)){
          completeMatch = true;
          precedingString = "";
          node = c;
          break;
        } else {
          overlap = numberOfSameChars(cLabel, precedingString);
          //System.out.println(overlap+" "+cLabel+" "+precedingString);
          if(overlap > 0){
            precedingString = precedingString.substring(overlap);
            node = c;
            stop = false;
            break;
          }
        }
      }
      if(stop) break;
    }
    
    if(precedingString.length()==0){
      if(completeMatch){ //precedingString ended AT the path so far
        if(node.isLeaf()) //if node is leaf, augment
          node.setFinish(phase+1);
        else{
          node = splitPath(node,overlap);
          node.setSuffixLinkSearchChar(originalString.substring(node.getStart()+1, node.getStart()+2));
          addLeafNodeOnlyIfUnique(node,phase);
        }
      } else { //precedingString ended INSIDE the path so far
        if(node == root){
          addLeafNode(node, phase);
        } else{
          String nodeLabel = originalString.substring(node.getStart(),node.getFinish());
          if(nodeLabel.substring(overlap,overlap+1).equals(toInsert)){ //if next char of the path is same as toInsert, do nothing
          }
          else{ //if not, split
            node = splitPath(node,overlap);
            node.setSuffixLinkSearchChar(originalString.substring(node.getStart()+1, node.getStart()+2));
            addLeafNodeOnlyIfUnique(node,phase);
          }
        }
      }
    } else { //precedingString only partially matched
      String nodeLabel = originalString.substring(node.getStart(),node.getFinish());
      //System.out.println(precedingString+" "+nodeLabel+" "+toInsert+" "+overlap);
      if(nodeLabel.substring(overlap,overlap+1).equals(toInsert)){ //if next char of the path is same as toInsert, do nothing
      }
      else{ //if not, split
        node = splitPath(node,overlap);
        node.setSuffixLinkSearchChar(originalString.substring(node.getStart()+1, node.getStart()+2));
        addLeafNodeOnlyIfUnique(node,phase);
      }
    }
    
    ins(root,extension+1,phase);
  }  
  
  /**
   * Adds a leaf node n1 to a given node n2 only if n2 doesn't have a child
   * that starts with the first character of n1.
   * 
   * @param node to add leaf node to (n2 above)
   * @param phase the offset of the character to add
   */
  private void addLeafNodeOnlyIfUnique(SuffixTreeNode node, int phase){
    boolean matchFound = false;
    String stringToMatch = originalString.substring(phase,phase+1);
    for(SuffixTreeNode c: node.getMap()){
      String cLabel = originalString.substring(c.getStart(),c.getFinish());
      if(cLabel.startsWith(stringToMatch)){
        matchFound = true;
        break;
      }
    }
    if(!matchFound) addLeafNode(node,phase);
  }
  
  /**
   * Adds a leaf node n1 to a given node n2.
   * 
   * @param node to add leaf node to (n2 above)
   * @param phase the offset of the character to add
   */
  private void addLeafNode(SuffixTreeNode node, int offset){
    SuffixTreeNode n1 = new SuffixTreeNode(offset, offset+1);
    n1.setDepth(node.getDepth()+1);
    n1.setLeaf(true);
    n1.setParent(node);
    node.getMap().add(n1);
  }

  /**
   * Processes pending suffix links for this phase. Should be invoked after all the extensions
   * for a given phase have been processed.
   */
  private void processPendingSuffixLinksForThisPhase(){
    if(pendingSuffixLinks.isEmpty())
      return;
    
    SuffixTreeNode n1 = pendingSuffixLinks.pop();
    SuffixTreeNode n1Parent = n1.getParent();
    
    n1.setSuffixLinkNode(n1Parent);
    for(SuffixTreeNode currentNode: n1Parent.getMap()){
      String k = originalString.substring(currentNode.getStart(),currentNode.getFinish());
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
  private SuffixTreeNode splitPath(SuffixTreeNode nodeToReplace, int numCommon){
    //**********************************************************************
    //  n                                 n
    //  |                                 |
    //  nodeToReplace[start,finish)  =>   n1[start,start+x)
    //                                    |
    //                                    nodeToReplace[start+x,finish)
    //**********************************************************************
    if(numCommon == 0)
      return nodeToReplace;
      
    SuffixTreeNode n = nodeToReplace.getParent();
    SuffixTreeNode n1 = new SuffixTreeNode(nodeToReplace.getStart(), nodeToReplace.getStart() + numCommon);
    n1.setDepth(n.getDepth()+1);
    n1.setParent(n);
    n.getMap().remove(nodeToReplace);
    n.getMap().add(n1);
    
    nodeToReplace.setParent(n1);
    nodeToReplace.setStart(n1.getFinish());
    nodeToReplace.setDepth(n1.getDepth()+1);
    n1.getMap().add(nodeToReplace);
    
    pendingSuffixLinks.push(n1);
    return n1;
  }

  /**
   * Returns number of common characters between two input strings.
   * 
   * @param s1 first string
   * @param s2 second string
   * @return
   */
  private int numberOfSameChars(String s1, String s2){ //assume s1.length < s2.length
    if(s1.length() > s2.length())
      return numberOfSameChars(s2, s1);
    
    int i = 0;
    while(i<s1.length() && s1.charAt(i)==s2.charAt(i)){
      i++;
    }
    
    return i;
  }

  /**
   * Internal node of suffix tree.
   * @author Raj
   *
   */
  private class SuffixTreeNode{
    private int start, finish;
    private List<SuffixTreeNode> map;
    private SuffixTreeNode suffixLinkNode;
    private SuffixTreeNode parent; //useful for applications like palindromes
    private boolean leaf;
    private int depth;
    private String suffixLinkSearchChar; //used for creating suffix link
    
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
      return finish;
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
