package com.neeraj2608.funwithprogramming.binarysearchtree;

public class BinarySearchTree{
  private Node root;
  
  /**
   * Insert a new integer into the BST.
   * @param i int to insert
   */
  public void insert(int i){
      root = insert(root, i);
  }
  
  private Node insert(Node node, int i){
    if(node==null){
      node = new Node(i);
      return node;
    }
    else{
      if(node.getValue()==i){
      }
      else if(node.getValue()>i){
        node.setLeftChild(insert(node.getLeftChild(), i));
      }
      else{
        node.setRightChild(insert(node.getRightChild(), i));
      }
      return node;
    }
  }
  
  /**
   * Searches for a given element in the BST.
   * @param i element to search for
   * @return true if element found; false otherwise
   */
  public boolean search(int i){
    Node n = searchChild(i);
    if(n == null) return false;
    else return true;
  }
  
  private Node searchChild(int i){
    Node n = searchParent(root,root,i);
    if(n == null) return null;
    else{
      if(n.getValue()==i) return n;
      else if(n.getLeftChild()!=null && n.getLeftChild().getValue()==i) return n.getLeftChild();
      else if(n.getRightChild()!=null && n.getRightChild().getValue()==i) return n.getRightChild();
    }
    return null;
  }

  private Node searchParent(Node n, Node nParent, int i){
    if(n==null) return null;
    else if (n.getValue()==i) return nParent;
    else{
      if(n.getValue()>i)
        return searchParent(n.getLeftChild(),n,i);
      else
        return searchParent(n.getRightChild(),n,i);
    }
  }
  
  /**
   * Delete int i from the BST. This method uses both the in-order successor and in-order predecessor
   * for replacements (for testing).
   * 
   * @param i int to delete
   */
  public void delete(int i){
    Node matchingNodeParent = searchParent(root, root, i);
    if(matchingNodeParent == null) return; //i was not in the BST; nothing to delete
    else{
      if(matchingNodeParent.getValue()==i) root = replaceWithInOrderPredessor(matchingNodeParent); //if the matching node's parent itself has the value i,
                                                                                                   //then in method searchParent(), n and nParent were the
                                                                                                   //same (because we return nParent when n.getValue()==i).
                                                                                                   //This can only happen on the first call to the
                                                                                                   //method (in all the other calls, nParent is a level higher
                                                                                                   //than n). Since we make the first call with root,
                                                                                                   //this means n = nParent = root
      else if(matchingNodeParent.getLeftChild()!=null && matchingNodeParent.getLeftChild().getValue()==i)
        matchingNodeParent.setLeftChild(replaceWithInOrderPredessor(matchingNodeParent.getLeftChild()));
      else if(matchingNodeParent.getRightChild()!=null && matchingNodeParent.getRightChild().getValue()==i)
        matchingNodeParent.setRightChild(replaceWithInOrderSuccessor(matchingNodeParent.getRightChild()));
    }
  }
  
  private Node replaceWithInOrderSuccessor(Node n){
    if(n.hasNoChildren())
      return null;
    else if(n.hasOnlyOneChild()){
      return (n.getLeftChild()!=null)? n.getLeftChild() : n.getRightChild();
    }
    else{
      Node n1 = inOrderSuccessor(n);
      n.setValue(n1.getValue());
      return n;
    }
  }

  private Node inOrderSuccessor(Node n){
    return inOrderSuccessor(n.getRightChild(), n.getRightChild());
  }
  
  private Node inOrderSuccessor(Node n, Node nParent){
    if(n.hasNoChildren() || n.getLeftChild()==null){
      if(n.getRightChild()!=null) nParent.setLeftChild(n.getRightChild());
      else nParent.setLeftChild(null);
      return n;
    }
    else
      return inOrderSuccessor(n.getLeftChild(), n);
  }

  private Node replaceWithInOrderPredessor(Node n){
    if(n.hasNoChildren())
      return null;
    else if(n.hasOnlyOneChild()){
      return (n.getLeftChild()!=null)? n.getLeftChild() : n.getRightChild();
    }
    else{
      Node n1 = inOrderPredecessor(n);
      n.setValue(n1.getValue());
      return n;
    }
  }

  private Node inOrderPredecessor(Node n){
    return inOrderPredecessor(n.getLeftChild(),n.getLeftChild());
  }

  private Node inOrderPredecessor(Node n, Node nParent){
    if(n.hasNoChildren() || n.getRightChild()==null){
      if(n.getLeftChild()!=null) nParent.setRightChild(n.getLeftChild());
      else nParent.setRightChild(null);
      return n;
    }
    else
      return inOrderPredecessor(n.getRightChild(), n);
  }
  
  /**
   * Check if BST is empty.
   * @return true if empty; false otherwise
   */
  public boolean isEmpty(){
    return root == null;
  }
  
  /**
   * Returns the max element in the BST so far.
   * @return max element in BST.
   */
  public int max(){
    return findMax(root).getValue();
  }
  
  private Node findMax(Node n){
    if(n.getRightChild()!=null)
      return findMax(n.getRightChild());
    else
      return n;
  }
  
  /**
   * Returns the min element in the BST so far.
   * @return max element in BST.
   */
  public int min(){
    return findMin(root).getValue();
  }

  private Node findMin(Node n){
    if(n.getLeftChild()!=null)
      return findMin(n.getLeftChild());
    else
      return n;
  }

  private class Node{
    int value;
    Node leftChild;
    Node rightChild;
    
    public Node(int value){
      this.value = value;
    }
    
    public boolean hasNoChildren(){
      return (leftChild==null && rightChild==null);
    }
    
    public boolean hasOnlyOneChild(){
      return (leftChild!=null && rightChild==null) || (leftChild==null && rightChild!=null);
    }
    
    public int getValue(){
      return value;
    }
    
    public void setValue(int value){
      this.value = value;
    }
    
    public Node getLeftChild(){
      return leftChild;
    }
    
    public void setLeftChild(Node leftChild){
      this.leftChild = leftChild;
    }
    
    public Node getRightChild(){
      return rightChild;
    }
    
    public void setRightChild(Node rightChild){
      this.rightChild = rightChild;
    }
  }
}
