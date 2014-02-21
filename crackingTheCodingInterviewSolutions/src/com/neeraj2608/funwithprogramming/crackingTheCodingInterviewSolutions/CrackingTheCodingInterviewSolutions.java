package com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
  
  private static int nthRecFibStartsWith0(int n){ //fib(1) = 0, fib(2) = 1
    if(n <= 1) return 0;
    if(n == 2) return 1;
    return nthRecFibStartsWith0(n-1) + nthRecFibStartsWith0(n-2);
  }
  
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
  
  private static int nthRecFibStartsWith1(int n){ //fib(1) = 1, fib(2) = 1
    return (n > 2) ? nthRecFibStartsWith1(n-1) + nthRecFibStartsWith1(n-2) : 1;
  }
  
  private static void permute(String s){ //CTCI 8.4 64
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
  
  private static void printBraces(int n){ // CTCI 8.5 64
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
  
  private static void generateSubs1(List<Integer> list){ // CTCI 8.3 64 (counting based)
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
  
  private static void fillPaint(int[][] a, int color, int x, int y){ //CTCI 8.6 64
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
  
  private static void Mc(int n){ //CTCI 8.7 64
    List<int[]> result = new ArrayList<int[]>();
    makeChange(result, new int[4], 25, n);
  }
  
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
  
    
  private static void quicksort(int[] arr){
    //choose pivot
    //move pivot to end of array so it doesn't interfere with the rest of the algorithm
    //we will assume for the moment that the pivot will rest here once this pass is done. whether this will actually be the pivot's
    //resting place depends on what happens below. 
    //starting from end-1 downto 0:
    //1. compare each element with the pivot's value (remember pivot is now at the end of the array!!)
    //2. if a given element is greater than or equal to the pivot, move it to just before the pivot. decrement the pivot's resting place
    //   by 1 since this is now the pivot's new prospective resting place. (to see why, note that the element we just moved into this
    //   location is greater than or equal to the pivot. this means that when we're done, we want it to lie to the RIGHT of the pivot. this
    //   can be accomplished by swapping this resting place element with the pivot element (which is currently at the end of the array).
    //   this means that the resting element will then end up on the pivot's right, which is what we want.
    //at the end of the loop above, we will have the pivot's final actual resting place. swap the pivot (which is at the end of the array)
    //with this resting place element.
    //recurse into the two halves of the array (apart from the pivot element).
    sort(arr,0,arr.length-1);
  }
  
  private static void sort(int[] arr, int start, int end){
    if(start>=end) return;
    
    int pivot = (start+end)>>1;
    swapInt(arr,pivot,end);
    int pivotrestingplace = end;
    
    for(int i=end-1;i>=0;--i){
      if(arr[i]>=arr[end]){
        swapInt(arr,i,--pivotrestingplace);
      }
    }
    swapInt(arr,pivotrestingplace,end);
    
    sort(arr,start,pivotrestingplace-1);
    sort(arr,pivotrestingplace+1,end);
  }
  
  private static void bubblesort(int[] arr){
    int n = arr.length;
    while(n>1){
      for(int i=1;i<n;++i){
        if(arr[i]<arr[i-1])
          swapInt(arr,i,i-1);
      }
      n--;
    }
  }
  
  private static void modifiedbubblesort(int[] arr){
    int n = arr.length;
    while(n>1){
      boolean flag = false;
      for(int i=1;i<n;++i){
        if(arr[i]<arr[i-1]){
          swapInt(arr,i,i-1);
          flag = true;
        }
      }
      if(!flag) break; //break early if no swaps
      n--;
    }
  }
  
  private static void insertionsort(int[] arr){
    for(int i=1;i<arr.length;++i){
      for(int j=i-1;j>=0;--j){
        if(arr[j]>arr[j+1])
          swapInt(arr,j,j+1);
      }
    }
  }
  
  private static void selectionsort(int[] arr){
    for(int i=0;i<arr.length-1;++i){
      int min = arr[i];
      int swapWith=i;
      for(int j=swapWith;j<arr.length;++j){
        if(arr[j]<min){
          min = arr[j];
          swapWith = j;
        }
      }
      swapInt(arr,i,swapWith);
    }
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

  private static void swapInt(int[] arr, int start, int end){
      int temp = arr[start];
      arr[start] = arr[end];
      arr[end] = temp;
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
    
    blah();
    
    int[] arr1 = {-5,2,-6,2,7,9,0};
    quicksort(arr1);
    bubblesort(arr1);
    insertionsort(arr1);
    selectionsort(arr1);
    
    int[] arr3 = {1,2,3,4,5};
    quicksort(arr3);
    arr3 = new int[]{1,2,3,4,5};
    bubblesort(arr3);
    arr3 = new int[]{1,2,3,4,5};
    modifiedbubblesort(arr3);
    arr3 = new int[]{1,2,3,4,5};
    insertionsort(arr3);
    arr3 = new int[]{1,2,3,4,5};
    selectionsort(arr3);
    arr3 = new int[]{1,2,3,4,5};
    mergesortinplace(arr3);
    arr3 = new int[]{1,2,3,4,5};
    arr3 = mergesortaux(arr3);
    
    arr3 = new int[]{5,4,3,2,1};
    quicksort(arr3);
    arr3 = new int[]{5,4,3,2,1};
    bubblesort(arr3);
    arr3 = new int[]{5,4,3,2,1};
    modifiedbubblesort(arr3);
    arr3 = new int[]{5,4,3,2,1};
    insertionsort(arr3);
    arr3 = new int[]{5,4,3,2,1};
    selectionsort(arr3);
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
