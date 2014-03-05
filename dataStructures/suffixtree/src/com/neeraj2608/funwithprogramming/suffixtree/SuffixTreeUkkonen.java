package com.neeraj2608.funwithprogramming.suffixtree;

import java.util.ArrayList;
import java.util.Iterator;
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
  private SuffixTreeNode fullStringLeafNode; //points to the leaf node representing the full string in the tree
  private String originalString;
  private Stack<SuffixTreeNode> pendingSuffixLinks;
  
  public SuffixTreeUkkonen(){
    root = new SuffixTreeNode(0, 0);
    root.setDepth(0);
    root.setSuffixLinkNode(root);
    fullStringLeafNode = root;
    pendingSuffixLinks = new Stack<SuffixTreeNode>();
  }
  
  /**
   * Insert string s into tree
   * @param s string to insert
   */
  public void insert(String s){
    if(s.length()==0) return;
    
    originalString = s;
    fullStringLeafNode = addLeafNode(root, 0);
    
    for(int phase=1;phase<s.length();++phase){
      //ins(root,0,phase);
      fullStringLeafNode.setFinish(phase+1);
      insert(fullStringLeafNode.getParent().getSuffixLinkNode(),fullStringLeafNode.getStart(),phase);
      processPendingSuffixLinksForThisPhase();
    }
  }
 
  /**
   * Starts new phase at full string leaf in tree.
   * @param fullStringLeafNode node representing full string in tree
   * @param phase
   */
  private void insert(SuffixTreeNode node, int start, int phase){
    if(start==phase){
      addLeafNodeOnlyIfUnique(node, phase);
      if(node!=root)
        insert(node.getParent().getSuffixLinkNode(), node.getStart(), phase);
      return;
    }
    
    String stringToFind = originalString.substring(start, phase);
    String toInsert = originalString.substring(phase,phase+1);
    
    FindSuffixTreeNodeResult matchingNodeResult = findLeaf(new FindSuffixTreeNodeResult(node,0), stringToFind);
    SuffixTreeNode matchingNode = matchingNodeResult.n;
   
    int overlap = matchingNodeResult.overlap;
    
    //use suffix rules to augment leaf
    if(matchingNode.isLeaf()){ //RULE 1
      matchingNode.setFinish(phase+1);
      if(overlap<matchingNode.getLength()-1){
        if(matchingNode.getLabel().substring(overlap,overlap+1).equals(toInsert)){ //RULE 3
        }
        else{
          matchingNode = addLeafNode(splitPath(matchingNode,overlap), phase); //RULE 2
        }
      }
    }
    else{
      if(overlap<matchingNode.getLength()){
        if(matchingNode.getLabel().substring(overlap,overlap+1).equals(toInsert)){ //RULE 3
        }
        else{
          matchingNode = addLeafNode(splitPath(matchingNode,overlap), phase); //RULE 2
        }
      }
    }
    
    processPendingSuffixLinksForThisExtension();
    
    if(matchingNode.getParent().getSuffixLinkNode()==root){
      insert(root,start+1,phase);
      return;
    }
    
    insert(matchingNode.getParent().getSuffixLinkNode(), matchingNode.getStart(), phase);
  }

  private FindSuffixTreeNodeResult findLeaf(FindSuffixTreeNodeResult sr, String s){
    if(s.length()==0)
      return sr;
    
    for(SuffixTreeNode c: sr.n.getMap()){
      int overlap = numberOfSameChars(c.getLabel(), s);
      if(overlap>0){
        sr.overlap = overlap;
        sr.n = c;
        return findLeaf(sr,s.substring(sr.overlap));
      }
    }
    return sr;
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
  private SuffixTreeNode addLeafNode(SuffixTreeNode node, int offset){
    SuffixTreeNode n1 = new SuffixTreeNode(offset, offset+1);
    n1.setDepth(node.getDepth()+1);
    n1.setLeaf(true);
    n1.setParent(node);
    node.getMap().add(n1);
    return n1;
  }
  
  /**
   * Processes pending suffix links for this extension. This differs from {@link SuffixTreeUkkonen#processPendingSuffixLinksForThisPhase()}
   * mainly in that that method consumes all the nodes that are in the pending state waiting for their suffix links to be updated. This
   * method does not consume the nodes but simply updates them.
   * <p> This method comes in handy when the same phase introduces more than one internal node
   * and we wish to use the latter. If we only updated the suffix links at the end of the phase using {@link SuffixTreeUkkonen#processPendingSuffixLinksForThisPhase()},
   * later extensions in the same phase wouldn't be able to proceed because when we tried to follow the suffix link from the last extension, it wouldn't be ready.
   * e.g. Consider the string aabaac just before extension 3 of phase 5:
   * <pre>
   * Suffix Link between 'a' and 'root' 
   *       -------root------
   *      |                |
   *  ----a---             baac
   * |        |
   * abaac    baac
   * </pre>
   * Extension 3 of phase 5 will try to insert 'aac' into the tree, resulting in a new internal node and a new suffix link
   * <pre>
   * Suffix Link between 'a' and 'root'
   * Suffix Link between 'aa' and 'a' (only available before the start of extension 4 if this method is used!!)
   *       -------root------
   *      |                |
   *  ----a---             baac
   * |        |
   * a----    baac
   * |   |
   * c   baac
   * </pre>
   * Extension 4 of phase 5 needs the suffix link between 'aa' and 'a' to proceed (because we're following suffix links to get from one extension
   * to the other. If we only updated the suffix links at the end of phase, this suffix link between 'aa' and 'a' wouldn't be ready.
   */
  private void processPendingSuffixLinksForThisExtension(){
    if(pendingSuffixLinks.isEmpty())
      return;
    
    SuffixTreeNode n1 = pendingSuffixLinks.peek();
    SuffixTreeNode n1Parent = n1.getParent();
    
    n1.setSuffixLinkNode(n1Parent);
    for(SuffixTreeNode currentNode: n1Parent.getMap()){
      String k = originalString.substring(currentNode.getStart(),currentNode.getFinish());
      if(k.startsWith(n1.getSuffixLinkSearchChar()) && !currentNode.isLeaf() && currentNode!=n1){
        n1.setSuffixLinkNode(currentNode);
        break;
      }
    }
    
    Iterator<SuffixTreeNode> it = pendingSuffixLinks.iterator();
    it.next();
    while(it.hasNext()){
      SuffixTreeNode n2 = it.next();
      n2.setSuffixLinkNode(n1);
      n1 = n2;
    }
  }

  /**
   * Processes pending suffix links for this phase. Should be invoked after all the extensions
   * for a given phase have been processed.
   * <p>This method basically chains suffix links created in a given phase in the reverse order
   * in which they were created (hence the use of a stack). Each suffix link looks at its parent
   * and searches the parent's children (i.e., its siblings) for the one that starts the character
   * that is supposed to follow it (which we record in {@link SuffixTreeNode#suffixLinkSearchChar}).
   * For example, in the suffix tree for the string "cdddcdc",
   * <pre>
   * (incomplete tree depicted...)
   *    ----root-----
   *    |           |
   *  --cd---    ---d---
   *  |     |    |     |
   * </pre>
   * Node 'cd' points to its sibling that starts with 'd' because the next prefix will start with 'd'.
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
   * This method creates a new internal node! It makes the following structural changes:
   * <pre>
   *  n                                 n
   *  |                                 |
   *  nodeToReplace[start,finish)  =>   n1[start,start+x)
   *                                    |
   *                                    nodeToReplace[start+x,finish)
   * </pre>
   * @param nodeToReplace node to split up
   * @param numCommon point in nodeToReplace's label at which to make the split. If numCommon is 0,
   *                  no split is made (i.e., nodeToReplace is returned as is)
   * @return
   */
  private SuffixTreeNode splitPath(SuffixTreeNode nodeToReplace, int numCommon){
    if(numCommon == 0)
      return nodeToReplace;
      
    SuffixTreeNode n = nodeToReplace.getParent();
    SuffixTreeNode n1 = new SuffixTreeNode(nodeToReplace.getStart(), nodeToReplace.getStart() + numCommon);
    n1.setDepth(n.getDepth()+1);
    n1.setParent(n);
    n1.setSuffixLinkSearchChar(originalString.substring(n1.getDepth()+1,n1.getDepth()+2));
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
   * Helper class to encapsulate results of node search. In particular, in addition
   * to the node, we're also interested in the amount of overlap the input string
   * had with the node's label.
   * 
   * @author Raj
   */
  private class FindSuffixTreeNodeResult{
    SuffixTreeNode n;
    int overlap;
    
    public FindSuffixTreeNodeResult(SuffixTreeNode n, int overlap){
      this.n = n;
      this.overlap = overlap;
    }
  }

  /**
   * Internal node of suffix tree.
   * @author Raj
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
    
    public String getLabel(){
      return originalString.substring(start,finish);
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
