from unittest import TestCase, main
import BinarySearchTree as b

class TestBinarySearchTree(TestCase):
    def setUp(self):
        self.class_under_test = b.BinarySearchTree()

    def test_new_bst_is_empty(self):
        self.assertTrue(self.class_under_test.isEmpty())

    def test_insert(self):
        self.class_under_test.insert(1)
        self.class_under_test.insert(2)
        self.class_under_test.insert(3)
        self.class_under_test.insert(4)
        self.assertFalse(self.class_under_test.isEmpty())

    def test_find(self):
        self.class_under_test.insert(1)
        self.class_under_test.insert(2)
        self.class_under_test.insert(3)
        self.class_under_test.insert(4)
        self.assertTrue(self.class_under_test.find(1))
        self.assertTrue(self.class_under_test.find(2))
        self.assertTrue(self.class_under_test.find(3))
        self.assertTrue(self.class_under_test.find(4))
        self.assertFalse(self.class_under_test.find(5))

    def test_delete(self):
        self.class_under_test.insert(1)
        self.class_under_test.insert(2)
        self.class_under_test.insert(3)
        self.class_under_test.insert(4)

        self.class_under_test.delete(1)
        self.assertFalse(self.class_under_test.find(1))
        self.assertTrue(self.class_under_test.find(2))
        self.assertTrue(self.class_under_test.find(4))

        self.class_under_test.delete(4)
        self.assertFalse(self.class_under_test.find(4))
        self.assertTrue(self.class_under_test.find(2))
        self.assertTrue(self.class_under_test.find(3))

        self.class_under_test.delete(2)
        self.assertFalse(self.class_under_test.isEmpty())

        self.class_under_test.delete(3)
        self.assertTrue(self.class_under_test.isEmpty())

    def test_to_sorted_doubly_linked_list(self):
        self.class_under_test.insert(1)
        self.class_under_test.insert(3)
        self.class_under_test.insert(4)
        self.class_under_test.insert(2)
        result = b.to_sorted_doubly_linked_list(self.class_under_test)
        self.assertTrue(result.head.data == 1)
        self.assertTrue(result.head.prev.data == 4)

    def test_is_bst(self):
        node1 = b.Node(1)
        node2 = b.Node(2)
        node3 = b.Node(3)
        node4 = b.Node(4)
        node5 = b.Node(5)

        # not a bst
        node1.left = node2
        node1.right = node4
        node4.left = node3
        node4.right = node5
        self.class_under_test.root = node1

        self.assertFalse(b.is_bst(self.class_under_test))

        # is a bst
        node1 = b.Node(1)
        node2 = b.Node(2)
        node3 = b.Node(3)
        node4 = b.Node(4)
        node5 = b.Node(5)

        node2.left = node1
        node2.right = node4
        node4.left = node3
        node4.right = node5
        self.class_under_test.root = node2

        self.assertTrue(b.is_bst(self.class_under_test))

    def test_find_largest_bst(self):
        node, size = b.find_largest_bst(self.class_under_test)
        self.assertTrue(node==None)
        self.assertTrue(size==0)

        # whole tree is a bst
        #          2 <- bst!
        #        1   4
        #          3   5
        node1 = b.Node(1)
        node2 = b.Node(2)
        node3 = b.Node(3)
        node4 = b.Node(4)
        node5 = b.Node(5)

        node2.left = node1
        node2.right = node4
        node4.left = node3
        node4.right = node5
        self.class_under_test.root = node2

        node, size = b.find_largest_bst(self.class_under_test)
        self.assertTrue(node.data==2)
        self.assertTrue(size==5)

        # part of tree is a bst; there's only one bst
        #          2
        #        1   3
        #          6   5 <- bst!
        #            4   7
        #                  8
        node1 = b.Node(1)
        node2 = b.Node(2)
        node3 = b.Node(3)
        node4 = b.Node(4)
        node5 = b.Node(5)
        node6 = b.Node(6)
        node7 = b.Node(7)
        node8 = b.Node(8)

        node2.left = node1
        node2.right = node3
        node3.left = node6
        node3.right = node5
        node5.left = node4
        node5.right = node7
        node7.right = node8
        self.class_under_test.root = node2

        node, size = b.find_largest_bst(self.class_under_test)
        self.assertTrue(node.data==5)
        self.assertTrue(size==4)

        # part of tree is a bst; there's more than one bst
        #             2
        # bst! -> 1       3
        #       0  1.5  6   5 <- largest bst!
        #                 4   7
        #                       8
        node0 = b.Node(0)
        node1 = b.Node(1)
        node15 = b.Node(1.5)
        node2 = b.Node(2)
        node3 = b.Node(3)
        node4 = b.Node(4)
        node5 = b.Node(5)
        node6 = b.Node(6)
        node7 = b.Node(7)
        node8 = b.Node(8)

        node1.left = node0
        node1.right = node15
        node2.left = node1
        node2.right = node3
        node3.left = node6
        node3.right = node5
        node5.left = node4
        node5.right = node7
        node7.right = node8
        self.class_under_test.root = node2

        node, size = b.find_largest_bst(self.class_under_test)
        self.assertTrue(node.data==5)
        self.assertTrue(size==4)

if __name__ == '__main__':
    main()

