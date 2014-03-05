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
  private int e; //nomenclature used in paper
  
  public SuffixTreeUkkonen(){
    root = new SuffixTreeNode(0, 0);
    root.setDepth(0);
    root.setSuffixLinkNode(root);
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
      e = phase+1;
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
    
    String toInsert = originalString.substring(phase,phase+1);
    
    int alphaLength = phase-start;
    FindSuffixTreeNodeResultSpeedUp matchingNodeResult = findLeafWithSkipCount(new FindSuffixTreeNodeResultSpeedUp(node,0,start), alphaLength);
    SuffixTreeNode matchingLeaf = matchingNodeResult.n;
   
    int overlap = matchingLeaf.getAdjustedLength()-(matchingNodeResult.examinedLength-alphaLength); //we use adjustedLength even to calculate examinedLength
    
    //use suffix rules to augment leaf
    if(matchingLeaf.isLeaf()){ //RULE 1
      if(overlap<matchingLeaf.getAdjustedLength()){
        if(matchingLeaf.getLabel().substring(overlap,overlap+1).equals(toInsert)){ //RULE 3
        }
        else{
          matchingLeaf = addLeafNode(splitPath(matchingLeaf,overlap), phase); //RULE 2
        }
      }
    }
    else{
      if(overlap<matchingLeaf.getLength()){
        if(matchingLeaf.getLabel().substring(overlap,overlap+1).equals(toInsert)){ //RULE 3
        }
        else{
          matchingLeaf = addLeafNode(splitPath(matchingLeaf,overlap), phase); //RULE 2
        }
      }
    }
    
    processPendingSuffixLinksForThisExtension();
    
    //1. We're currently at a leaf with label (say) alpha.
    //2. Walk up to the nearest node (can be root or an internal node).
    //3. Go to the suffix link of that node (if root, we define the suffix link
    //   as pointing to itself -- a departure from the paper but makes
    //   the algorithm implementation much cleaner)
    //4. From the suffix link, walk down until the path you have walked becomes
    //   the same as alpha. Note that alpha is already augmented with the character
    //   we're adding for this extension (toInsert), hence we ignore that character
    //   and look for the rest of alpha.
    SuffixTreeNode nextNode = matchingLeaf.getParent().getSuffixLinkNode();
    
    if(nextNode==root && node==root){ //if we were already at the root, we must go to the next extension
      insert(root,start+1,phase);
      return;
    }
    
    insert(nextNode, matchingLeaf.getStart(), phase); //else, we look for the leaf's label
  }
  
  private FindSuffixTreeNodeResultSpeedUp findLeafWithSkipCount(FindSuffixTreeNodeResultSpeedUp sr, int alphaLength){
    if(alphaLength==0)
      return sr;
    
    String charToLookFor = originalString.substring(sr.lookAtPos,sr.lookAtPos+1);
    
    for(SuffixTreeNode c: sr.n.getMap()){
      if(c.getCharFromLabel(0).equals(charToLookFor)){
        sr.n = c;
        sr.examinedLength += c.getAdjustedLength();
        if(alphaLength > sr.examinedLength){ //path not long enough yet, keep looking
          sr.lookAtPos += c.getLength();
          return findLeafWithSkipCount(sr, alphaLength);
        }
        return sr;
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
   * to the node, we're also interested in the length of the path examined so far and
   * the current position of the string we're looking for ('h' in the paper)
   * 
   * @author Raj
   */
  private class FindSuffixTreeNodeResultSpeedUp{
    SuffixTreeNode n;
    int examinedLength;
    int lookAtPos;
    
    public FindSuffixTreeNodeResultSpeedUp(SuffixTreeNode n, int examinedLength, int lookAtPos){
      this.n = n;
      this.examinedLength = examinedLength;
      this.lookAtPos = lookAtPos;
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
      return getFinish() - getStart();
    }
    
    public int getAdjustedLength(){
      return isLeaf()? getLength()-1 : getLength();
    }
    
    public String getCharFromLabel(int offset){
      return getLabel().substring(offset,offset+1);
    }
    
    public String getLabel(){
      return originalString.substring(getStart(),getFinish());
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
      return isLeaf()? SuffixTreeUkkonen.this.e:finish;
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