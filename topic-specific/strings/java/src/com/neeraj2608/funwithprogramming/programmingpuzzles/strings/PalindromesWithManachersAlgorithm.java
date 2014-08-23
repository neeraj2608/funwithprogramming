package com.neeraj2608.funwithprogramming.programmingpuzzles.strings;

import java.util.ArrayList;
import java.util.List;

public class PalindromesWithManachersAlgorithm{
  /**
   * Finds the longest palindrome in a given string using Manacher's algorithm.<br>
   * Efficiency is O(n).<br>
   * <a href="http://tarokuriyama.com/projects/palindrome2.php">Excellent reference</a><br>
   * 
   * <p>If multiple palindromes of the same longest size exist, this method will return the first one.<br>
   * e.g. "abaca" has two palindromes "aba" and "aca". "aba" is returned.
   * 
   * <p>If all the palindromes are trivial (length 1), the first one (i.e., the first character will
   * be returned).<br>
   * e.g. "abc" has all trivial palindromes. "a" is returned.
   * @param s input string
   * @return
   */
  public String findLongestPalindrome(String s){
    if(s.length()==0 || s == null)
      throw new RuntimeException();
    
    int maxLength = 1;
    int maxCenter = 0;
    int[] paLength = new int[(s.length()<<1)-1];
    paLength[0] = 1; paLength[paLength.length-1] = 1;
    for(int i=1; i<paLength.length-1;++i){
      if((i&1) == 1)
        findEvenPalindromes(s, i, paLength);
      else
        findOddPalindromes(s, i, paLength);
      if(maxLength<paLength[i]){
        maxLength = paLength[i];
        maxCenter = i;
      }
      i = tryPredict(i, paLength);
      i--; //this is required because the for loop will do its own increment
    }
    return s.substring((maxCenter-(maxLength-1))/2,((maxCenter+(maxLength-1))/2)+1); 
  }
  
  /**
   * Finds all non-trivial (length > 1) palindromes in a given string using Manacher's algorithm.<br>
   * Efficiency is O(n).<br>
   * <a href="http://tarokuriyama.com/projects/palindrome2.php">Excellent reference</a><br>
   * @param s input string
   * @return all non-trivial palindromes
   */
  public List<String> findAllPalindromes(String s){
    if(s.length()==0 || s == null)
      throw new RuntimeException();
    
    List<String> results = new ArrayList<String>();
    int[] paLength = new int[(s.length()<<1)-1];
    paLength[0] = 1; paLength[paLength.length-1] = 1;
    for(int i=1; i<paLength.length-1;++i){
      if((i&1) == 1)
        findEvenPalindromes(s, i, paLength);
      else
        findOddPalindromes(s, i, paLength);
      if(paLength[i]>1){
        results.add(s.substring((i-(paLength[i]-1))/2,((i+(paLength[i]-1))/2)+1));
      }
      i = tryPredict(i, paLength);
      i--; //this is required because the for loop will do its own increment
    }
    return results;
  }
  
  private void findEvenPalindromes(String s, int i, int[] paLength){
    int paLen = findLengthOfPalindrome(s, i, 1);
    paLength[i] = 2*paLen;
  }
  
  private void findOddPalindromes(String s, int i, int[] paLength){
    int paLen = findLengthOfPalindrome(s, i, 2);
    paLength[i] = 2*paLen + 1;
  }

  private int findLengthOfPalindrome(String s, int i, int expand){
    int paLen = 0;
    while(s.charAt((i+expand)>>1) == s.charAt((i-expand)>>1)){
      paLen++;
      if(((i+expand)>>1 == s.length()-1) || ((i-expand)>>1 == 0))
        break;
      expand += 2;
    }
    return paLen;
  }
  
  private int tryPredict(int i, int[] arr){
    int r = i + arr[i];
    int j = i+1;
    for(;j<r;++j){
      if(arr[i-(j-i)]<(r-j))
        arr[j] = arr[i-(j-i)];
      else if(arr[i-(j-i)]>(r-j))
        arr[j] = r-j;
      else
        break; //must continue the search with this as the new center
    }
    return j;
  }
}
