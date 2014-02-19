package com.neeraj2608.funwithprogramming.programmingpuzzles.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class finds all the palindromes in a given string, using
 * suffix and LCP arrays.
 * @author Raj
 */
public class PalindromesWithSuffixArrays{
  /**
   * Returns all non-trivial (length 1) palindromes of the input string.
   * @param s input string
   * @return list of palindromes found
   */
  public List<String> findPalindromes(String s){
    List<String> results = new ArrayList<String>();
    String augmentedString = createAugmentedString(s);
    int[] suffixArray = createSuffixArray(augmentedString);
    int[] lcpArray = createLCPArray(augmentedString,suffixArray);
    findEvenPalindromes(results, s, suffixArray, lcpArray);
    findOddPalindromes(results, s, suffixArray, lcpArray);
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
   * Creates the suffix array of the input string. To save space, we store only the starting index
   * of the suffix instead of the actual suffix string.
   * e.g.
   * <ol>
   * <li>suffix array of 'abc' is [0, 1, 2] (which corresponds to ["abc","bc","c"])
   * <li>suffix array of 'caa' is [2, 1, 0] (which corresponds to ["a","aa","caa"])
   * </ol>
   * @param augmentedString input string, which for finding palindromes is augmented with "$" and its own reverse. E.g.
   *        if the original input string is "acc", we give this method "acc$cca"
   * @return
   */
  private int[] createSuffixArray(String augmentedString){
    SuffixObject[] suffixObjectsArray = new SuffixObject[augmentedString.length()];
    for(int i=0;i<augmentedString.length();++i){
      SuffixObject o = new SuffixObject();
      o.s = augmentedString.substring(i);
      o.offset = i;
      suffixObjectsArray[i] = o;
    }
    
    Arrays.sort(suffixObjectsArray, new SuffixObjectComparator());
    int[] suffixArray = new int[augmentedString.length()];
    for(int i=0;i<augmentedString.length();++i)
      suffixArray[i] = suffixObjectsArray[i].offset;
    
    return suffixArray;
  }
  
  /**
   * This object is used in construction of the suffix array. We use this object to keep track
   * of the starting index of a given suffix. This is useful because when we sort the suffix
   * strings lexicographically, the starting indices get shuffled from their original positions.<br>
   * e.g. for input string "caa", we first have the suffix array ["caa","aa","a"] which corresponds
   * to the natural order of the suffixes' starting indices (e.g. "caa" starts at 0, "aa" starts at 1 etc).
   * When we then sort lexicographically, we get ["a","aa","caa"]. The starting indices of these suffixes
   * is no longer in the natural order (e.g. "a" is 2 and "aa" is 1). The SuffixObject object helps keep
   * track of these shuffled indices because these indices are what we finally want in our suffix array.
   */
  private class SuffixObject{
    public String s;
    public int offset;
  }
  
  private class SuffixObjectComparator implements Comparator<SuffixObject>{
    @Override
    public int compare(SuffixObject o1, SuffixObject o2){
      return o1.s.compareTo(o2.s); 
    }
  }

  /**
   * Creates the LCP array of the input string.
   * e.g.
   * <ol>
   * <li>
   * LCP array of 'abc' is [0, 0, -1] = ["abc" and "bc" have 0 characters in common, "bc" and "c" have 0 characters in common, don't care]<br>
   * ["abc","bc","c"] come from the suffix array.
   * </li>
   * <li>
   * LCP array of 'caa' is [1, 0, -1] = ["a" and "aa" have 1 characters in common, "aa" and "caa" have 0 characters in common, don't care]<br>
   * ["a","aa","caa"] come from the suffix array.
   * </li>
   * </ol>
   * @param augmentedString input string, which for finding palindromes is augmented with "$" and its own reverse. E.g.
   *        if the original input string is "acc", we give this method "acc$cca"
   * @param suffixArray suffix array       
   * @return the LCP array.
   */
  private int[] createLCPArray(String augmentedString, int[] suffixArray){
    int[] lcpArray = new int[augmentedString.length()];
    for(int i=0;i<augmentedString.length()-1;i++){
      lcpArray[i] = numberOfSameChars(augmentedString,suffixArray[i],suffixArray[i+1]);
    }
    lcpArray[augmentedString.length()-1] = -1;
    return lcpArray;
  }

  private int numberOfSameChars(String s, int i, int j){
    String s1 = s.substring(i);
    String s2 = s.substring(j);
    int index=0;
    while(index<s1.length() && index < s2.length() && s1.charAt(index)==s2.charAt(index))
      index++;
    return index;
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
  private void findOddPalindromes(List<String> results, String originalString, int[] suffixArray, int[] lcpArray){
    int[] palindromeLengthArray = new int[originalString.length()];
    palindromeLengthArray[0] = 1;
    palindromeLengthArray[palindromeLengthArray.length-1] = 1;
    for(int i=1;i<originalString.length()-1;++i){
      palindromeLengthArray[i] = (2 * findMin(lcpArray, findIndexOf(suffixArray, i+1), findIndexOf(suffixArray, (2*originalString.length())-(i-1)))) + 1;
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
  private void findEvenPalindromes(List<String> results, String originalString, int[] suffixArray, int[] lcpArray){
    int[] palindromeLengthArray = new int[originalString.length()-1];
    for(int i=0;i<originalString.length()-1;++i){
      palindromeLengthArray[i] = 2 * findMin(lcpArray, findIndexOf(suffixArray, i+1), findIndexOf(suffixArray, (2*originalString.length())-i));
      if(palindromeLengthArray[i]>0)
        results.add(originalString.substring(i+1-(palindromeLengthArray[i]>>1),i+1+(palindromeLengthArray[i]>>1)));
    }
  }

  /**
   * Used to find the minimum element in the range [from, to) of the LCP array. This is a naive solution to finding
   * the smallest common prefix! A better solution is a more complicated <a href="http://wcipeg.com/wiki/RMQ">Range Minimum Query</a>
   * algorithm.
   * 
   * @param lcpArray LCP array
   * @param from start of range
   * @param to end of range (not inclusive)
   * @return minimum element found in the given range
   */
  private int findMin(int[] lcpArray, int from, int to){
    if(from > to)
      return findMin(lcpArray, to, from);
    
    int min = Integer.MAX_VALUE;
    for(int i=from;i<to;++i){
      if(lcpArray[i]<min)
        min = lcpArray[i];
    }
    return min;
  }

  /**
   * Finds the position of a given element in the suffix array. We take this position and use it to
   * create a range on the LCP array when we're finding the odd and even palindromes.
   * 
   * @param suffixArray
   * @param elem to find
   * @return position of element we are looking for
   */
  private int findIndexOf(int[] suffixArray, int elem){
    for(int i=0;i<suffixArray.length;++i){
      if(suffixArray[i] == elem)
        return i;
    }
    return -1;
  }

}
