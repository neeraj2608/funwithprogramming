class SinglyLinkedList:
    def __init__(self):
        self.head = None
        self.count = 0

    def insert(self, node):
        if(self.isEmpty()):
            self.head = node;
        else:
            node.setNext(self.head)
            self.head = node
        self.count = self.count + 1

    def delete(self):
        ''' Returns data in the node that was deleted '''
        if self.isEmpty():
            return None
        else:
            self.count = self.count - 1
            data = self.head.getData()
            self.head = self.head.getNext()
            return data

    def isEmpty(self):
        return not self.count

    def reverse(self):
        ''' Reverse the list '''
        prev = None
        while(self.head.getNext() != None):
            temp = self.head.getNext()
            self.head.setNext(prev)
            prev = self.head
            self.head = temp

        self.head.setNext(prev)

    def recursiveReverse(self):
        ''' Reverse the list using recursion '''
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

# errors:
# 1. Not using self. in many places after refactoring
# 2. Not paying attention to the algorithm:
#    I had:
#      self.head.setNext(node)
#      self.head = node
#    instead of:
#      node.setNext(self.head)
#      self.head = node

