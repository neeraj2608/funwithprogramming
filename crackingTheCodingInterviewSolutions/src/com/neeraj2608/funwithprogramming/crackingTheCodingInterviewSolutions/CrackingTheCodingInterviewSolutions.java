package com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions;

import com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions.chapter1stringsarrays.CrackingTheCodingInterviewSolutionsChapter1;
import com.neeraj2608.funwithprogramming.minheap.MinHeap;

class CrackingTheCodingInterviewSolutions
{
  private static void swapNoBuf(int a, int b){ //CTCI 19.2 89
    System.out.println("a = "+a+", b = "+b);
    a = a ^ b;
    b = a ^ b;
    a = a ^ b;
    System.out.println("a = "+a+", b = "+b);
  }
  
  private static void swapNoBuf2(int a, int b){ //CTCI 19.2 89
    System.out.println("a = "+a+", b = "+b);
    a = a - b;
    b = b + a;
    a = b - a;
    System.out.println("a = "+a+", b = "+b);
  }
  
  private static void mergesortinplace(int[] arr){
    //in the first pass, we start with arrays of 1 and merge.
    //since we're merging in place, the merge is done by rotating the array. this needs auxiliary
    //space of O(1) (one variable, to be exact).
    //here's how the rotation looks:
    //5 3 1 2
    //Pass 1: we're merging arrays of length 1
    //5       3
    //start   j   stop
    //(compare arr[start] with arr[j]. 5 is larger than 3, so need to rotate (j-start) = once.)
    //3       5
    //        start j
    //              stop
    //j has become >= stop so stop 
    //1       2
    //start   j   stop
    //(compare arr[start] with arr[j]. 1 is smaller than 2, increment start.)
    //1       2
    //        start  j
    //               stop
    //j has become >= stop so stop 
    //Pass 2: we merge arrays of length 2
    //3       5   1      2
    //start       j         stop
    //(compare arr[start] with arr[j]. 3 is larger than 1, so need to rotate (j-start) = twice.)
    //5       1      2      3
    //1       2      3      5
    //        start  j         stop
    //(compare arr[start] with arr[j]. 2 is smaller than 3, increment start. Since start == j, increment j.)
    //1       2      3      5
    //               start  j  stop
    //(compare arr[start] with arr[j]. 3 is smaller than 5, increment start. Since start == j, increment j.)
    //1       2      3      5
    //                      start  j
    //                             stop
    //j has become >= stop so stop 
    //Pass 3: we merge arrays of length 4
    //j would be larger than the array, so we can stop.
    int length = 1; 
    while(true){
      for(int i=0;i<arr.length-length;i=i+(length<<1)){
        int start = i;
        int j = start + length;
        if(j>=arr.length) break;
        int stop = Math.min(start + (length<<1),arr.length);
        while(start<stop){
          if(arr[start]>arr[j]){
            rotate(arr,start,stop-1,j-start);
          }
          start++;
          if(start==j) j++;
          if(j==stop) break;
        }
      }
      length<<=1;
      if(length>=arr.length) break;
    }
  }
  
  private static int[] mergesortaux(int[] arr){
    return m(arr,0,arr.length-1);
  }
  
  private static int[] m(int[] arr, int start, int end){
    if(start>=end){
      int[] result = new int[1];
      result[0] = arr[start];
      return result;
    }
    
    int half = (start+end)>>1;
    return merge(m(arr,start,half),m(arr,half+1,end));
  }

  private static int[] merge(int[] a, int[] b){
    int[] result = new int[a.length+b.length];
    int i=0,j=0,k=0;
    while(k<result.length){
      if(i==a.length)
        result[k++] = b[j++];
      else if(j==b.length)
        result[k++] = a[i++];
      else{
        if(a[i]<b[j])
          result[k++] = a[i++];
        else
          result[k++] = b[j++];
      }
    }
    return result;
  }

  private static void rotate(int[] arr, int start, int stop, int times){
    while(times>0){
      for(int i=start;i<stop;++i){
        int temp = arr[i];
        arr[i] = arr[i+1];
        arr[i+1] = temp;
      }
      times--;
    }
  }
  
  private static void heapsort(int[] arr){
    MinHeap minHeap = new MinHeap(arr.length);
    for(int i: arr){
      minHeap.insert(i);
    }
    for(int i=0;i<arr.length;++i)
      arr[i] = minHeap.deleteMinSiftDown();
  }

  private static void blah(){
    int x = 5;
    int y = 10;
    int z = ++x * y--;
    System.out.println(z);
    System.out.println("1 + 2 = " + 1 + 2);
    x = 5;
    System.out.println(x++ - --x);
    int a = 9;
    a =+ (a = 3);
    System.out.println(a);
  }
  
  private static String rotString1(String s, int n){
    //divide the string into two sub-strings. the starting character of the second substring should be
    //the character that you want to end up at the start after the rotation.
    //reverse both the sub strings, put them together and reverse the whole string
    return CrackingTheCodingInterviewSolutionsChapter1.reverseString(CrackingTheCodingInterviewSolutionsChapter1.reverseString(s.substring(0,n))+CrackingTheCodingInterviewSolutionsChapter1.reverseString(s.substring(n)));
  }
  
  private static String rotString2(String s, int n){
    //put each character into its final place thus:
    //abcde - if we want c to be at the start of the string after the rotation, note that
    //        a (which is currently at the beginning of the string) will end up one place
    //        to the right of the current position of c. the current position of c is
    //        what is being indicated by 'n'. Hence,
    //        a ends up at (pos(a)+n+1)%stringlength,
    //        b ends up at (pos(b)+n+1)%stringlength,
    //        etc
    char[] c = new char[s.length()];
    for(int i=0;i<c.length;++i){
      c[(i+n+1)%s.length()] = s.charAt(i);
    }
    return new String(c);
  }
  
  static class A{
    public int a = 2;
  }
  
  private static void test1(A a){
    a.a = 5;
  }
  
  private static void test2(A a){
    a = new A();
    a.a = 5;
  }
  
  private static int[] test3(int[] a){
    int[] result = new int[a.length];
    return result;
  }
  
  private static void test4(int[] a){
    a = test5(a);
  }

  private static int[] test5(int[] a){
    return new int[a.length];
  }

  public static void main (String[] args) throws java.lang.Exception
  {
    int N = 40; //101000
    int M = 6;  //000110
    swapNoBuf(N, M);
    swapNoBuf2(N, M);
    
    blah();
    
    int[] arr3 = new int[]{1,2,3,4,5};
    mergesortinplace(arr3);
    arr3 = new int[]{1,2,3,4,5};
    arr3 = mergesortaux(arr3);
    
    arr3 = new int[]{5,4,3,2,1};
    arr3 = mergesortaux(arr3);
    arr3 = new int[]{5,4,3,2,1};
    heapsort(arr3);
    arr3 = new int[]{5,3,-1,0,-2};
    mergesortinplace(arr3);
    arr3 = new int[]{5,3,-1,0,-2};
    heapsort(arr3);
    
    A a3 = new A();
    test1(a3);
    System.out.println(a3.a); // will print 5 (change in test1() reflected)
    a3 = new A();
    test2(a3);
    System.out.println(a3.a); // will print 2 (change in test2() not reflected)
    int[] a4 = new int[]{1,2,3};
    a4 = test3(a4); //a4 will be changed to {0,0,0}
    a4 = new int[]{1,2,3};
    test4(a4); //a4 will return to its original value of {1,2,3} when test4 exits. JUST BEFORE test4 exits, it was {0,0,0}
    
    System.out.println(rotString1("abcdefg",3));
    System.out.println(rotString2("abcdefg",3));
    
    //System.out.println(rotated_binary_search(test4,7,14));
    /*ArrayList<ArrayList<String>> x9  = new ArrayList<ArrayList<String>>();
    ArrayList<List<String>>      x10 = new ArrayList<ArrayList<String>>();
    ArrayList<List<String>>      x2  = new ArrayList<List<String>>();
    List<List<String>>           x11 = new ArrayList<ArrayList<String>>();
    List<List<String>>           x1  = new ArrayList<List<String>>();
    List<ArrayList<String>>      x8  = new ArrayList<ArrayList<String>>();*/
  }
  
}
