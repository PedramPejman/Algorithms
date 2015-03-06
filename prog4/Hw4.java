import java.util.*;
import java.io.*;


public class Hw4 {
public static void main (String[] args) {
    if (args.length < 1) System.exit(0);
    File file = new File(args[0]);
    try (BufferedReader br = new BufferedReader(new FileReader(file))){
      int n  = Integer.parseInt(br.readLine());
      String line, name;
      String[] values_string = new String[3];
      int rows, cols;
      //Do this n trials
      for (int count=0; count<n; count++) {
        //Read [name, r, c] - first we read it as a String[3] then parse it to a int[3]
        values_string = (br.readLine()).split(" ");
        name = values_string[0];
        rows = Integer.parseInt(values_string[1]);
        cols = Integer.parseInt(values_string[2]);
        String[] s_row_values = new String[cols];
        Item[] M = new Item[rows * cols]; 
        for (int row_num=0; row_num<rows; row_num++) {
          s_row_values = (br.readLine()).split(" ");
          for (int col_num=0; col_num<cols; col_num++) {
            Item item = new Item(row_num, col_num, Integer.parseInt(s_row_values[col_num]));
            M[row_num * cols + col_num] = item;
          }
        }
        System.out.println(name + ": "  + process(name, M, rows, cols));
      } 
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static int process (String name, Item[] M, int rows, int cols) {
    Item[] sorted = new Item[rows*cols];
    System.arraycopy(M, 0, sorted, 0, M.length);
    Arrays.sort(sorted);
    int max = 0;
    int i, j;
    ArrayList<Item> neighbors;
    for (int k=0; k<M.length; k++) {
      int temp_max=1;
      i = sorted[k].i;
      j = sorted[k].j;
      neighbors = neighbors(M, i, j, rows, cols);
      for (Item neighbor : neighbors) {
        temp_max  = Math.max(temp_max, neighbor.length);
      }
      M[i*rows + j].length = temp_max + 1;
      max = Math.max(temp_max, max);
      //System.out.println("(" + i + ", " + j + "): " + M[i*rows + j] + " - " + neighbors + "  - max: " +M[i*rows +j].length);
    }
    return max;
  }
  public static ArrayList<Item> neighbors(Item[] M, int i, int j, int rows, int cols) {
    ArrayList<Item> ns = new ArrayList<Item>();
    Item  m1, m2, m3, m4;
    m1 = null; m2 = null; m3 = null; m4 = null;
    if (((i-1) < rows) && ((i-1) > -1) && (j < cols) && (j > -1)) {
      if (M[(i-1)*rows + j].value < M[i*rows + j].value) m1 = M[(i-1)*rows + j];
    }
    if (((i+1) < rows) && ((i+1) > -1) && (j < cols) && (j > -1)) {
      if (M[(i+1)*rows + j].value < M[i*rows + j].value) m2 = M[(i+1)*rows + j];
    }    
    if ((i < rows) && (i > -1) && ((j-1) < cols) && ((j-1) > -1)) {
      if (M[i*rows + (j-1)].value < M[i*rows + j].value) m3 = M[i*rows + (j-1)];
    }    
    if ((i < rows) && (i > -1) && ((j+1) < cols) && ((j+1) > -1)) {
      if (M[i*rows + (j+1)].value < M[i*rows + j].value) m4 = M[i*rows + (j+1)];
    }    
    
    if (m1 != null) ns.add(m1);
    if (m2 != null) ns.add(m2);
    if (m3 != null) ns.add(m3);
    if (m4 != null) ns.add(m4);
    return ns;
    }

}
  class Item implements Comparable<Item>{
    int i,j,value,length;
    public Item(int i, int j, int value) {
      this.i = i;
      this.j = j;
      this.value = value;
      this.length = 1;
    }
    public String toString() {
      return "" + this.value;
    }
    public int compareTo (Item item) {
      if (item.value == this.value) return 0;
      return (item.value > this.value) ? -1 : 1;
    }
  }

