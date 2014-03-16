package com.neeraj2608.funwithprogramming.algorithms.sort;

import com.neeraj2608.funwithprogramming.linkedlist.SLLNode;
import com.neeraj2608.funwithprogramming.linkedlist.SinglyLinkedList;

public class MergeSortInPlaceLinkedLists{
  public static void mergeSortInPlace(SinglyLinkedList<Integer> list){
    SLLNode<Integer> a = list.getHead();
    SLLNode<Integer> b = a;
    SLLNode<Integer> constructList = null;
    SLLNode<Integer> newHead = null;
    int initSize = 1;
    boolean endMerge = false;

    while(!endMerge){
      while(true){
        int aSize = 0;
        int bSize = initSize;
        
        while(b!=null && aSize<initSize){ //we need a null guard in case proceeding by initSize places makes b fall off the list. This is ONLY
                                          //meaningful if initSize > 1 (see explanation below)
          b = b.getNext();
          aSize++;
        }
        
        if(initSize>1 && b==null){ //b == null (falling off the list in the while above) matters only if the initSize is more than 1.
                                   //If the initSize == 1, then even a null b may still leave an a to be handled (this only happens if
                                   //the list's size is odd):
                                   //E.g., consider 3,2,1 and initSize == 1. After the first iteration, a and b both
                                   //are at the 1. Proceeding by 1 will make b = null but there is still an a to be processed!
                                   //Once we're past initSize = 1, a null b actually becomes meaningful, because the only way we can get a null b
                                   //is if we're trying to step b forward by initSize places (in the while loop above) and fall off the list before
                                   //getting there. In other words, if a alone is large enough to cover the whole list.
          endMerge = true;
          break;
        }

        while(b!=null && aSize>0 && bSize>0){
          if(a.getData()<b.getData()){
            if(constructList==null){
              newHead = a;
            }
            else{
              constructList.setNext(a);
            }
            constructList = a;
            a = a.getNext();
            aSize--;
          } else{
            if(constructList==null){
              newHead = b;
            }
            else{
              constructList.setNext(b);
            }
            constructList = b;
            b = b.getNext();
            bSize--;
          }
        }

        while(a!=null && aSize>0){ //continue until a is exhausted for this phase
          constructList.setNext(a);
          constructList = a;
          a = a.getNext();
          aSize--;
        }

        while(b!=null && bSize>0){ //continue until b is exhausted for this phase
          constructList.setNext(b);
          constructList = b;
          b = b.getNext();
          bSize--;
        }

        if(b == null){ //b is the leading edge of our exploration. Once it is null, we have nothing left to do for this phase
          constructList.setNext(null); //make sure you mark the end of the list at this point before starting the next phase
          break;
        }
        
        a = b; //continue in the same phase. b is at the end of its sublist for this phase. a should start from here in the next iteration.
      }
      
      initSize <<= 1;
      a = newHead;
      b = a;
      constructList = null; //don't forget to reset the constructList node so that newHead can be updated in the next phase if it needs to
    }

    list.setHead(newHead);

  }
}
