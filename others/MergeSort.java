import java.util.*;

public class MergeSort {

  public static void main (String[] args) {
    int[] list = {1, 5, 4, 7, 8, 5, 4, 8, 1, 5, 4, 7, 8, 5, 4, 8};
    System.out.println(Arrays.toString(list));
    System.out.println(Arrays.toString(mergeSort(list)));
    int[] list2 = {1, 6, 4, 3, 8, 5, 4, 8};
    //merge(list2, 4, 6, 2);
  }

  public static int[] mergeSort (int[] list) {
    int[] temp = new int[list.length];
    for (int length=1; Math.pow(2,length/2)<=list.length; length*=2) {
      for (int start=0; start<list.length - length; start+=2*length) {
        //System.out.printf("Pass: %d \t start: %d \n", length, start);
        temp = merge(list, start, start+length, length);
        for (int i=start; i<start+2*length; i++) {
          list[i] = temp[i-start];
        }
        //System.out.println(Arrays.toString(list));
      }
    }
    return list;
  }
  

  public static int[] merge(int[] list, int start1, int start2, int length) {
    int[] temp = new int[length*2];
    int i = start1;
    int j = start2;
    int k = 0 ;
    while ((i<(start1+length))&&(j<(start2+length))) {
      if (list[i] < list[j]) temp[k++] = list[i++];
      else temp[k++] = list[j++];
    }
    while (i < (start1+length)) {
      temp[k++] = list[i++];
    }

    while (j < (start2+length)) {
      temp[k++] = list[j++];
    }

    //System.out.println(Arrays.toString(temp));
    return temp;
  }

  public static void merge(int[] list, int start1, int start2) {
    System.out.println(Arrays.toString(list));
    print(list.length, start1, start2);
  } 

  public static void print(int length, int i, int j) {
    System.out.print("[");
    int k;
    for (k=0; k<length-1; k++) {
      if (k == i) System.out.print("^  ");
      else if (k == j) System.out.print("*  ");
      else System.out.print("   ");
    }
    if (k == i) System.out.print("^");
    else if (k == j) System.out.print("*");
    else System.out.print(" ");
    System.out.println("]");
  }
}
