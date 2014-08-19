package com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions.chapter5bitmanipulation;

public class CrackingTheCodingInterviewSolutionsChapter5{
  /**
   * QUESTION: CTCI 5.1 (page 58)
   * You are given two 32-bit numbers, N and M, and two bit positions, i and j Write a 
   * method to set all bits between i and j in N equal to M (e g , M becomes a substring of 
   * N located at i and starting at j) 
   * EXAMPLE:
   * Input: N = 10000000000, M = 10101, i = 2, j = 6
   * Output: N = 10001010100
   * @param N
   * @param M
   * @param i
   * @param j
   * @return N with the changes described above.
   */
  private static int setString(int N, int M, int i, int j){
    int mask = 0;
    for(int k=0;k<=j-i;k++)
      mask = 1 << 1 | 1;
    mask = mask << i;
    
    M = M | ~mask;
    N = N&M;
    M = M & mask;
    N = N|M;
    return N;
  }
  
  /**
   * QUESTION: CTCI 5.2 (page 58)
   * Given a (decimal - e g 3 72) number that is passed in as a string, print the binary
   * representation. If the number can not be represented accurately in binary, print “ERROR”
   * @param s input string
   * @return
   */
  private static String convertDecStringToBinary(String s){
    String wholePart = s.substring(0,s.indexOf('.'));
    String decPart = s.substring(s.indexOf('.'));
    String result = convertIntToBinary(Integer.parseInt(wholePart))+"."+convertFracStringToBinary(decPart);
    if(result.length()>32)
      throw new RuntimeException("ERROR");
    return result;
  }
  
  private static String convertIntToBinary(int i){
    String s = "";
    while(i>0){
      s = (i%2)+ s;
      i>>=1;
    }
    return s;
  }
  
  private static String convertIntToBinary1(int i){
    String s = "";
    while(i>0){
      s = (i&1)+ s;
      i>>=1;
    }
    return s;
  }
  
  private static String convertIntStringToBinary(String s){
    String wholePart = s.substring(0,s.indexOf('.'));
    String result = convertIntToBinary(Integer.parseInt(wholePart));
    return result;
  }
  
  private static String convertFracStringToBinary(String input){
    Double d = Double.parseDouble(input);
    String s = "";
    while(d>0){
      d *= 2;
      if(d>=1){
        s = "1" + s;
        d -= 1;
      }
      else {
        s = "0" + s;
      }
    }
    return s;
  }
  
  /**
   * QUESTION: CTCI 5.3 (page 58)
   * Given an integer, print the next smallest and next largest number that have the same 
   * number of 1 bits in their binary representation
   * 
   * This one is the next largest part.
   * @param a 
   * @return next largest number after a, satisfying above conditions.
   */
  private static int nextLargest(int a){
    int count = 0, countOnes = 0;
    int b = a;
    while((a&3)!=1 && a>0){
      if((a&1)==1) countOnes++;
      a>>=1;
      count++;
    }
    b >>= (count+2);
    b <<= 2;
    b |= 2;
    b <<= count;
    int mask = 0;
    while(countOnes>0){
      mask = mask << 1 | 1;
      countOnes--;
    }
    b |= mask;
    return b;
  }
  
  /**
   * QUESTION: CTCI 5.3 (page 58)
   * Given an integer, print the next smallest and next largest number that have the same 
   * number of 1 bits in their binary representation
   * 
   * This one is the next smallest part.
   * @param a next largest number
   * @return
   */
  private static int nextSmallest(int a){
    return ~nextLargest(~a);
  }

  /**
   * QUESTION: CTCI 5.5 (page 58)
   * 
   * Write a function to determine the number of bits required to convert integer A to 
   * integer B
   * Input: 31, 14
   * Output: 2
   * @param a integer A
   * @param b integer B
   * @return number of bits required
   */
  private static int numOfBitsToConvertOneIntToAnother(int a, int b){
    int c = a ^ b;
    int count = 0;
    while(c > 0){
      count += (c & 1);
      c >>= 1;
    }
    return count;
  }
  
  /**
   * QUESTION: CTCI 5.6 (page 58)
   * Write a program to swap odd and even bits in an integer with as few instructions as 
   * possible (e g , bit 0 and bit 1 are swapped, bit 2 and bit 3 are swapped, etc)
   * @param a
   * @return input integer with odd and even bits swapped
   */
  private static int exchangeOddEvenBits(int a){
    return ((a & 0xAAAAAAAA)>>1) | ((a & 0x55555555)<<1);
  }
  
  /**
   * QUESTION: CTCI 5.7 (page 58)
   * An array A[1..n] contains all the integers from 0 to n except for one number which is 
   * missing In this problem, we cannot access an entire integer in A with a single operation.
   * The elements of A are represented in binary, and the only operation we can use 
   * to access them is “fetch the jth bit of A[i]”, which takes constant time.
   * Write code to find the missing integer. Can you do it in O(n) time?
   * 
   * This is an iterative solution.
   * @param arr input array
   * @return element that's missing
   */
  private static int findMissingInteger(int[] arr){
    int[] holder = new int[arr.length];
    int lookFor = 0;
    int result = 0;
    for(int j=0;j<32;++j){
      int countOnes = 0;
      int countZeros = 0;
      boolean stop = true;
      
      for(int i=0;i<arr.length;++i){
        int x = fetchJthBitOfIthArrayElement(arr, i, j);
        if(holder[i] == lookFor){
          if(x==1) countOnes++;
          else countZeros++;
        }
        holder[i] = x;
        if(x == 1) stop = false; //if all the bits we ever found in position j were 0, it's time to stop
      }
      if(stop)
        break;
      
      if(countZeros<=countOnes)
        lookFor = 0;
      else
        lookFor = 1;
      
      result = result | (lookFor << j);
    }
    return result;
  }
  
  private static int fetchJthBitOfIthArrayElement(int[] arr, int i, int j){
    int k = 1;
    int jCopy = j;
    while(jCopy>0){
      k<<=1;
      jCopy--;
    }
    return (arr[i] & k)>>j;
  }
  
  public static void main(String[] args){
    int N = 40; //101000
    int M = 6;  //000110
    System.out.println(Integer.toBinaryString(setString(N, M, 1, 2)));
    
    System.out.println(convertIntToBinary(10));
    System.out.println(convertIntToBinary1(10));
    System.out.println(convertIntStringToBinary("10.1"));
    System.out.println(convertDecStringToBinary("10.5"));
    System.out.println(numOfBitsToConvertOneIntToAnother(31, 14));
    
    int b1 = 22;
    System.out.println(Integer.toBinaryString(b1)+" "+Integer.toBinaryString(nextLargest(b1)));
    System.out.println(Integer.toBinaryString(b1)+" "+Integer.toBinaryString(nextSmallest(b1)));
    b1 = 598;
    System.out.println(Integer.toBinaryString(b1)+" "+Integer.toBinaryString(exchangeOddEvenBits(b1)));
    
    int[] arr = {0,1,3};
    System.out.println(findMissingInteger(arr));
  }

}
