from unittest import TestCase, main
import SinglyLinkedList as s

class TestSinglyLinkedList(TestCase):
    def setUp(self):
        self.cut = s.SinglyLinkedList()

    def test_new_list_is_empty(self):
        self.assertTrue(self.cut.isEmpty())

    def test_inserts(self):
        self.cut.insert(s.Node(1))
        self.cut.insert(s.Node(2))
        self.cut.insert(s.Node(3))
        self.cut.insert(s.Node(4))
        self.assertTrue(self.cut.count == 4)

    def test_deletes(self):
        self.cut.insert(s.Node(1))
        self.cut.insert(s.Node(2))
        self.cut.insert(s.Node(3))
        self.cut.insert(s.Node(4))
        self.cut.delete()
        self.cut.delete()
        self.cut.delete()
        self.assertFalse(self.cut.isEmpty())
        self.cut.delete()
        self.assertTrue(self.cut.isEmpty())

    def test_reverse(self):
        # list with single element
        self.cut.insert(s.Node(1))
        self.cut.reverse()
        self.assertTrue(self.cut.delete() == 1)

        # list with multiple elements
        self.cut.insert(s.Node(1))
        self.cut.insert(s.Node(2))
        self.cut.insert(s.Node(3))
        self.cut.insert(s.Node(4))
        self.cut.reverse()
        self.assertTrue(self.cut.count == 4)
        self.assertTrue(self.cut.delete() == 1)
        self.assertTrue(self.cut.delete() == 2)
        self.assertTrue(self.cut.delete() == 3)
        self.assertTrue(self.cut.delete() == 4)
        self.assertTrue(self.cut.delete() == None)

    def test_recursive_reverse(self):
        # list with single element
        self.cut.insert(s.Node(1))
        self.cut.recursiveReverse()
        self.assertTrue(self.cut.delete() == 1)

        # list with multiple elements
        self.cut.insert(s.Node(1))
        self.cut.insert(s.Node(2))
        self.cut.insert(s.Node(3))
        self.cut.insert(s.Node(4))
        self.cut.recursiveReverse()
        self.assertTrue(self.cut.count == 4)
        self.assertTrue(self.cut.delete() == 1)
        self.assertTrue(self.cut.delete() == 2)
        self.assertTrue(self.cut.delete() == 3)
        self.assertTrue(self.cut.delete() == 4)
        self.assertTrue(self.cut.delete() == None)

if __name__ == '__main__':
    main()
