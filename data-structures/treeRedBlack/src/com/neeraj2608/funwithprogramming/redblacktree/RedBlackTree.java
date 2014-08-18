package com.neeraj2608.funwithprogramming.redblacktree;

/**
 * Red-Black Tree.
 * Sentinels are marked by null.
 * @see http://www.cse.ohio-state.edu/~gurari/course/cis680/cis680Ch11.html
 * @author Raj
 */
public class RedBlackTree{
  private Node root;
  
  /**
   * Inserts int i into the RB tree.
   * @param i
   */
  public void insert(int i){
    if(root == null){
      root = new Node();
      root.i = i;
    }
    else{
      Node n = insert(root,root,i);
      if(n!=null) //null indicates i is already in the tree
        rebalance(n);
    }
  }
  
  private Node insert(Node node, Node child, int i){
    if(child==null){
      child = new Node();
      child.i = i;
      child.r = true;
      child.parent = node;
      if(child.i>node.i)
        node.right = child;
      else
        node.left = child;
      return child;
    }
    else{
      if(child.i == i) return null;
      else if(child.i < i) return insert(child,child.right,i);
      else return insert(child,child.left,i);
    }
  }

  private void rebalance(Node n){
    if(n==root){
      n.r = false;
      return;
    }
    
    Node p = n.parent;
    if(!p.r) //parent = black; covers first insertion (as direct child of root) also
      return;
    else{ //parent is red
      Node g = p.parent;
      
      if(g.left==p && p.right==n){
        //     g         g       g
        //    /         /       /
        //   p     =>  n   =>  p
        //    \       /       /
        //     n     p       n
        g.left = n;
        n.parent = g;
        p.right = n.left;
        n.left = p;
        p.parent = n;
        Node temp = p;
        p = n;
        n = temp;
      }
      else if(g.right==p && p.left==n){
        // g        g       g
        //  \        \       \
        //   p   =>   n   =>  p
        //  /          \       \
        // n            p       n
        g.right = n;
        n.parent = g;
        p.left = n.right;
        n.right = p;
        p.parent = n;
        Node temp = p;
        p = n;
        n = temp;
      }
      
      Node u = null;
      boolean lUncle = true;
      if(g.left==p){
        u = g.right;
        lUncle = false;
      }
      else
        u = g.left;
      
      if(u==null || !u.r){ //parent = red, uncle = black - rotations required!
        if(lUncle){
          //     g(B)                     p(R)                 p(B)
          //    /   \                    /    \               /    \
          //   B1    p(R)      =>      g(B)   n(R)    =>     g(R)   n(R)
          //        /  \              /   \   /  \          /   \   /  \
          //       B2   n(R)         B1   B2  B   B        B1   B2  B   B
          //           /  \
          //          B    B
          if(g!=root){ //root has no parent so no need to do this
            p.parent = g.parent;
            if(g.parent.left==g)
              g.parent.left = p;
            else
              g.parent.right = p;
          }
          else{
            root = p;
            p.parent = null;
          }
          g.parent = p;
          g.right = p.left;
          p.left = g;
          p.r = false;
          g.r = true;
        }
        else{
          //        g(B)              p(R)                 p(B)
          //       /   \             /    \               /    \
          //      p(R)  B1  =>     n(R)    g(B)    =>    n(R)    g(R)
          //     /  \              /   \   /  \          /   \   /  \
          //    n(R) B2           B    B  B2  B1        B    B  B1   B2
          //   /  \
          //  B    B
          if(g!=root){ //root has no parent so no need to do this
            p.parent = g.parent;
            if(g.parent.left==g)
              g.parent.left = p;
            else
              g.parent.right = p;
          }
          else{
            root = p;
            p.parent = null;
          }
          g.parent = p;
          g.left = p.right;
          p.right = g;
          p.r = false;
          g.r = true;
        }
      }
      else { //parent = red, uncle = red
        //           B            R
        //          / \          / \
        //         R   R   =>   B   B
        //        / \ / \      / \ / \
        // new-> R  B B  B    R  B B  B
        //      / \          / \
        //     B   B        B   B
        p.r = false;
        u.r = false;
        g.r = true;
        rebalance(g);
      }
    }
  }

  /**
   * Deletes a given value from the tree.
   * @param i value to delete
   */
  public void delete(int i){
    if(isEmpty())
      return;
    
    Node targetNode = search(root, i);
    if(targetNode==null) //the value we want to delete wasn't in the tree
      return;
    
    Node n = findInOrderSuccessor(targetNode,targetNode.right);
    targetNode.i = n.i;

    Node p = n.parent;
    if(p==null){ //only root left and root is the element we wanted to delete (i.e., n = root)
      root = null;
      return;
    }
    
    if(n.r){
      if(n.left==null && n.right==null){ //Red pre-leaf node with two sentinels; simply delete
        if(p.left==n)
          p.left = null;
        else
          p.right = null;
      }
      else{ //Red pre-leaf node with only one non-sentinel node (Black, of course); replace with the non-sentinel
        Node c = (n.left==null)? n.right : n.left;
        c.parent = p;
        if(p.left==n)
          p.left = c;
        else
          p.right = c;
      }
    }
    else{
      if(n.left==null && n.right==null){ //Black pre-leaf node with two sentinels
        if(p.r){
          //            R                                       R           B
          //           / \                                     / \         / \
          //delete -> B   B <- this can only be black    =>   b   B   =>  b   R
          //         / \ / \                                     / \         / \
          //        b  b b  b                                   b   b       b   b
          //
          // b = sentinel
          if(p.left==n){
            p.left = null;
            p.right.r = true;
          }
          else{
            p.right = null;
            p.left.r = true;
          }
          p.r = false;
        }
        else{
          Node s = (p.left==n)? p.right: p.left;
          if(!s.r){
            if(s==p.right){
              //             B              B           B
              //            / \            / \         / \
              // delete -> B   B <- s  => b   B   =>  b   R
              //          / \ / \            / \         / \
              //          b b b b           b   b       b   b
              // b = sentinels
              p.left = null;
              s.r = true;
            }
            else{
              //             B                   B           B
              //            / \                 / \         / \
              //      s -> B   B <- delete  => B   b   =>  R   b
              //          / \ / \             / \         / \
              //          b b b b            b   b       b   b
              // b = sentinels
              p.right = null;
              s.r = true;
            }
          }
          else{
            if(s==p.right){
              //              B              B             R              B                 B
              //            /  \            / \          /   \          /   \             /   \
              // delete -> B    R     =>   b   R    =>  B     B   =>   R     B     =>    B     B
              //          / \  / \            / \      / \   / \      / \   / \         / \   / \
              //         b  b B   B          B   B    b   B  b b      b B  b   b       R   b b   b
              //             / \ / \        / \ / \      / \           / \            / \
              //             b b b b        b b b b      b b          b   b          b   b
              // b = sentinels
              p.left = null;
              if(p!=root){
                if(p.parent.left==p)
                  p.parent.left = s;
                else
                  p.parent.right = s;
              }
              s.parent = p.parent;
              p.parent = s;
              p.right = s.left;
              s.left.parent = p;
              s.left = p;
              
              s.r = false;
              p.r = true;
              
              Node x = p.right;
              x.parent = s;
              s.left = x;
              p.right = x.left;
              p.parent = x;
              x.left = p;
            }
            else{
              //         B                      B             R              B           B
              //        / \                    / \          /   \           / \         / \
              //       R   B <- delete  =>    R   b  =>    B     B   =>    B   R   =>  B   B
              //     /  \ / \                / \          / \   / \       / \ / \     / \ / \
              //    B   B b  b              B   B         b b  B   b      b b B  b    b b b  R
              //   / \ / \                 / \ / \            / \            / \            / \
              //   b b b b                 b b b b           b   b           b b           b   b
              // b = sentinels
              p.right = null;
              if(p!=root){
                if(p.parent.left==p)
                  p.parent.left = s;
                else
                  p.parent.right = s;
              }
              s.parent = p.parent;
              p.parent = s;
              p.left = s.right;
              s.right.parent = p;
              s.right = p;
              
              s.r = false;
              p.r = true;
              
              Node x = p.left;
              x.parent = s;
              s.right = x;
              p.left = x.right;
              p.parent = x;
              x.right = p;
            }
          }
        }
      }
      else{ //Black pre-leaf node with one non-sentinel node (which can only be Red or else you'd have an imbalance in the subtree rooted at n)
        Node c = (n.left==null)? n.right : n.left;
        c.parent = p;
        c.r = false;
        if(p.left==n)
          p.left = c;
        else
          p.right = c;
      }
    }
      
  }
  
  private Node findInOrderSuccessor(Node n, Node child){
    if(child==null)
      return n;
    else{
      if(child.left==null)
      return child;
    else
      return findInOrderSuccessor(child, child.left);
    }
  }

  /**
   * Searches the tree for the given input value.
   * @param i int to search for
   * @return true if i was found; false otherwise
   */
  public boolean search(int i){
    Node n = search(root, i);
    return (n==null)? false : true;
  }
  
  private Node search(Node node, int i){
    if(node==null)
      return null;
    else{
      if(node.i==i) return node;
      else if(node.i>i) return search(node.left,i);
      else return search(node.right, i);
    }
  }

  /**
   * Checks if tree is empty
   * @return true if tree is empty, false otherwise
   */
  public boolean isEmpty(){
    return root == null;
  }
  
  /**
   * Returns the max element in the RBT so far.
   * @return max element in RBT.
   */
  public int max(){
    return findMax(root).i;
  }
  
  private Node findMax(Node n){
    if(n.right!=null)
      return findMax(n.right);
    else
      return n;
  }
  
  /**
   * Returns the min element in the RBT so far.
   * @return max element in RBT.
   */
  public int min(){
    return findMin(root).i;
  }

  private Node findMin(Node n){
    if(n.left!=null)
      return findMin(n.left);
    else
      return n;
  }
  
  private class Node{
    int i;
    boolean r;
    Node parent;
    Node left;
    Node right;
  }
}
