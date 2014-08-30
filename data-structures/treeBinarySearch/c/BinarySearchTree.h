
using namespace std;

class Node{
    public:
        struct Node* left;
        struct Node* right;
        int data;
        Node(int data): data(data) {};
};

class BST{
    private:
        void insert_into_node(Node* node, int data);
        int search(Node** currentNode, Node** parent, int data);
        int find_in_node(Node* node, int data);
        int find_node_(Node* node, Node* x);

    public:
        Node* root;

        /*
         * inserts data into BST
         * */
        void insert(int data);

        /*
         * deletes data from BST
         * */
        void remove(int data);

        /*
         * finds data in BST
         * returns 1 if found; 0 otherwise
         * */
        int find(int data);

        /*
         * Returns 1 if node x is found in bst
         * Returns 0 otherwise
         * */
        int find_node(Node* x);
};
