package com.neeraj2608.funwithprogramming.algorithms.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MergeSortAuxiliarySpaceLinkedLists{
  public static List<Integer> mergeSortInPlace(List<Integer> list){
    int initSize = 1;
    List<Integer> result = new ArrayList<Integer>();
    while(initSize < list.size()){

      int i = 0;
      int j = initSize;

      while(true){
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        int stop = j+initSize;

        for(;i<Math.min(j, list.size());++i){
          list1.add(list.get(i));
        }

        for(;j<Math.min(stop,list.size());++j){
          list2.add(list.get(j));
        }
        
        if(list1.isEmpty() && list2.isEmpty()) break;

        i=j;
        j+=initSize;

        while(!list1.isEmpty() || !list2.isEmpty()){
          if(list1.isEmpty()){
            result.addAll(list2);
            list2.removeAll(list2);
          }
          else if(list2.isEmpty()){
            result.addAll(list1);
            list1.removeAll(list1);
          }
          else if(list1.get(0) < list2.get(0))
            result.add(list1.remove(0));
          else
            result.add(list2.remove(0));
        }
      }
      
      list = result;
      result = new ArrayList<Integer>();
      initSize <<= 1;

    }
    return list;
  }


  public static List<Integer> mergeSortInPlaceWithIterators(List<Integer> list){
    int initSize = 1;
    List<Integer> result = new ArrayList<Integer>();
    while(initSize < list.size()){
      ListIterator<Integer> ita = list.listIterator();
      ListIterator<Integer> itb = list.listIterator();
      int a = ita.next();
      int b = itb.next();
      int aSize = initSize;
      int bSize = initSize;
      for(int i=0;i<initSize;i++)
        b = itb.next();

      while(aSize>0 || bSize>0){ 
        while(aSize>0 && bSize>0){
          if(a<b){
            result.add(a);
            aSize--;
            if(!ita.hasNext()){
              aSize = 0;
              break;
            }
            a = ita.next();
          }
          else{
            result.add(b);
            bSize--;
            if(!itb.hasNext()){
              bSize = 0;
              break;
            }
            b = itb.next();
          }
        }

        while(aSize>0){
          result.add(a);
          aSize--;
          if(!ita.hasNext()){
            aSize = 0;
            break;
          }
          a = ita.next();
        }

        while(bSize>0){
          result.add(b);
          bSize--;
          if(!itb.hasNext()){
            bSize = 0;
            break;
          }
          b = itb.next();
        }

        while(ita.nextIndex()<itb.nextIndex()){
          if(!ita.hasNext()){
            aSize = 0;
            break;
          }
          aSize = 1;
          a = ita.next();
        }

        while(bSize<initSize){
          if(!itb.hasNext()){
            bSize = 0;
            break;
          }
          b = itb.next();
          bSize++;
        }
      }

      list = result;
      result = new ArrayList<Integer>();

      initSize = initSize << 1;
    }

    return list;
  }
}
