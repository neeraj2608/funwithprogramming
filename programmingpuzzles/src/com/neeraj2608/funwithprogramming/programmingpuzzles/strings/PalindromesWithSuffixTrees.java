package com.neeraj2608.funwithprogramming.programmingpuzzles.strings;

import java.util.ArrayList;
import java.util.List;

import com.neeraj2608.funwithprogramming.suffixtree.SuffixTree;

/**
 * This class finds all the palindromes in a given string, using
 * suffix and LCP arrays.
 * @author Raj
 */
public class PalindromesWithSuffixTrees{
  /**
   * Returns all non-trivial (length 1) palindromes of the input string.
   * @param s input string
   * @return list of palindromes found
   */
  public List<String> findPalindromes(String s){
    List<String> results = new ArrayList<String>();
    String augmentedString = createAugmentedString(s);
    SuffixTree st = new SuffixTree();
    st.insert(augmentedString);
    findEvenPalindromes(results, s, augmentedString, st);
    findOddPalindromes(results, s, augmentedString, st);
    return results;
  }

  /**
   * For finding palindromes, we augment the input string with "$" and its own reverse.
   * E.g. if the original input string is "acc", we give this method "acc$cca"
   * @param s input string
   * @return augmented string
   */
  private String createAugmentedString(String s){
    return s + "$" + reverse(s);
  }
  
  private String reverse(String s){
    if(s.length()==1)
      return s.substring(0,1);
    return s.substring(s.length()-1) + reverse(s.substring(0,s.length()-1));
  }
  
  /**
   * Finds odd-length palindromes (i.e., palindromes centered on actual characters in the original
   * string).<br>
   * E.g. an odd-length palindrome in "acad" is "aca" which is centered at "c".
   * @param results list of palindromes found
   * @param originalString original input string (NOT augmented)
   * @param suffixArray suffix array
   * @param lcpArray LCP array
   */
  private void findOddPalindromes(List<String> results, String originalString, String augmentedString, SuffixTree st){
    int[] palindromeLengthArray = new int[originalString.length()];
    palindromeLengthArray[0] = 1;
    palindromeLengthArray[palindromeLengthArray.length-1] = 1;
    for(int i=1;i<originalString.length()-1;++i){
      palindromeLengthArray[i] = (2 * st.findLCADepth(augmentedString.substring(i+1), augmentedString.substring(2*originalString.length()-(i-1)))) + 1;
      if(palindromeLengthArray[i]>1)
        results.add(originalString.substring(i-(palindromeLengthArray[i]>>1),i+1+(palindromeLengthArray[i]>>1)));
    }
  }
  
  /**
   * Finds even-length palindromes (i.e., palindromes centered in between actual characters in the original
   * string).<br>
   * E.g. an even-length palindrome in "caad" is "aa" which is centered between the two a's.
   * @param results list of palindromes found
   * @param originalString original input string (NOT augmented)
   * @param suffixArray suffix array
   * @param lcpArray LCP array
   */
  private void findEvenPalindromes(List<String> results, String originalString, String augmentedString, SuffixTree st){
    int[] palindromeLengthArray = new int[originalString.length()-1];
    for(int i=0;i<originalString.length()-1;++i){
      palindromeLengthArray[i] = 2 * st.findLCADepth(augmentedString.substring(i+1),augmentedString.substring(2*originalString.length()-i));
      if(palindromeLengthArray[i]>0)
        results.add(originalString.substring(i+1-(palindromeLengthArray[i]>>1),i+1+(palindromeLengthArray[i]>>1)));
    }
  }

}
