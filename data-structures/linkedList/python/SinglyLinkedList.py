class SinglyLinkedList:
    def __init__(self):
        self.head = None
        self.tail = None
        self.count = 0

    def insert(self, node):
        ''' insert at head '''
        if(self.isEmpty()):
            self.head = node
            self.tail = node
        else:
            node.setNext(self.head)
            self.head = node
        self.count = self.count + 1

    def insertAtTail(self, node):
        ''' insert at tail '''
        if self.isEmpty():
            self.head = node
            self.tail = node
        else:
            self.tail.next = node
            self.tail = node
        self.count = self.count + 1

    def delete(self):
        ''' Delete from head. Returns data in the node that was deleted '''
        if self.isEmpty():
            return None
        else:
            self.count = self.count - 1
            data = self.head.getData()
            self.head = self.head.getNext()
            if self.head == None: self.tail = None
            return data

    def isEmpty(self):
        return not self.count

    def reverse(self):
        ''' Reverse the list '''
        prev = None
        self.tail = self.head

        while(self.head.getNext() != None):
            temp = self.head.getNext()
            self.head.setNext(prev)
            prev = self.head
            self.head = temp

        self.head.setNext(prev)

    def recursiveReverse(self):
        ''' Reverse the list using recursion '''
        self.tail = self.head
        self.recRev(None, self.head)

    def recRev(self, prev, curr):
        ''' helper for recursiveReverse '''
        if(curr == None):
            self.head = prev;
        else:
            self.recRev(curr, curr.getNext())
            curr.setNext(prev)

    def isCyclic(self):
        self.slow = self.head

        if(self.head == None or self.head.getNext() == None):
            return False
        self.fast = self.head.getNext().getNext()

        while(self.fast and self.fast.getNext()):
            if(self.fast == self.slow):
                return True
            self.slow = self.slow.getNext()
            self.fast = self.fast.getNext().getNext()
        return False

class Node:
    def __init__(self, data=None, next=None):
        self.data = data
        self.next = next
        self.prev = None

    def getNext(self):
        return self.next

    def setNext(self, next):
        self.next = next

    def getData(self):
        return self.data

    def setData(self, data):
        self.data = data

    def __str__(self):
        return str(self.data)

def sublistSplit(inputList):
    slow = inputList.head
    count = 1

    # corner case of empty list
    if(inputList.head == None):
        return None, None

    # corner case of only one element
    if(inputList.head.getNext() == None):
        return inputList, None

    fast = inputList.head.getNext().getNext()

    # run to the end of the list
    while(fast):
        count = count + 1
        slow = slow.getNext()
        if not fast.getNext():
            break
        fast = fast.getNext().getNext()

    secondList = SinglyLinkedList()
    secondList.head = slow.getNext()
    secondList.count = inputList.count - count

    inputList.count = count
    slow.setNext(None)

    return inputList, secondList

def insert_into_cyclic_sorted_list_node(node, val):
    if(node == None):
        node = Node(val)
        node.setNext(node)
        return node

    left = node
    right = node.getNext()
    while(True):
        if ((left.getData() <= val and val < right.getData()) or
            (left.getData() > right.getData() and
             (val < left.getData() or val > right.getData()))):
            return insert_node(val, left, right)
        left = left.getNext()
        right = right.getNext()

def insert_node(val, left, right):
    newNode = Node(val)
    left.setNext(newNode)
    newNode.setNext(right)
    return newNode

# errors:
# 1. Not using self. in many places after refactoring
# 2. Not paying attention to the algorithm:
#    I had:
#      self.head.setNext(node)
#      self.head = node
#    instead of:
#      node.setNext(self.head)
#      self.head = node

