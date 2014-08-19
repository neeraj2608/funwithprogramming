package com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions.chapter9sortingsearching;

import java.util.Arrays;
import java.util.Comparator;

public class CrackingTheCodingInterviewSolutionsChapter9{
  
  
  /**
   * QUESTION: CTCI 9.1 (page 66)
   * 
   * You are given two sorted arrays, A and B, and A has a large enough buffer at the end 
   * to hold B Write a method to merge B into A in sorted order
   * @param a array A
   * @param b array B
   */
  private static void mergeSortedArrays(int[]a, int[]b){
    if(a.length < b.length)
      mergeSortedArrays(b,a);
    
    int length = a.length - b.length;
    int temp = b[0];
    int b1;
    
    for(int i=0;i<length;i++){
      if(a[i]>temp){
        b1 = temp;
        temp = a[i];
        a[i] = b1;
        if(temp > b[1]){
          b1 = temp;
          temp = b[1];
          b[1] = b1;
          siftDown(b);
        }
      }
      else if(a[i]>b[1]){
        b1 = b[1];
        b[1] = a[i];
        a[i] = b1;
        siftDown(b);
        if(b[1] > temp){
          b1 = temp;
          temp = b[1];
          b[1] = b1;
          siftDown(b);
        }
      }
    }
    
    for(int i=0;i<b.length-1;i++){
      if(temp < b[i+1]){
        b1 = temp;
        temp = b[i+1];
        b[i+1] = b1;
      }
      a[length+i] = b[i+1];
    }
    a[a.length-1] = temp;
  }

  private static void siftDown(int[] b){
    int i = 1;
    int j = 2;
    while(j<b.length && b[i]>b[j] ){
      int temp = b[i];
      b[i] = b[j];
      b[j] = temp;
      i++;
      j++;
    }
  }
  
  /**
   * QUESTION: CTCI 9.1 (page 66)
   * 
   * You are given two sorted arrays, A and B, and A has a large enough buffer at the end 
   * to hold B Write a method to merge B into A in sorted order
   * 
   * This is a more efficient implementation.
   * @param a array A
   * @param b array B
   */
  private static void mergeSortedFromBack(int[]a, int[]b){
    if(a.length < b.length)
      mergeSortedFromBack(b,a);
    
    int avar = a.length-b.length-1;
    int bvar = b.length-1;
    for(int i=a.length-1;i>=0;i--){
      if(a[avar]>b[bvar]){
        a[i] = a[avar];
        avar--;
        if(avar == -1){
          while(bvar>=0){
            i--;
            a[i] = b[bvar];
            bvar--;
          }
        }
      }
      else{
        a[i] = b[bvar];
        bvar--;
        if(bvar == -1){
          while(avar>=0){
            i--;
            a[i] = a[avar];
            avar--;
          }
        }
      }
      
    }
  }
  
  /**
   * QUESTION: CTCI 9.1 (page 66)
   * 
   * You are given two sorted arrays, A and B, and A has a large enough buffer at the end 
   * to hold B Write a method to merge B into A in sorted order
   * 
   * This is an even more efficient implementation.
   * @param a array A
   * @param b array B
   */
  private static void mergeSortedFromBackBetter(int[]a, int[]b){
    int i = a.length-b.length-1;
    int j = b.length-1;
    int k = a.length-1;
    
    while(i>=0 && j>=0){
      if(a[i]>b[j])
        a[k--] = a[i--];
      else
        a[k--] = b[j--];
    }
    
    while(j>=0)
      a[k--] = b[j--];
  }
  
  /**
   * QUESTION: CTCI 9.2 (page 66)
   * Write a method to sort an array of strings so that all the anagrams are next to each 
   * other.
   * 
   * Uses a comparator.
   * @param arr array to sort
   */
  private static void sortAnagramsLibrary(String[] arr){
    Arrays.sort(arr, new CrackingTheCodingInterviewSolutionsChapter9.MyComparator());
  }
  
  static class MyComparator implements Comparator<String>{

    @Override
    public int compare(String s1, String s2){
      char[] s1sorted = s1.toCharArray();
      Arrays.sort(s1sorted);
      char[] s2sorted = s2.toCharArray();
      Arrays.sort(s2sorted);
      return (new String(s1sorted)).compareTo(new String(s2sorted));
    }
    
  }
  
  /**
   * QUESTION: CTCI 9.2 (page 66)
   * Write a method to sort an array of strings so that all the anagrams are next to each 
   * other.
   * 
   * Custom code.
   * @param arr array to sort
   */
  private static void sortAnagramsBespoke(String[] arr){
    for(int i=1;i<arr.length;i++){
      int k = i;
      int j = i-1;
      while(j>=0 && alphaBetaSort(arr[k],arr[j], false)>0){
        swap(arr,k,j);
        j--;k--;
      }
    }
  }

  private static void swap(String[] arr, int k, int j){
    String temp = arr[k];
    arr[k] = arr[j];
    arr[j] = temp;
  }

  private static int alphaBetaSort(String string1, String string2, boolean flag){ //+ve if string1 < string2
    if(string1.length() > string2.length())
      alphaBetaSort(string2, string1,true);
    
    int mult = (flag)? -1:1;
    int retVal = 0;
    
    for(int i=0;i<string1.length();i++){
      if(string1.charAt(i)<string2.charAt(i)){
        retVal = 1;
        break;
      }
      else if(string1.charAt(i)>string2.charAt(i)){
        retVal = -1;
        break;
      }
    }
    return mult * retVal;
  }
  
  /**
   * QUESTION: CTCI 9.3 (page 66)
   * Given  a  sorted  array  of  n  integers  that  has  been  rotated  an  unknown  number  of 
   * times, give an O(log n) algorithm that finds an element in the array You may assume 
   * that the array was originally sorted in increasing order
   * EXAMPLE:
   * Input: find 5 in array (15 16 19 20 25 1 3 4 5 7 10 14)
   * Output: 8 (the index of 5 in the array)
   * @param arr rotated array to search
   * @param elem element that we're looking for
   * @return element that we're looking for
   */
  private static int searchRotArray(int[] arr, int elem){
    return search(0,arr.length-1,arr,elem);
  }

  private static int search(int left, int right, int[] arr, int elem){
    if(left>right) return -1;
    int mid = (left+right)>>1;
    if(arr[mid]==elem) return mid;
    if(arr[left]<arr[mid]){ //good (non-rotated)
      if(elem >= arr[left] && elem < arr[mid]) //recurse good (non-rotated)
        return search(left,mid-1,arr,elem);
      else // recurse bad (rotated)
        return search(mid+1,right,arr,elem);
    }
    else if(arr[mid]<arr[right]){ //good (non-rotated)
      if(elem > arr[mid] && elem <= arr[right]) //recurse good (non-rotated)
        return search(mid+1,right,arr,elem);
      else // recurse bad (rotated)
        return search(left,mid-1,arr,elem);
    }
    return -1;
  }
  
  /**
   * QUESTION: CTCI 9.5 (page 66)
   * Given a sorted array of strings which is interspersed with empty strings, write a method to find the location of a given string 
   * Example:  find  “ball”  in  [“at”,  “”,  “”,  “”,  “ball”,  “”,  “”,  “car”,  “”,  “”,  “dad”,  “”,  “”]  will  return  4
   * Example: find “ballcar” in [“at”, “”, “”, “”, “”, “ball”, “car”, “”, “”, “dad”, “”, “”] will return -1
   * @param arr array to search
   * @param elem string to search for
   * @return location of string we were searching for
   */
  private static int binSearch(String[] arr, String elem){
    return search(0, arr.length-1, arr, elem);
  }

  private static int search(int left, int right, String[] arr, String elem){
    if(left>right) return -1;
    int mid = (left+right)>>1;

    int oldmid = mid;
    while(mid>=left && arr[mid].equals(""))
      mid--;
    if(mid == left-1)
      return search(oldmid+1,right,arr,elem);
    
    if(arr[mid].equals(elem)) return mid;
    
    if(arr[mid].compareTo(elem)<0)
      return search(mid+1,right,arr,elem);
    else
      return search(left,mid-1,arr,elem);
  }
  
  /**
   * QUESTION: CTCI 9.6 (page 66)
   * 
   * Given a matrix in which each row and each column is sorted, write a method to find 
   * an element in it.
   * @param arr
   * @param elem
   * @return
   */
  private static String findInSortedMtx(int[][]arr, int elem){
    return findElemMtx(arr.length-1, 0, elem, arr);
  }

  private static String findElemMtx(int r, int c, int elem, int[][] arr){
    if(r<0 || c>arr.length-1) return "not found";
    if(arr[r][c] == elem) return r+", "+c;
    
    if(arr[r][c]>elem)
      return findElemMtx(--r,c,elem,arr);
    else{
      if(arr[r][c]<elem)
        return findElemMtx(r,++c,elem,arr);
      else
        return "not found";
    }
  }
  
  /**
   * QUESTION: CTCI 9.7 (page 66)
   * 
   * A circus is designing a tower routine consisting of people standing atop one another’s shoulders
   * For practical and aesthetic reasons, each person must be both shorter and lighter than the person
   * below him or her Given the heights and weights of each person in the circus, write a method to
   * compute the largest possible number of people in such a tower
   * EXAMPLE:
   * Input (ht, wt): (65, 100) (70, 150) (56, 90) (75, 190) (60, 95) (68, 110)
   * Output:  The  longest  tower  is  length  6  and  includes  from  top  to  bottom: 
   * (56,  90) (60,95) (65,100) (68,110) (70,150) (75,190)
   *
   * @param arr array of heights and weights
   * @return largest possible number of people in the tower.
   */
  private static int towerBuilder(HtWt[] arr){
    Arrays.sort(arr, new HeightComparator());
    int result = 1;
    int least = 0;
    for(int i=1;i<arr.length;++i){
      HtWt h1 = arr[least];
      HtWt h2 = arr[i];
      if(h1.weight<h2.weight && h1.height < h2.height){
        result++;
        least = i;
      }
    }
    return result;
  }
  
  static class HtWt{
    int height;
    int weight;
    HtWt(int height, int weight){
      this.height = height;
      this.weight = weight;
    }
  }
  
  static class HeightComparator implements Comparator<HtWt>{
    @Override
    public int compare(HtWt h1, HtWt h2){ //weight first, then height
      if(h1.weight < h2.weight)
        return -1;
      else if(h1.weight > h2.weight)
        return 1;
      else{
        if(h1.height < h2.height)
          return -1;
        else if(h1.height > h2.height)
          return 1;
        else
          return 0;
      }
    }
  }

  public static void main(String[] args){
    int[] test2 = {0,4,9,11,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE};
    int[] test3 = {2,5,10,12};
    mergeSortedArrays(test2, test3);
    test2 = new int[]{0,4,9,11,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE};
    test3 = new int[]{2,5,10,12};
    mergeSortedFromBack(test2, test3);
    test2 = new int[]{0,4,9,11,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE};
    test3 = new int[]{2,5,10,12};
    mergeSortedFromBackBetter(test2, test3);
    
    String[] testStringArray = {"abc","dca","cad","bac","xyz"};
    sortAnagramsLibrary(testStringArray);
    testStringArray = new String[]{"abc","dca","cad","bac","xyz"};
    sortAnagramsBespoke(testStringArray);
    
    int[] test4 = {15,2,5,10,12,13,14};
    System.out.println(searchRotArray(test4,15));
    
    String[] test5 = {"all","","","","ball","","","call","","doll",""};
    System.out.println(binSearch(test5,"boll"));
    
    int[][]a9 = new int[3][3];
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        a9[i][j] = i+j;
      }
    }
    System.out.println(findInSortedMtx(a9,3));
    
    HtWt[] arr2 = {new HtWt(65,100),new HtWt(70,150),new HtWt(56,90),new HtWt(75,190),new HtWt(60,110),new HtWt(68,110)};
    System.out.println(towerBuilder(arr2));
  }

}
