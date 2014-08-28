import SinglyLinkedList as s

LEFT = 0
RIGHT = 1

class BinarySearchTree:
    def __init__(self):
        self.root = None

    def insert(self, val):
        if self.root == None:
            self.root = Node(val)
        else:
            self._insert(self.root, val)

    def _insert(self, node, val):
        if val <= node.data:
            if node.left == None:
                node.left = Node(val)
            else: self._insert(node.left, val)
        else:
            if node.right == None:
                node.right = Node(val)
            else: self._insert(node.right, val)

    def delete(self, val):
        if(self.root == None):
            return
        self._delete(self.root, None, val)

    def _delete(self, node, parent, val, dir=LEFT):
        if node == None: # we fell off the tree; val isn't in the tree
            return
        if node.data == val:
            if node.left == None and node.right == None: # 0 children
                if parent == None: # i.e, node = self.root
                    self.root = None
                else:
                    if dir == LEFT:
                        parent.left = None
                    else:
                        parent.right = None
            elif node.left != None and node.right != None: # 2 children
                iop = self.in_order_predecessor(node.left, node)
                node.data = iop.data
            else: # only one child
                if node.left != None:
                    replacement = node.left
                else:
                    replacement = node.right
                node.data = replacement.data
                node.right = replacement.right
                node.left = replacement.left
        elif val < node.data:
            self._delete(node.left, node, val, LEFT)
        else:
            self._delete(node.right, node, val, RIGHT)

    def in_order_predecessor(self, node, parent, dir=LEFT):
        if node.right == None:
            if dir == LEFT:
                parent.left = node.left
            else:
                parent.right = node.left
            return node
        else:
            return self.in_order_predecessor(node.right, node, RIGHT)

    def find(self, val):
        return self._find(self.root, val)

    def _find(self, node, val):
        if node == None:
            return False

        if node.data == val:
            return True
        elif val < node.data:
            return self._find(node.left, val)
        else:
            return self._find(node.right, val)

    def isEmpty(self):
        return self.root == None

class Node:
    def __init__(self, data=None):
        self.data = data
        self.left = None
        self.right = None

def to_sorted_doubly_linked_list(bst):
    ''' convert BST to sorted doubly linked list '''
    l = s.SinglyLinkedList()
    last = convert_to_dll(l, bst.root)
    l.head.prev = last
    last.next = l.head
    return l

def convert_to_dll(l, node, prev=None):
    if node==None: return prev
    convert_to_dll(l, node.left, prev)
    newNode = s.Node(node.data)
    newNode.prev = prev
    l.insertAtTail(newNode)
    return convert_to_dll(l, node.right, newNode)

def is_bst(tree):
    ''' return True if given binary tree is BST, False otherwise '''
    if tree.root == None:
        return False
    return _is_bst(tree.root, tree.root.data, tree.root.data, None)

def _is_bst(node, mn, mx, dir):
    if(node == None):
        return True

    if(dir==LEFT and node.data > mx):
        return False

    if(dir==RIGHT and node.data < mn):
        return False

    if _is_bst(node.left, mn, node.data, LEFT) == False:
        return False # fail out early
    elif _is_bst(node.right, node.data, mx, RIGHT) == False:
        return False # fail out early
    else:
        return True

def find_largest_bst(tree):
    '''
    finds the largest bst in a given tree. returns the size of that bst as well
    as its root node.
    '''
    _, node, size = _find_largest_bst(tree.root)
    return node, size

def _find_largest_bst(currNode, bstRootNode=None, bstSize=0):
    if currNode == None:
        return True, None, 0

    is_bst_l, node_l, size_l = _find_largest_bst(currNode.left)
    is_bst_r, node_r, size_r = _find_largest_bst(currNode.right)

    if is_bst_l and is_bst_r:
        if currNode.left == None and currNode.right == None: # leaf
            return True, currNode, 1
        if currNode.left == None and currNode.right != None: # check BST property
            if currNode.data < currNode.right.data:
                return True, currNode, size_r + 1
            else:
                return False, currNode.right, size_r
        if currNode.left != None and currNode.right == None: # check BST property
            if currNode.data >= currNode.left.data:
                return True, currNode, size_l + 1
            else:
                return False, currNode.left, size_l
        if currNode.data >= currNode.left.data and currNode.data < currNode.right.data: # check BST property
            return True, currNode, size_l + size_r + 1
    # this covers the following conditions:
    # * both children were BSTs but this node is not
    # * one child was NOT a BST
    # * both children were NOT BSTs
    if size_l > size_r: return False, node_l, size_l
    else: return False, node_r, size_r
