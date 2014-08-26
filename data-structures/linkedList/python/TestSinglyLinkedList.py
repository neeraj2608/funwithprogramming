from unittest import TestCase, main
import SinglyLinkedList as s

class TestSinglyLinkedList(TestCase):
    def setUp(self):
        self.class_under_test = s.SinglyLinkedList()

    def test_new_list_is_empty(self):
        self.assertTrue(self.class_under_test.isEmpty())

    def test_inserts(self):
        self.class_under_test.insert(s.Node(1))
        self.class_under_test.insert(s.Node(2))
        self.class_under_test.insert(s.Node(3))
        self.class_under_test.insert(s.Node(4))
        self.assertTrue(self.class_under_test.count == 4)

    def test_deletes(self):
        self.class_under_test.insert(s.Node(1))
        self.class_under_test.insert(s.Node(2))
        self.class_under_test.insert(s.Node(3))
        self.class_under_test.insert(s.Node(4))
        self.class_under_test.delete()
        self.class_under_test.delete()
        self.class_under_test.delete()
        self.assertFalse(self.class_under_test.isEmpty())
        self.class_under_test.delete()
        self.assertTrue(self.class_under_test.isEmpty())

    def test_reverse(self):
        # list with single element
        self.class_under_test.insert(s.Node(1))
        self.class_under_test.reverse()
        self.assertTrue(self.class_under_test.delete() == 1)

        # list with multiple elements
        self.class_under_test.insert(s.Node(1))
        self.class_under_test.insert(s.Node(2))
        self.class_under_test.insert(s.Node(3))
        self.class_under_test.insert(s.Node(4))
        self.class_under_test.reverse()
        self.assertTrue(self.class_under_test.count == 4)
        self.assertTrue(self.class_under_test.delete() == 1)
        self.assertTrue(self.class_under_test.delete() == 2)
        self.assertTrue(self.class_under_test.delete() == 3)
        self.assertTrue(self.class_under_test.delete() == 4)
        self.assertTrue(self.class_under_test.delete() == None)

    def test_recursive_reverse(self):
        # list with single element
        self.class_under_test.insert(s.Node(1))
        self.class_under_test.recursiveReverse()
        self.assertTrue(self.class_under_test.delete() == 1)

        # list with multiple elements
        self.class_under_test.insert(s.Node(1))
        self.class_under_test.insert(s.Node(2))
        self.class_under_test.insert(s.Node(3))
        self.class_under_test.insert(s.Node(4))
        self.class_under_test.recursiveReverse()
        self.assertTrue(self.class_under_test.count == 4)
        self.assertTrue(self.class_under_test.delete() == 1)
        self.assertTrue(self.class_under_test.delete() == 2)
        self.assertTrue(self.class_under_test.delete() == 3)
        self.assertTrue(self.class_under_test.delete() == 4)
        self.assertTrue(self.class_under_test.delete() == None)

    def test_is_cyclic(self):
        self.assertFalse(self.class_under_test.isCyclic())

        self.class_under_test.insert(s.Node(1))
        self.class_under_test.insert(s.Node(2))
        self.class_under_test.insert(s.Node(3))
        self.class_under_test.insert(s.Node(4))
        self.class_under_test.insert(s.Node(5))
        self.assertFalse(self.class_under_test.isCyclic())

        node3 = s.Node(3)
        node5 = s.Node(5)
        self.class_under_test.insert(s.Node(1))
        self.class_under_test.insert(s.Node(2))
        self.class_under_test.insert(node3)
        self.class_under_test.insert(s.Node(4))
        self.class_under_test.insert(node5)
        node3.setNext(node5)
        self.assertTrue(self.class_under_test.isCyclic())

    def test_sublist_split(self):
        # even-length list
        self.class_under_test.insert(s.Node(1))
        self.class_under_test.insert(s.Node(2))
        self.class_under_test.insert(s.Node(3))
        self.class_under_test.insert(s.Node(4))
        first, second = s.sublistSplit(self.class_under_test)
        self.assertTrue(first.count == 2)
        self.assertTrue(first.delete() == 4)
        self.assertTrue(first.delete() == 3)
        self.assertTrue(second.count == 2)
        self.assertTrue(second.delete() == 2)
        self.assertTrue(second.delete() == 1)

        # odd-length list
        # self.class_under_test is the same as first above, which was emptied by the delete()
        self.class_under_test.insert(s.Node(1))
        self.class_under_test.insert(s.Node(2))
        self.class_under_test.insert(s.Node(3))
        self.class_under_test.insert(s.Node(4))
        self.class_under_test.insert(s.Node(5))
        first, second = s.sublistSplit(self.class_under_test)
        self.assertTrue(first.count == 3)
        self.assertTrue(first.delete() == 5)
        self.assertTrue(first.delete() == 4)
        self.assertTrue(first.delete() == 3)
        self.assertTrue(second.count == 2)
        self.assertTrue(second.delete() == 2)
        self.assertTrue(second.delete() == 1)

    def test_insert_into_cyclic_sorted_list(self):
        node = None
        node = s.insert_into_cyclic_sorted_list_node(node, 1)
        self.assertTrue(node)
        self.assertTrue(node.getNext() == node)

        node5 = s.Node(5)
        node4 = s.Node(4)
        node3 = s.Node(3)
        node1 = s.Node(1)
        self.class_under_test.insert(node5)
        node5.setNext(node1)
        self.class_under_test.insert(node4)
        self.class_under_test.insert(node3)
        self.class_under_test.insert(s.Node(2))
        self.class_under_test.insert(node1)

        # insert element larger than max in list
        self.assertTrue(node5.getNext().getData()==1)
        s.insert_into_cyclic_sorted_list_node(node3, 6)
        self.assertTrue(self.class_under_test.isCyclic())
        self.assertTrue(node5.getNext().getData()==6)

        # insert element smaller than min in list
        s.insert_into_cyclic_sorted_list_node(node3, 0)
        self.assertTrue(self.class_under_test.isCyclic())
        self.assertTrue(node5.getNext().getNext().getData()==0)

        # insert element in range of list
        self.assertTrue(node4.getNext().getData()==5)
        s.insert_into_cyclic_sorted_list_node(node3, 4)
        self.assertTrue(self.class_under_test.isCyclic())
        self.assertTrue(node4.getNext().getData()==4)

if __name__ == '__main__':
    main()
