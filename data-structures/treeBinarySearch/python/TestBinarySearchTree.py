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

if __name__ == '__main__':
    main()

