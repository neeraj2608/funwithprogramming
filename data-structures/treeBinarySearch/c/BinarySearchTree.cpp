#include <iostream>
#include <cstdlib>
#include <assert.h>
#include "BinarySearchTree.h"

void BST::insert_into_node(Node* node, int data){
    if(node == NULL) return;

    if(node->data >= data)
        if(node->left == NULL)
            node->left = new Node(data);
        else
            insert_into_node(node->left, data);
    else
        if(node->right == NULL)
            node->right = new Node(data);
        else
            insert_into_node(node->right, data);
}

/*
 * Returns 1 if data found in node; 0 otherwise
 * On exit, currentNode will point to node with data (if found)
 *          parent will point to currentNode's parent
 * */
int BST::search(Node** currentNode, Node** parent, int data){
    if(*currentNode == NULL) return 0;

    if((*currentNode)->data == data) return 1;
    else if((*currentNode)->data > data){
        *parent = *currentNode;
        *currentNode = (*currentNode)->left;
    }
    else{
        *parent = *currentNode;
        *currentNode = (*currentNode)->right;
    }
    return search(currentNode, parent, data);
}

int BST::find_in_node(Node* node, int data){
    if(node == NULL)
        return 0;
    else if(node->data == data)
        return 1;
    else if(node->data > data)
        return find_in_node(node->left, data);
    else
        return find_in_node(node->right, data);
}

int BST::find_node_(Node* node, Node* x){
    if(node == NULL)
        return 0;
    else if(node == x)
        return 1;
    else if(node->data > x->data)
        return find_node_(node->left, x);
    else
        return find_node_(node->right, x);
}

/*
 * inserts data into BST
 * */
void BST::insert(int data){
    if(this == NULL) return;

    if(this->root == NULL)
        this->root = new Node(data);
    else
        insert_into_node(this->root, data);
}

/*
 * deletes data from BST
 * */
void BST::remove(int data){
    if(this == NULL) return;

    Node* x = this->root;
    Node* parent = NULL;
    if(search(&x, &parent, data)){
        if(x->left == NULL && x->right == NULL){ // x was a leaf
            if(x == this->root) this->root = NULL;
            else
                if(parent->left == x) parent->left = NULL;
                else parent->right = NULL;
            delete(x);
        }
        else if(x->left != NULL && x -> right == NULL){ // x had only one child
            if(x == this->root) this->root = x->left;
            else
                if(parent->left == x) parent->left = x->left;
                else parent->right = x->left;
            delete(x);
        }
        else if(x->left == NULL && x -> right != NULL){ // x had only one child
            if(x == this->root) this->root = x->right;
            else
                if(parent->left == x) parent->left = x->right;
                else parent->right = x->right;
            delete(x);
        }
        else{ // x had two children
            Node* tempParent = x;
            Node* succ = x->left;
            while(succ->right != NULL){
                tempParent = succ;
                succ = succ->right;
            }
            x->data = succ->data;
            if(tempParent == x) tempParent->left = succ->left;
            else tempParent->right = succ->left;
            delete(succ);
        }
    }
}

/*
 * finds data in BST
 * returns 1 if found; 0 otherwise
 * */
int BST::find(int data){
    return find_in_node(this->root, data);
}

/*
 * Returns 1 if node x is found in bst
 * Returns 0 otherwise
 * */
int BST::find_node(Node* x){
    if(!(this->root)) return 0;
    else return find_node_(this->root, x);
}

/*
 * Find the lowest common ancestor of the two nodes x and y
 * in bst. x and y may or may not actually be in bst
 * */
Node* find_lca_node(Node* node, Node* x, Node* y){
    if(node == NULL) return NULL;
    if(node == x || node == y || (node->data >= x->data && node->data < y->data)) return node;
    else if(node->data > x->data && node->data > y->data) return find_lca_node(node->left, x, y);
    else return find_lca_node(node->right, x, y);
}

Node* find_lca(BST* bst, Node* x, Node* y){
    if(x == NULL || y == NULL) return NULL;
    if(bst->find_node(x) == 0 || bst->find_node(y) == 0) return NULL;
    else
        if(x->data <= y->data) return find_lca_node(bst->root, x, y);
        else return find_lca_node(bst->root, y, x);
}

/*
 * Construct a binary tree from the given inorder and preorder traversal
 * */
Node* btree_from_inorder_preorder(int ino[], int preo[], int size){
    int MAX_ELEMS = 256;
    int inorderindices[MAX_ELEMS];
    int i;
    for(i=0;i<size;++i)
        inorderindices[ino[i]] = i;

    return btree_ino_preo(preo, inorderindices, 0, size, 0, size);
}

Node* btree_ino_preo(int preo[], int inorderindices[], int inlo, int inhi, int prelo, int prehi){
    if(inlo == inhi - 1) return new Node(preo[prelo]);
    if(inlo >= inhi) return NULL;

    Node* root = new Node(preo[prelo]);

    int leftlower = inlo;
    int leftupper = inorderindices[root->data];
    int leftsize = leftupper - leftlower;
    root->left = btree_ino_preo(preo, inorderindices, leftlower, leftupper, prelo+1, prelo+1+leftsize);

    int rightlower = leftupper + 1;
    int rightupper = inhi;
    int rightsize = rightupper - rightlower;
    root->right = btree_ino_preo(preo, inorderindices, rightlower, rightupper, prelo+1+leftsize, prehi);

    return root;
}

/*
 * Construct a binary tree from the given inorder and postorder traversal
 * */
Node* btree_from_inorder_postorder(int ino[], int posto[], int size){
    int MAX_ELEMS = 256;
    int inorderindices[MAX_ELEMS];
    int i;
    for(i=0;i<size;++i)
        inorderindices[ino[i]] = i;

    return btree_ino_posto(posto, inorderindices, 0, size, 0, size);
}

Node* btree_ino_posto(int posto[], int inorderindices[], int inlo, int inhi, int prelo, int prehi){
    if(inlo == inhi - 1) return new Node(posto[prehi-1]);
    if(inlo >= inhi) return NULL;

    Node* root = new Node(posto[prehi-1]);

    int leftlower = inlo;
    int leftupper = inorderindices[root->data];
    int leftsize = leftupper - leftlower;
    root->left = btree_ino_posto(posto, inorderindices, leftlower, leftupper, prelo, prelo+leftsize);

    int rightlower = leftupper + 1;
    int rightupper = inhi;
    int rightsize = rightupper - rightlower;
    root->right = btree_ino_posto(posto, inorderindices, rightlower, rightupper, prelo+leftsize, prehi-1);

    return root;
}

int main(){
    BST* bst = new BST();

    // test insertion
    bst->insert(2);
    bst->insert(3);
    bst->insert(1);
    bst->insert(5);

    // test search
    assert(bst->find(1) == 1);
    assert(bst->find(2) == 1);
    assert(bst->find(3) == 1);
    assert(bst->find(5) == 1);

    // test deletion
    bst->remove(2); //delete root
    assert(bst->find(1) == 1);
    assert(bst->find(2) == 0);
    assert(bst->find(3) == 1);
    assert(bst->find(5) == 1);

    bst->remove(5);
    assert(bst->root->data == 1);
    assert(bst->find(1) == 1);
    assert(bst->find(3) == 1);
    assert(bst->find(5) == 0);

    bst->remove(1); //delete root
    assert(bst->find(1) == 0);
    assert(bst->find(3) == 1);

    bst->remove(3);
    assert(bst->find(3) == 0);

    // test LCA
    Node* node1 = new Node(1);
    Node* node2 = new Node(2);
    Node* node3 = new Node(3);
    Node* node4 = new Node(4);
    Node* node5 = new Node(5);
    Node* node6 = new Node(6);
    Node* node7 = new Node(7);
    Node* node8 = new Node(8);
    node2->left = node1;
    node2->right = node6;
    node6->left = node4;
    node4->left = node3;
    node4->right = node5;
    node6->right = node7;
    node7->right = node8;
    bst = new BST();
    bst->root = node2;
    assert(find_lca(bst, node3, node5)->data == 4);
    assert(find_lca(bst, node5, node3)->data == 4);
    assert(find_lca(bst, node5, node4)->data == 4);
    assert(find_lca(bst, node5, node1)->data == 2);
    assert(find_lca(bst, new Node(9), node1) == NULL);

    // test binary tree from inorder and preorder
    // this should create a tree that looks like:
    //          5
    //       7     9
    //     6    11  15
    int inorder[] = {6, 7, 5, 11, 9, 15};
    int preorder[] = {5, 7, 6, 9, 11, 15};
    Node* root = btree_from_inorder_preorder(inorder, preorder, 6);
    assert(root->data == 5);
    assert(root->left->data == 7);
    assert(root->left->left->data == 6);
    assert(root->right->data == 9);
    assert(root->right->left->data == 11);
    assert(root->right->right->data == 15);

    // test binary tree from inorder and postorder
    // this should create a tree that looks like:
    //          5
    //       7     9
    //     6    11  15
    int postorder[] = {6, 7, 11, 15, 9, 5};
    root = btree_from_inorder_postorder(inorder, postorder, 6);
    assert(root->data == 5);
    assert(root->left->data == 7);
    assert(root->left->left->data == 6);
    assert(root->right->data == 9);
    assert(root->right->left->data == 11);
    assert(root->right->right->data == 15);

}
