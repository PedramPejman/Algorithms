import java.util.*;
import java.io.*;


public class Hw2 {
  public static Map<String, Integer> companies = new HashMap<String, Integer>();
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
        //Read [m,b,c] - first we read it as a String[3] then parse it to a int[3]
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
    //Do while remain > m
    while (remain > values[1]) {
      //If we can halve the boxes, do it
      if ((Math.floor(remain/2) >= values[1])&&(half <= Math.ceil(remain/2.0)*one)) {
        remain = (int)Math.floor(remain/2);
        cost += half;
      }
      //If not, decrease by one
      else{
        remain --;
        cost += one;
      }
    }
    companies.put(name, cost);
  }

  public static void returnResults (int cycle) {
    //print header for section
    System.out.printf("Case %d\n", cycle+1);
    //Sort the map and print it
    ValueComparator vc = new ValueComparator(companies);
    TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(vc);
    sorted.putAll(companies);
    for (String key: sorted.keySet()) {
      System.out.printf("%s %d\n", key,  companies.get(key));
    }
    //Empty the static map for next round
    companies = new HashMap<String, Integer>();
  }
}

//For fun (sorta). Used to construct a sorted Hashmap from an unordered one. 
//It's a bit unorthodox but I had fun designing it so I consider it a win :)
class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;
    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) > base.get(b)) {
            return 1;
        } else if (base.get(a) < base.get(b)){
            return -1;
        } // returning 0 would merge keys
          else {
          return (a.compareTo(b));
        }
    }
}
