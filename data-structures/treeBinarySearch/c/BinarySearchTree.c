#include <stdio.h>
#include <assert.h>

typedef struct Node{
    int data;
    struct Node* left;
    struct Node* right;
} Node;

typedef struct BST{
    Node* root;
} BST;

/*
 * create a new BST
 * */
BST* createBST(){
    BST* bst = (BST*) malloc(sizeof(BST));
    bst->root = NULL;
    return bst;
}

/*
 * create a new node
 * */
Node* createNode(int data){
    Node* node = (Node*) malloc(sizeof(Node));
    node->data = data;
    node->left = node->right = NULL;
    return node;
}

/*
 * inserts data into BST
 * */
void insert(BST* bst, int data){
    if(bst == NULL) return;

    if(bst->root == NULL)
        bst->root = createNode(data);
    else
        insert_into_node(bst->root, data);
}

void insert_into_node(Node* node, int data){
    if(node == NULL) return;

    if(node->data >= data)
        if(node->left == NULL)
            node->left = createNode(data);
        else
            insert_into_node(node->left, data);
    else
        if(node->right == NULL)
            node->right = createNode(data);
        else
            insert_into_node(node->right, data);
}

/*
 * deletes data from BST
 * */
void delete(BST* bst, int data){
    if(bst == NULL) return;

    Node* x = bst->root;
    Node* parent = NULL;
    if(search(&x, &parent, data)){
        if(x->left == NULL && x->right == NULL){ // x was a leaf
            if(x == bst->root) bst->root = NULL;
            else
                if(parent->left == x) parent->left == NULL;
                else parent->right == NULL;
            free(x);
        }
        else if(x->left != NULL && x -> right == NULL){ // x had only one child
            if(x == bst->root) bst->root = x->left;
            else
                if(parent->left == x) parent->left == x->left;
                else parent->right == x->left;
            free(x);
        }
        else if(x->left == NULL && x -> right != NULL){ // x had only one child
            if(x == bst->root) bst->root = x->right;
            else
                if(parent->left == x) parent->left == x->right;
                else parent->right == x->right;
            free(x);
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
            free(succ);
        }
    }
}

/*
 * Returns 1 if data found in node; 0 otherwise
 * On exit, currentNode will point to node with data (if found)
 *          parent will point to currentNode's parent
 * */
int search(Node** currentNode, Node** parent, int data){
    if(*currentNode == NULL) return 0;

    if((*currentNode)->data == data)
        return 1;
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

/*
 * finds data in BST
 * returns 1 if found; 0 otherwise
 * */
int find(BST* bst, int data){
    return find_in_node(bst->root, data);
}

int find_in_node(Node* node, int data){
    if(node == NULL)
        return 0;
    else if(node->data == data)
        return 1;
    else if(node->data > data)
        return find_in_node(node->left, data);
    else
        return find_in_node(node->right, data);
}

int main(){
    BST* bst = createBST();

    // test insertion
    insert(bst,2);
    insert(bst,3);
    insert(bst,1);
    insert(bst,5);

    // test search
    assert(find(bst, 1) == 1);
    assert(find(bst, 2) == 1);
    assert(find(bst, 3) == 1);
    assert(find(bst, 5) == 1);

    // test deletion
    delete(bst, 2); //delete root
    assert(find(bst, 1) == 1);
    assert(find(bst, 2) == 0);
    assert(find(bst, 3) == 1);
    assert(find(bst, 5) == 1);

    delete(bst, 5);
    assert(find(bst, 1) == 1);
    assert(find(bst, 3) == 1);
    assert(find(bst, 5) == 0);

    delete(bst, 1); //delete root
    assert(find(bst, 1) == 0);
    assert(find(bst, 3) == 1);

    delete(bst, 3);
    assert(find(bst, 3) == 0);
}
