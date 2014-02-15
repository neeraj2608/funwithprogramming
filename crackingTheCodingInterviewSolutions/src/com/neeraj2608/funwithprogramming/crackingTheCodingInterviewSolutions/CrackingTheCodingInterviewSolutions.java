package com.neeraj2608.funwithprogramming.crackingTheCodingInterviewSolutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.neeraj2608.funwithprogramming.linkedlist.Data;
import com.neeraj2608.funwithprogramming.linkedlist.SinglyLinkedList;
import com.neeraj2608.funwithprogramming.linkedlist.Node;
import com.neeraj2608.funwithprogramming.minheap.MinHeap;

class Queue { //CTCI 3.5 52
  private Stack<Integer> ping, pong;
  
  public Queue(){
    ping = new Stack<Integer>();
    pong = new Stack<Integer>();
  }
  
  public void queue(Integer i){
    while(!ping.isEmpty())
      pong.push(ping.pop());
    pong.push(i);
    while(!pong.isEmpty())
      ping.push(pong.pop());
  }
  
  public Integer deQueue(){
    if(ping.isEmpty())
      throw new RuntimeException("queue is empty!");
    
    return ping.pop();
  }
}

class SetOfStacks{ //CTCI 3.3 52
  private int capacity;
  private List<StacksOnLL> listOfStacks;
  private StacksOnLL stacksOnLL;
  
  public SetOfStacks(int capacity){
    this.capacity = capacity;
    listOfStacks = new ArrayList<StacksOnLL>();
    stacksOnLL = new StacksOnLL(capacity);
    listOfStacks.add(stacksOnLL);
  }
  
  public void push(Object o){
    if(!stacksOnLL.isFull())
      stacksOnLL.push(o);
    else{
      stacksOnLL = new StacksOnLL(capacity);
      listOfStacks.add(stacksOnLL);
      stacksOnLL.push(o);
    }
  }
  
  public Object pop(){
    if(!stacksOnLL.isEmpty())
      return stacksOnLL.pop();
    else{
      listOfStacks.remove(listOfStacks.size()-1);
      if(listOfStacks.isEmpty())
        throw new RuntimeException("stack is empty!");
      stacksOnLL = listOfStacks.get(listOfStacks.size()-1);
      return pop();
    }
  }
  
  public Object popAt(int index){
    if(index > listOfStacks.size()-1)
      throw new RuntimeException("index too large!");
    StacksOnLL s = listOfStacks.get(index);
    return s.pop();
  }
}


class MinStacksOnLL extends StacksOnLL{ //CTCI 3.2 52
  private LL llMin;
  
  public MinStacksOnLL(int maxSize){
    super(maxSize);
    llMin = new LL();
  }
  
  @Override
  public void push(Object o){
    if(!(o instanceof Integer))
      throw new RuntimeException("this stack only takes integers");
    Integer oInt = (Integer) o;
    super.push(oInt);
    if(llMin.isEmpty())
      llMin.addInFront(oInt);
    else{
      Integer cmp = (Integer)llMin.remove();
      llMin.addInFront(cmp);
      if(cmp > oInt)
        llMin.addInFront(o);
    }
  }
  
  @Override
  public Object pop(){
    Integer o = (Integer) super.pop();
    Integer cmp = (Integer) llMin.remove();
    if(cmp != o)
      llMin.addInFront(cmp);
    return o;
  }
  
  public Object min(){
    if(size == 0)
      return Integer.MAX_VALUE;
    Object min = llMin.remove();
    llMin.addInFront(min);
    return min;
  }
}


class StacksOnLL{
  private LL ll;
  protected int size, maxSize;
  
  public StacksOnLL(int maxSize){
    this.maxSize = maxSize;
    this.size = 0;
    ll = new LL();
  }
  
  public void push(Object o){
    if(isFull())
      throw new RuntimeException("Stack is full!");
    
    size++;
    ll.addInFront(o);
  }
  
  public Object pop(){
    if(isEmpty())
      throw new RuntimeException("Stack is empty!");
    
    size--;
    return ll.remove();
  }
  
  public Object peek(){
    if(isEmpty())
      throw new RuntimeException("Stack is empty!");
    
    Object o = ll.remove();
    ll.addInFront(o);
    return o;
  }
  
  public boolean isEmpty(){
    return size == 0;
  }
  
  public boolean isFull(){
    return size >= maxSize;
  }
}

class StacksOnArray{ //CTCI 3.1 52
  private Object[] arr;
  private int size, maxSize, index;
  
  public StacksOnArray(Object[] arr, int maxSize, int start){
    this.arr = arr;
    this.maxSize = maxSize;
    this.index = start;
    this.size = 0;
  }
  
  public void push(Object o){
    if(isFull())
      throw new RuntimeException("stack is full");
    
    arr[index] = o;
    index--;
    size++;
  }
  
  public Object pop(){
    if(isEmpty())
      throw new RuntimeException("stack is empty");
    
    index++;
    size--;
    return arr[index];
  }
  
  public Object peek(){
    if(isEmpty())
      throw new RuntimeException("stack is empty");
    
    index++;
    Object o = arr[index];
    index--;
    return o;
  }
  
  public boolean isEmpty(){
    return size == 0;
  }
  
  public boolean isFull(){
    return size >= maxSize;
  }
}

class LL extends SinglyLinkedList{

  public void reverse(){
    if(isEmpty() || head.getNext()==null)
      return;
    
    Node previous = null;
    
    while(head!=null){
      Node temp = head.getNext();
      head.setNext(previous);
      previous = head;
      head = temp;
    }
    
    head = previous;
  }
  
  public void remDupsNoBuffer(){ //O(n^2) CTCI 2.1 50
    Node start = head;
    
    while(start != null){
      Node current = start.getNext();
      Node previous = start;
      while(current != null){
        if(start.getData() == current.getData())
          previous.setNext(current.getNext());
        previous = current;
        current = current.getNext();
      }
      start = start.getNext();
    }
  }
  
  public Object nthToLastElement(int n){ //CTCI 2.2 50
    if(isEmpty())
      throw new RuntimeException("list is empty!");
    if(n < 0)
      throw new RuntimeException("n cannot be less than 0");
    
    Node start = head;
    Node o = head;
    int count = -1;
    
    while(start!=null){
      start = start.getNext();
      if(count < n)
        count ++;
      else
        o = o.getNext();
    }
    
    if(count < n)
      throw new RuntimeException("list wasn't long enough");
    else
      return o.getData();
  }
  
  public void deleteFromMiddle(Data d){ //CTCI 2.3 50
    if(isEmpty())
      return;
    
    if(head.getData()==d){
      head = head.getNext();
      return;
    }
    
    Node start = head.getNext();
    Node previous = head;
    
    while(start!=null){
      if(start.getData()==d){
        previous.setNext(start.getNext());
        return;
      }
      previous = start;
      start = start.getNext();
    }
  }
  
  public Object startOfLoop(){ //CTCI 2.5 50
    Node slow = head;
    Node fast = head;
    
    while(true){
      slow = slow.getNext();
      fast = fast.getNext().getNext();
      
      if(null == fast)
        throw new RuntimeException("no loop in this list!");
      
      if(slow==fast)
        break;
    }
    
    slow = head;
    while(slow!=fast){
      slow = slow.getNext();
      fast = fast.getNext();
    }
    
    return slow.getData();
  }
  
  public void corruptThisList(int n){ //used to test CTCI 2.5 50. will create loopback from tail to (n-1)th element
    Node current = head;
    Node loopBackTo = head;
    int count = 0;
    while(current.getNext()!=null){
      if(count == n)
        loopBackTo = current;
      current = current.getNext();
      count++;
    }
    
    current.setNext(loopBackTo);
  }
   
}

class CrackingTheCodingInterviewSolutions
{
  private static MyComparator myComparator = new CrackingTheCodingInterviewSolutions.MyComparator();
  
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
  
  private static boolean findDups(String s){ //CTCI 1.1 48
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
  
  private static boolean findDups2(String s){ //CTCI 1.1 48
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
  
  private static String reverseString(String s){ //CTCI 1.2 48
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
  
  private static boolean checkIfAnagrams(String s1, String s2){ //CTCI 1.4 48
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
  
  private static String replaceSpaces(String s){ //CTCI 1.5 48
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
  
  private static void rotMatNoBuf(int[][] a){ //CTCI 1.6 48
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
  
  private static boolean isRotated(String rotS, String orgS){ //CTCI 1.8 48
    if(rotS.length() != orgS.length())
      return false;
    
    String concatS = rotS + rotS;
    if(concatS.matches(".*"+orgS+".*"))
      return true;
    else
      return false;
  }
  
  private static void setZeros(int[][] a){ // CTCI 1.7 48
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
  
  private static void sortStack(Stack<Integer> s){ //CTCI 3.5 52
    Stack<Integer> s1, s2, l, r;
    s1 = new Stack<Integer>();
    s2 = new Stack<Integer>();
    
    while(!s.isEmpty())
      s1.push(s.pop());
    
    while(!s1.isEmpty() || !s2.isEmpty()){
      if(s1.isEmpty()){
        l = s2;
        r = s1;
      } else {
        l = s1;
        r = s2;
      }
      
      Integer cmp = l.pop();
      while(!l.isEmpty()){
        Integer cmp2 = l.pop();
        if(cmp > cmp2)
          r.push(cmp2);
        else {
          r.push(cmp);
          cmp = cmp2;
        }
      }
      s.push(cmp);
    }
  }
  
  private static void sortStack2(Stack<Integer> s){ //CTCI 3.5 52 more efficient
    Stack<Integer> s1 = new Stack<Integer>();
    
    while(!s.isEmpty()){
      Integer a = s.pop();
      while(!s1.isEmpty() && s1.peek()>a){
        s.push(s1.pop());
      }
      s1.push(a);
    }
    
    while(!s1.isEmpty())
      s.push(s1.pop());
  }
  
  private static LL add2NumLLs(LL first, LL second){ // CTCI 2.4 50
    LL result = new LL();
    int carry = 0;
    
    while(!first.isEmpty() || !second.isEmpty()){
      int s = 0;
      if(!first.isEmpty())
        s += ((Data)first.remove()).getId();
      if(!second.isEmpty())
        s +=((Data)second.remove()).getId();
      s+=carry;
      Data d = new Data();
      d.setId(s%10);
      result.addInFront(d);
      carry = s/10;
    }
    
    if(carry>0){
      Data d = new Data();
      d.setId(carry);
      result.addInFront(d);
    }
    
    result.reverse();
    
    return result;
  }
  
  private static int setString(int N, int M, int i, int j){ //CTCI 5.1 58
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
  
  private static String convertDecStringToBinary(String s){ //CTCI 5.2 58
    String wholePart = s.substring(0,s.indexOf('.'));
    String decPart = s.substring(s.indexOf('.'));
    String result = convertIntToBinary(Integer.parseInt(wholePart))+"."+convertFracStringToBinary(decPart);
    if(result.length()>32)
      throw new RuntimeException("ERROR");
    return result;
  }
  
  private static int numOfBitsToConvertOneIntToAnother(int a, int b){ //CTCI 5.5 58
    int c = a ^ b;
    int count = 0;
    while(c > 0){
      count += (c & 1);
      c >>= 1;
    }
    return count;
  }
  
  private static int nextLargest(int a){ //CTCI 5.3 58
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
  
  private static int nextSmallest(int a){ //CTCI 5.3 58
    return ~nextLargest(~a);
  }
  
  private static int exchangeBits(int a){ //CTCI 5.6 58
    return ((a & 0xAAAAAAAA)>>1) | ((a & 0x55555555)<<1);
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
  
  private static void mergeSortedArrays(int[]a, int[]b){ //CTCI 9.1 66
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
  
  private static void mergeSortedFromBack(int[]a, int[]b){ //CTCI 9.1 66
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
  
  private static void mergeSortedFromBackBetter(int[]a, int[]b){ //CTCI 9.1 66
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
  

  
  private static void sortAnagramsLibrary(String[] arr){ //CTCI 9.2 66
    Arrays.sort(arr, myComparator);
  }
  
  private static void sortAnagramsBespoke(String[] arr){ //CTCI 9.2 66
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
  
  private static int searchRotArray(int[] arr, int elem){ //CTCI 9.3 66
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
  
  static int rotated_binary_search(int A[], int N, int key) { //CTCI 9.3 66 http://leetcode.com/2010/04/searching-element-in-rotated-array.html
    int L = 0;
    int R = N - 1;
   
    while (L <= R) {
      // Avoid overflow, same as M=(L+R)/2
      int M = L + ((R - L) / 2);
      if (A[M] == key) return M;
   
      // the bottom half is sorted
      if (A[L] <= A[M]) {
        if (A[L] <= key && key < A[M])
          R = M - 1;
        else
          L = M + 1;
      }
      // the upper half is sorted
      else {
        if (A[M] < key && key <= A[R])
          L = M + 1;
        else 
          R = M - 1;
      }
    }
    return -1;
  }
  
  private static int binSearch(String[] arr, String elem){ //CTCI 9.5 66
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
  
  private static String findInSortedMtx(int[][]arr, int elem){ //CTCI 9.6 66
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
    return reverseString(reverseString(s.substring(0,n))+reverseString(s.substring(n)));
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
    /********** LINKED LISTS **********/
    LL ll = new LL();
    Data d0 = new Data();
    d0.setId(0);
    ll.addInFront(d0);
    Data d1 = new Data();
    d1.setId(1);
    ll.addInFront(d1);
    Data d2 = new Data();
    d2.setId(2);
    ll.addInFront(d2);
    Data testData = new Data();
    testData.setId(9);
    ll.addInFront(testData);
    ll.addInFront(d0);
    ll.addInFront(d1);
    ll.addInFront(d2);
    
    ll.deleteFromMiddle(testData);
    
    System.out.println("2nd last element is "+((Data)ll.nthToLastElement(2)).getId());
    
    ll.remDupsNoBuffer();
    
    ll.reverse();
    
    while(!ll.isEmpty()){
      Data d = (Data) ll.remove();
      System.out.println(d.getId());
    }
    
    LL first = new LL();
    LL second = new LL();
    
    Data firstD1 = new Data();
    firstD1.setId(9);
    first.addInFront(firstD1);
    Data firstD2 = new Data();
    firstD2.setId(9);
    first.addInFront(firstD2);
    Data firstD3 = new Data();
    firstD3.setId(9);
    first.addInFront(firstD3);
    
    Data secondD1 = new Data();
    secondD1.setId(9);
    second.addInFront(secondD1);
    Data secondD2 = new Data();
    secondD2.setId(9);
    second.addInFront(secondD2);
    Data secondD3 = new Data();
    secondD3.setId(9);
    second.addInFront(secondD3);
    
    LL result = add2NumLLs(first, second);
    while(!result.isEmpty()){
      Data d = (Data) result.remove();
      System.out.println(d.getId());
    }
    
    LL ll2 = new LL();
    Data d = new Data();
    d.setId(6);
    ll2.addInFront(d);
    d = new Data();
    d.setId(5);
    ll2.addInFront(d);
    d = new Data();
    d.setId(4);
    ll2.addInFront(d);
    d = new Data();
    d.setId(3);
    ll2.addInFront(d);
    d = new Data();
    d.setId(2);
    ll2.addInFront(d);
    d = new Data();
    d.setId(1);
    ll2.addInFront(d);
    d = new Data();
    d.setId(0);
    ll2.addInFront(d);
    ll2.corruptThisList(2);
    
    System.out.println("loop starts at "+((Data)ll2.startOfLoop()).getId());
    
    /********** STACKS **********/
    Object[] arr = new Object[10];
    StacksOnArray s1 = new StacksOnArray(arr, 3, 2);
    StacksOnArray s2 = new StacksOnArray(arr, 3, 5);
    StacksOnArray s3 = new StacksOnArray(arr, 4, 9);
    StacksOnLL s4 = new StacksOnLL(3);
    MinStacksOnLL s5 = new MinStacksOnLL(10);
    
    for(int i=0;i<3;i++){
      s1.push(new Integer(i));
      s2.push(new Integer(2-i));
      s4.push(new Integer(i));
    }
    
    for(int i=0;i<2;i++){
      System.out.println("s1 peek "+s1.peek());
      System.out.println("s4 peek "+s4.peek());
    }
    
    for(int i=0;i<4;i++){
      s3.push(new Integer(i));
    }
    
    for(int i=0;i<3;i++){
      System.out.println("s1 "+s1.pop());
      System.out.println("s4 "+s4.pop());
      System.out.println("s2 "+s2.pop());
    }
    
    for(int i=0;i<4;i++){
      System.out.println("s3 "+s3.pop());
    }
    
    for(int i=0;i<10;i++){
      s5.push(new Integer(-i));
      System.out.println("s5 min "+s5.min());
    }
    
    SetOfStacks s6 = new SetOfStacks(3);
    
    for(int i=0;i<10;i++){
      s6.push(new Integer(i));
    }
    
    System.out.println("s6 "+s6.popAt(2));
    
    for(int i=0;i<9;i++){
      System.out.println("s6 "+s6.pop());
    }
    
    Queue q1 = new Queue();
    
    for(int i=0;i<10;i++){
      q1.queue(new Integer(i));
    }
    
    for(int i=0;i<10;i++){
      System.out.println("q1 "+q1.deQueue());
    }
    
    Stack<Integer> s = new Stack<Integer>();
    s.push(4);
    s.push(6);
    s.push(2);
    s.push(0);
    s.push(-10);
    //sortStack(s);
    sortStack2(s);
    
    while(!s.isEmpty())
      System.out.println(s.pop());
    
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
    
    int N = 40; //101000
    int M = 6;  //000110
    System.out.println(Integer.toBinaryString(setString(N, M, 1, 2)));
    
    swapNoBuf(N, M);
    swapNoBuf2(N, M);
    
    System.out.println(convertIntToBinary(10));
    System.out.println(convertIntToBinary1(10));
    System.out.println(convertIntStringToBinary("10.1"));
    System.out.println(convertDecStringToBinary("10.5"));
    System.out.println(numOfBitsToConvertOneIntToAnother(31, 14));
    
    int b1 = 22;
    System.out.println(Integer.toBinaryString(b1)+" "+Integer.toBinaryString(nextLargest(b1)));
    System.out.println(Integer.toBinaryString(b1)+" "+Integer.toBinaryString(nextSmallest(b1)));
    b1 = 598;
    System.out.println(Integer.toBinaryString(b1)+" "+Integer.toBinaryString(exchangeBits(b1)));
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
    int[] test2 = {0,4,9,11,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE};
    int[] test3 = {2,5,10,12};
    //mergeSortedArrays(test2, test3);
    //mergeSortedFromBack(test2, test3);
    mergeSortedFromBackBetter(test2, test3);
    String[] testStringArray = {"abc","dca","cad","bac","xyz"};
    sortAnagramsLibrary(testStringArray);
    sortAnagramsBespoke(testStringArray);
    
    int[] test4 = {15,2,5,10,12,13,14};
    System.out.println(searchRotArray(test4,15));
    
    String[] test5 = {"all","","","","ball","","","call","","doll",""};
    System.out.println(binSearch(test5,"boll"));
    
    int x1 = 7;
    System.out.println(x1 += ++x1);
    
    blah();
    
    int[][]a9 = new int[3][3];
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        a9[i][j] = i+j;
      }
    }
    
    System.out.println(findInSortedMtx(a9,3));
    
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
    
    HtWt[] arr2 = {new HtWt(65,100),new HtWt(70,150),new HtWt(56,90),new HtWt(75,190),new HtWt(60,110),new HtWt(68,110)};
    System.out.println(towerBuilder(arr2));

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
    
    MinHeap minHeap = new MinHeap(5);
    minHeap.insert(2);
    minHeap.insert(4);
    minHeap.insert(0);
    minHeap.insert(7);
    minHeap.insert(3);
    
    System.out.println("min heap contents:");
    while(!minHeap.isEmpty()){
      System.out.println(minHeap.deleteMinSiftUp());
    }
    
    minHeap.insert(2);
    minHeap.insert(4);
    minHeap.insert(0);
    minHeap.insert(7);
    minHeap.insert(3);
    
    System.out.println("min heap contents:");
    while(!minHeap.isEmpty()){
      System.out.println(minHeap.deleteMinSiftDown());
    }
    
    minHeap.insert(-2);
    minHeap.insert(40);
    minHeap.insert(-10);
    minHeap.insert(7);
    minHeap.insert(6);
    
    System.out.println("min heap contents:");
    while(!minHeap.isEmpty()){
      System.out.println(minHeap.deleteMinSiftUp());
    }
    
    minHeap.insert(-2);
    minHeap.insert(40);
    minHeap.insert(-10);
    minHeap.insert(7);
    minHeap.insert(6);
    
    System.out.println("min heap contents:");
    while(!minHeap.isEmpty()){
      System.out.println(minHeap.deleteMinSiftDown());
    }
    
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
