package com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions.chapter8recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrackingTheCodingInterviewSolutionsChapter8{
  
  /**
   * QUESTION: CTCI 8.1 (page 64)
   * Write a method to generate the nth Fibonacci number
   * 
   * This recursive solution assumes the first Fibonacci number is 0.
   * @param n the nth fibonacci number
   * @return
   */
  private static int nthRecFibStartsWith0(int n){ //fib(1) = 0, fib(2) = 1
    if(n <= 1) return 0;
    if(n == 2) return 1;
    return nthRecFibStartsWith0(n-1) + nthRecFibStartsWith0(n-2);
  }
  
  /**
   * QUESTION: CTCI 8.1 (page 64)
   * Write a method to generate the nth Fibonacci number
   * 
   * This iterative solution assumes the first Fibonacci number is 0.
   * @param n the nth fibonacci number
   * @return
   */
  private static int nthIterFibStartsWith0(int n){ //fib(1) = 0, fib(2) = 1
    if(n <= 1) return 0;
    if(n == 2) return 1;
    int x1 = 0, x2 = 1, y = 0;
    for(int i=0;i<n-2;i++){
      y = x1 + x2;
      x1 = x2;
      x2 = y;
    }
    return y;
  }
  
  /**
   * QUESTION: CTCI 8.1 (page 64)
   * Write a method to generate the nth Fibonacci number
   * 
   * This recursive solution assumes the first Fibonacci number is 1.
   * @param n the nth fibonacci number
   * @return
   */
  private static int nthRecFibStartsWith1(int n){ //fib(1) = 1, fib(2) = 1
    return (n > 2) ? nthRecFibStartsWith1(n-1) + nthRecFibStartsWith1(n-2) : 1;
  }
  
  /**
   * QUESTION: CTCI 8.3 (page 64)
   * Write a method that returns all subsets of a set
   * 
   * @param n list representing the input set
   */
  private static void generateSubs(List<Integer> list){ // CTCI 8.3 64
    List<ArrayList<Integer>> result = genSubs(list);
    result.add(new ArrayList<Integer>());
  }
  
  private static List<ArrayList<Integer>> genSubs(List<Integer> l){
    List<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> temp = new ArrayList<Integer>();
    temp.add(l.get(0));
    list.add(temp);
    if(l.size()==1)
      return list;
    List<ArrayList<Integer>> list1 = genSubs(l.subList(1, l.size()));
    list.addAll(list1);
    for(List<Integer> l1: list1){
      temp = new ArrayList<Integer>();
      temp.add(l.get(0));
      temp.addAll(l1);
      list.add(temp);
    }
    return list;
  }
  
  /**
   * QUESTION: CTCI 8.3 (page 64)
   * Write a method that returns all subsets of a set
   * 
   * This is a counting based solution.
   * @param n list representing the input set
   */
  private static void generateSubs1(List<Integer> list){
    int count = 0;
    int i = list.size();
    while(i>0){
      count = count << 1 | 1; 
      i--;
    }
    List<List<Integer>> result = genSubs1(list.toArray(new Integer[list.size()]), count);
  }
  
  private static List<List<Integer>> genSubs1(Integer[] x, int n){
    List<List<Integer>> list = new ArrayList<List<Integer>>();
    if(n==0){
      list.add(new ArrayList<Integer>());
      return list;
    }
    List<List<Integer>> l = genSubs1(x, n-1);
    list.addAll(l);
    List<Integer> temp = new ArrayList<Integer>();
    int count = 0;
    while(n > 0){
      if((n&1)==1)
        temp.add(x[count]);
      n >>= 1;
      count++;
    }
    list.add(temp);
    return list;
  }
  
  /**
   * QUESTION: CTCI 8.4 (page 64)
   * Write a method to compute all permutations of a string.
   * 
   * @param s input string
   */
  private static void permute(String s){
    List<String> permuteList = permuteString(s);
    for(String a: permuteList)
      System.out.println(a);
  }
  
  private static List<String> permuteString(String s){
    List<String> list = new ArrayList<String>();
    if(s.length()==1){
      list.add(s);
      return list;
    }
    for(int i=0;i<s.length();i++){
      for(String a: permuteString(s.substring(0,i)+s.substring(i+1)))
        list.add(s.charAt(i)+a);
    }
    return list;
  }
  
  /**
   * QUESTION: CTCI 8.5 (page 64)
   * Implement an algorithm to print all valid (e g , properly opened and closed) combinations of n-pairs of parentheses
   * EXAMPLE:
   * input: 3 (e g , 3 pairs of parentheses)
   * output: ()()(), ()(()), (())(), ((()))
   *
   * @param n number of pairs of braces to print combinations of
   */
  private static void printBraces(int n){
    if(n>2){
      int x = n;
      while(x>0){
        printOpening(1);
        x--;
      }
      System.out.print(", ");
    }
    for(int i=0;i<n;i++){
      printOpening(i);
      printOpening(n-i);
      System.out.print(", ");
    }
  }
  
  private static void printOpening(int n){
    if(n==0)
      return;
    System.out.print("(");
    printOpening(n-1);
    printClosing();
  }
  
  private static void printClosing(){
    System.out.print(")");
  }
  
  /**
   * QUESTION: CTCI 8.6 (page 64)
   * Implement the “paint fill” function that one might see on many image editing programs
   * That  is,  given  a  screen  (represented  by  a  2  dimensional  array  of  Colors),
   * a point, and a new color, fill in the surrounding area until you hit a border of that
   * color.
   * 
   * @param a input array
   * @param color color to paint
   * @param x x coordinate of input point
   * @param y y coordinate of input point
   */
  private static void fillPaint(int[][] a, int color, int x, int y){
    if(x < 0 || y < 0 || x >= a.length || y >= a.length)
      return;
    
    if(a[x][y] == color)
      return;
    
    a[x][y] = color;
    
    fillPaint(a, color, x+1, y);
    fillPaint(a, color, x, y+1);
    fillPaint(a, color, x-1, y);
    fillPaint(a, color, x, y-1);
  }
  
  /**
   * QUESTION: CTCI 8.7 (page 64)
   * Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents) and 
   * pennies (1 cent), write code to calculate the number of ways of representing n cents
   * @param n
   */
  private static void Mc(int n){
    List<int[]> result = new ArrayList<int[]>();
    makeChange(result, new int[4], 25, n);
  }
  
  private static void makeChange(List<int[]> list, int[] x, int i, int n){
    if(n==0)
      return;
    int m = n/i;
    int index = 0;
    int newi = 1;
    switch(i){
      case(25):
        index=3;
        newi = 10;
        break;
      case(10):
        index=2;
        newi = 5;
        break;
      case(5):
        index=1;
        break;
    }
    int[] x1 = Arrays.copyOf(x, x.length);
    x1[index] = m;
    if((n-(m*i))==0)
      list.add(x1);
    if(i==1) return;
    
    int count = m;
    for(int j=0;j<=count;j++){
      x1[index] = j;
      makeChange(list, x1, newi, (n-(j*i)));
    }
  }
  
  /**
   * QUESTION: CTCI 8.8 (page 64)
   * Write an algorithm to print all ways of arranging eight queens on a chess board so 
   * that none of them share the same row, column or diagonal.
   */
  private static void Qnpos(){
    System.out.println();
    for(int i=0;i<8;i++){
      int[] occ = {-1, -1, -1, -1, -1, -1, -1, -1};
      occ[0] = i;
      QnPosPrint(occ,0);
    }
  }

  private static void QnPosPrint(int[] occ, int currCol){
    if(currCol==7){
      for(int i=0;i<8;i++){
        System.out.println(i+", "+occ[i]);
      }
      System.out.println("----");
      return;
    }
    for(int row=0;row<8;row++){
        boolean okay = true;
        int x = 0;
        while(currCol-x>=0){
          if(occ[currCol-x]==row || occ[currCol-x]+x+1==row || occ[currCol-x]-x-1==row) //'+1' and '-1' prevents jumping diagonally to right, without +/- prevents jumping straight to right
            okay = false;
          x++;
        }
        if(okay){
          int[] oldOcc = Arrays.copyOf(occ,occ.length);
          occ[currCol+1] = row;
          QnPosPrint(occ, currCol+1);
          occ = oldOcc;
        }
    }
  }
  
  public static void main(String[] args){
    System.out.println(nthRecFibStartsWith0(24));
    System.out.println(nthIterFibStartsWith0(24));
    System.out.println(nthRecFibStartsWith1(0));
    permute("ab");
    printBraces(3);
    List<Integer> l1 = new ArrayList<Integer>();
    l1.add(1);
    l1.add(2);
    l1.add(3);
    l1.add(4);
    generateSubs(l1);
    generateSubs1(l1);
    
    int[][] x = {{1, 1, 1, 1},
                 {0, 0, 0, 1},
                 {1, 1, 0, 1},
                 {1, 1, 0, 1}};
    fillPaint(x, 0, 3, 0);
    
    int test = 20;
    Mc(test);

    Qnpos();
  }

}
