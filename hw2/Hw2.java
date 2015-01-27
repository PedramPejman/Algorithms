import java.util.*;
import java.io.*;


public class Hw2 {
  public static HashMap<String, Integer> companies = new HashMap<String, Integer>();
  public static int[] values = new int[3]; //[m,b,c]
  public static void main (String[] args) {
    if (args.length < 1) System.exit(0);
    File file = new File(args[0]);
    try (BufferedReader br = new BufferedReader(new FileReader(file))){
      int n  = Integer.parseInt(br.readLine());
      String line;
      String[] values_string = new String[3];
      //Do this n trials
      for (int i=0; i<n; i++) {
        //Read [m,b,c]
        values_string = (br.readLine()).split(" ");
        for (int j=0; j<3; j++) {
          values[j] = Integer.parseInt(values_string[j]);
        }
        for (int j=0; j<values[2]; j++) {
          values_string = (br.readLine()).split(" ");
          process(values_string[0], Integer.parseInt(values_string[1]), Integer.parseInt(values_string[2]));
        }
        returnResults(i);
      }
      
        
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void process (String name, int one, int half) {
    int remain = values[0];
    int cost = 0;
    while (remain > values[1]) {
      if (Math.ceil(remain/2) >= values[1]) {
        remain -= (int)Math.ceil(remain/2);
        cost += half;
      }
      else {
        remain--;
        cost += one;
      }
    }
    companies.put(name, cost);
  }

  public static void returnResults (int cycle) {
    //print header for section
    System.out.printf("Case %d\n", cycle+1);

    for (String key: companies.keySet()) {
      System.out.printf("%s : %d\n", key, companies.get(key));
    }
    companies = new HashMap<String, Integer>();
  }
}

class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
