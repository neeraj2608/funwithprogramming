package com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions.chapter1;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Raj
 * Chapter 1: Arrays and Strings
 */
public class CrackingTheCodingInterviewSolutionsChapter1{
  
  /**
   * QUESTION: CTCI 1.1 (page 48)
   * Implement an algorithm to determine if a string has all unique characters What if you 
   * can not use additional data structures?
   * 
   * This is the additional data structure one.
   * @param s input string 
   * @return true if there are duplicates; false otherwise.
   */
  private static boolean findDups(String s){
    HashMap<Character, Integer> h = new HashMap<Character, Integer>();
    for(int i=0; i<s.length(); i++){
      char c = s.charAt(i);
      if(h.containsKey(c))
        h.put(c, h.get(c)+1);
      else
        h.put(c, 1);
    }
    
    boolean found = false;
    for(Character c: h.keySet())
      if(h.get(c)>1){
        found = true;
        break;
      }
    
    return found;
  }
  
  /**
   * QUESTION: CTCI 1.1 (page 48)
   * Implement an algorithm to determine if a string has all unique characters What if you 
   * can not use additional data structures?
   * 
   * This is the additional data structure one.
   * @param s input string 
   * @return true if there are duplicates; false otherwise.
   */
  private static boolean findDups2(String s){
    HashMap<Character, Integer> h = new HashMap<Character, Integer>();
    for(int i=0; i<s.length(); i++){
      char c = s.charAt(i);
      if(h.containsKey(c))
        return true;
      else
        h.put(c, 1);
    }
    
    return false;
  }
  
  /**
   * QUESTION: CTCI 1.1 (page 48)
   * Implement an algorithm to determine if a string has all unique characters What if you 
   * can not use additional data structures?
   * 
   * This is without any additional data structure.
   * @param s input string 
   * @return true if there are duplicates; false otherwise.
   */
  private static boolean findDups3(String s){
    boolean[] b = new boolean[256];
    for(int i=0; i<s.length(); i++){
      int c = s.charAt(i) - '0';
      if(b[c])
        return true;
      else
        b[c] = true;
    }
    
    return false;
  }
  
  /**
   * QUESTION: CTCI 1.2 (page 48)
   * Write code to reverse a C-Style String (C-String means that “abcd” is represented as 
   * five characters, including the null character )
   * @param s input string
   * @return reversed string
   */
  public static String reverseString(String s){
    char[] c = recReverse(s.toCharArray(), 0, s.length()-1);
    return new String(c);
  }
  
  private static char[] recReverse(char[] c, int start, int end){
    if(start>=end)
      return c;
    char tmp = c[start];
    c[start] = c[end];
    c[end] = tmp;
    return recReverse(c, ++start, --end);
  }
  
  /**
   * QUESTION: CTCI 1.2 (page 48)
   * Write code to reverse a C-Style String (C-String means that “abcd” is represented as 
   * five characters, including the null character )
   * @param s input string
   * @return reversed string
   */
  private static String reverseString2(String s){ //CTCI 1.2 48
    char[] c = s.toCharArray();
    recReverse2(c, 0, s.length()-1);
    return new String(c);
  }
  
  private static void recReverse2(char[] c, int start, int end){
    if(start>=end)
      return;
    char tmp = c[start];
    c[start] = c[end];
    c[end] = tmp;
    recReverse(c, ++start, --end);
  }
  
  /**
   * QUESTION: CTCI 1.3 (page 48)
   * Design an algorithm and write code to remove the duplicate characters in a string without
   * using any additional buffer NOTE: One or two additional variables are fine An extra copy
   * of the array is not
   * TODO: FOLLOW UP
   * Write the test cases for this method
   * @param s input string
   * @return input string with duplicates removed
   */
  private static String remDups(String s){
    if(null == s)
      throw new RuntimeException("string is null!");
    
    char[] c = s.toCharArray();
    int k = 1;
    for(int i=1;i<c.length;i++){
      boolean foundDups = false;
      for(int j=0;j<i;j++){
        if(c[i]==c[j]){
          foundDups = true;
          break;
        }
      }
      if(!foundDups){
        c[k] = c[i];
        k++;
      }
    }
    
    return new String(c,0,k);
  }
  
  /**
   * QUESTION: CTCI 1.4 (page 48)
   * Write a method to decide if two strings are anagrams or not
   * @param s1 first string
   * @param s2 second string
   * @return true if both input strings are anagrams; false otherwise.
   */
  private static boolean checkIfAnagrams(String s1, String s2){
    if(s1 == null || s2 == null)
      return false;
    
    char[] c1 = s1.toCharArray();
    char[] c2 = s2.toCharArray();
    
    Arrays.sort(c1);
    Arrays.sort(c2);
    
    if(Arrays.equals(c1,c2))
      return true;
    else
      return false;
  }
  
  /**
   * QUESTION: CTCI 1.5 (page 48)
   * Write a method to replace all spaces in a string with ‘%20'.
   * @param s input string
   * @return input string with spaces replaced by '%20'.
   */
  private static String replaceSpaces(String s){
    char[] c = s.toCharArray();
    char[] c1 = new char[3*c.length];
    int k = 0;
    for(int i=0;i<c.length;i++){
      if(c[i]==' '){
        c1[k] = '%';
        c1[k+1] = '2';
        c1[k+2] = '0';
        k = k+3;
      }
      else{
        c1[k] = c[i];
        k++;
      }
    }
    return new String(c1,0,k);
  }
  
  /**
   * QUESTION: CTCI 1.6 (page 48)
   * Given an image represented by an NxN matrix, where each pixel in the image is 4 
   * bytes, write a method to rotate the image by 90 degrees.
   * Can you do this in place?
   * 
   * This is the version that uses auxiliary storage.
   * @param arr array representation of matrix
   * @return rotated matrix.
   */
  private static int[][] rotMat(int[][] arr){
    int[][] a = new int[arr.length][arr.length];
    int n = arr.length-1;
    for(int row=0;row<=n;row++){
      for(int col=0;col<=n;col++){
        a[row][col] = arr[n-col][row];
      }
    }
    return a;
  }
  
  /**
   * QUESTION: CTCI 1.6 (page 48)
   * Given an image represented by an NxN matrix, where each pixel in the image is 4 
   * bytes, write a method to rotate the image by 90 degrees.
   * Can you do this in place?
   * 
   * This is the in-place version.
   * @param arr array representation of matrix
   * @return rotated matrix.
   */
  private static void rotMatNoBuf(int[][] a){
    //TODO: provide explanatory comments.
    int n = a.length - 1;
    
    for(int i=0;i<n;i++){
      for(int j=0;j<n-i;j++){
        int dist = n - (i+j);
        int tmp = a[i][j];
        a[i][j] = a[i+dist][j+dist];
        a[i+dist][j+dist] = tmp;
      }
    }
    
    for(int i=0;i<n/2;i++){
      for(int j=0;j<n;j++){
        int tmp = a[i][j];
        a[i][j] = a[n-i][j];
        a[n-i][j] = tmp;
      }
    }
  }
  
  /**
   * QUESTION: CTCI 1.7 (page 48).
   * Write an algorithm such that if an element in an MxN matrix is 0, its entire row and 
   * column is set to 0.
   * @param a array representation of given matrix.
   */
  private static void setZeros(int[][] a){
    int[] rows = new int[a.length];
    int[] cols = new int[a.length];
    int n = a.length;
    
    for(int i=0;i<n;i++){
      for(int j=0;j<n;j++){
        if(a[i][j]==0){
          rows[i] = -1;
          cols[j] = -1;
        }
      }
    }
    
    for(int i=0;i<n;i++){
      if(rows[i]==-1){
        for(int j=0;j<n;j++){
          a[i][j] = 0;
        }
      }
      if(cols[i]==-1){
        for(int j=0;j<n;j++){
          a[j][i] = 0;
        }
      }
    }
    
  }
  
  /**
   * QUESTION: CTCI 1.8 (page 48).
   * Assume you have a method isSubstring which checks if one word is a substring of 
   * another Given two strings, s1 and s2, write code to check if s2 is a rotation of s1 using 
   * only one call to isSubstring (i e , “waterbottle” is a rotation of “erbottlewat”)
   * 
   * @param rotS rotated string
   * @param orgS original string
   * @return true if rotS is a rotated version of orgS; false otherwise.
   */
  private static boolean isRotated(String rotS, String orgS){ //CTCI 1.8 48
    if(rotS.length() != orgS.length())
      return false;
    
    String concatS = rotS + rotS;
    if(concatS.matches(".*"+orgS+".*")) //stand-in for isSubstring
      return true;
    else
      return false;
  }
  
  public static void main(String[] args){
    String s7 = "aa0bcc0d";
    System.out.println(reverseString2(reverseString(s7)));
    System.out.println("dups found: "+findDups(s7));
    System.out.println("dups found: "+findDups2(s7));
    System.out.println("dups found: "+findDups3(s7));
    System.out.println("dups rmed: "+remDups(s7));
    s7 = "abcdefgh";
    System.out.println("dups found: "+findDups(s7));
    System.out.println("dups found: "+findDups2(s7));
    System.out.println("dups found: "+findDups3(s7));
    
    String s8 = "abababab";
    String s9 = "abababac";
    System.out.println("anagrams "+checkIfAnagrams(s8, s8));
    System.out.println("anagrams "+checkIfAnagrams(s8, s9));
    System.out.println(replaceSpaces("abcd"));
    
    int[][]a = new int[3][3];
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        a[i][j] = i;
      }
    }
    int[][] b = rotMat(a);
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        System.out.print(b[i][j]+" ");
      }
      System.out.println();
    }
    
    rotMatNoBuf(a);
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        System.out.print(a[i][j]+" ");
      }
      System.out.println();
    }
  
    String orgS = "abc";
    String rotS = "bca";
    String nonRotS = "bac";
    System.out.println("bca is abc rotated "+isRotated(rotS, orgS));
    System.out.println("bac is abc rotated "+isRotated(nonRotS, orgS));
    
    int[][]a1 = new int[3][3];
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        a1[i][j] = i+1;
      }
    }
    setZeros(a1);
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        System.out.print(a1[i][j]+" ");
      }
      System.out.println();
    }
    
    a1[1][1] = 0;
    setZeros(a1);
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        System.out.print(a1[i][j]+" ");
      }
      System.out.println();
    }
  }

}
