import java.util.*;
import java.io.*;

public class Hw3 {
  public static int[] byX, byY;
  public static boolean sortX = true;
  public static void main (String[] args) {
    List<Point> points = new ArrayList<Point>();
    File file = new File(args[0]);
    String temp;
    double minDist;
    String[] tempArr = new String[2];
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      int n = Integer.parseInt(br.readLine());
      while (n != 0) {
            for (int i=0; i<n; i++) {
              temp = br.readLine();
              tempArr = temp.split(" ");
              points.add(new Point(tempArr[0], tempArr[1]));
            }
            //System.out.println("****************");
            process(points);
            points = new ArrayList<Point>();
            n = Integer.parseInt(br.readLine());
      }
    } catch (Exception e) {
      e.printStackTrace(); 
    }
  }

 public static Point[] merge(Point[] a, Point[] b) {
    Point[] temp = new Point[a.length + b.length];
    int i=0; int j=0; int k=0;
    while((a[i] != null)&&(b[j] != null)) {
      temp[k++] = (a[i].y < b[j].y) ? a[i++] : b[j++];
    }
    while (a[i] != null) temp[k++] = a[i++];
    while (b[j] != null) temp[k++] = b[j++];
    return temp;
 }
 public static double findMinDist(List<Point> points, Point[] yVals, int beg, int end) {
  double minR, minL, d;
  if ((end - beg)<4) {
    return minSquaredDist(points, yVals, beg, end);
  }
  Point[] rArr = new Point[end-beg];
  minR = findMinDist(points, rArr, (beg+end)/2, end);
  Point[] lArr = new Point[end-beg];
  minL = findMinDist(points, lArr, beg, (beg+end)/2);
  yVals = merge(rArr, lArr);
  d = Math.min(minR, minL);
  return Math.min(d, findMidMin(points, yVals, beg, end, d));
 }

 public static double findMidMin(List<Point> points, Point[] yVals, int beg, int end, double d) { 
    List<Point> temp = new ArrayList<Point>();
    int mid = (beg + end)/2;
    int start = mid;
    int finish = mid;
    while ((start > 0) && (d > Math.abs(points.get(mid).x - points.get(start).x))) start--; 
    while ((finish < points.size()-1) && (d > Math.abs(points.get(mid).x - points.get(finish).x))) finish++;
    for (Point p : yVals) {
      if (p != null) {
      if (Math.abs(points.get(mid).x - p.x) < d) temp.add(p);}
    }
    for (int i=0; i<=temp.size(); i++) {
      for (int j=i+1; j<=Math.min(i+7, temp.size() -1); j++) {
        d = Math.min(d, distance(temp.get(i), temp.get(j)));
      }
    }
    return d;
 }

 public static double minSquaredDist(List<Point> points, Point[] yVals, int beg, int end) {
    double min = distance(points.get(beg), points.get(beg+1));
    if (end - beg == 3) {
      min = Math.min(min, distance(points.get(beg), points.get(beg+2)));
      min = Math.min(min, distance(points.get(beg+1), points.get(beg+2)));
    }
    for (int i=beg; i<end; i++) {
      yVals[i-beg] = points.get(i);
    }
    Arrays.sort(yVals, new Comp());
    return min;
 }

 public static double distance (Point a, Point b) {
  return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
 }

 public static void process(List<Point> points) {
     sortX = true;
     Collections.sort(points);
     cleanOutput(findMinDist(points,new Point[points.size()],0,points.size()));
 }
 
 public static void cleanOutput(double output) {
  if (output>1000.0) System.out.println("infinity");
  else System.out.printf("%.4f\n",output);
 }

static class Comp implements Comparator<Point> {
  public int compare(Point a, Point b) {
    if (a == null && b == null) {
      return 0;
    }
    if (a == null) return 1;
    if (b == null) return -1;
    return a.compareTo(b);
  }
}
static class Point implements Comparable<Point>{
  public double x,y;
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
  public Point(String x, String y) {
    this.x = Double.parseDouble(x);
    this.y = Double.parseDouble(y);
  }
  public String toString() {
    return "(" + this.x + " " + this.y + ")";
  }
  public int compareTo(Point p) {
    if (Hw3.sortX) {
      return (int)Math.floor(this.x - p.x);
    } else {
      return (int)Math.floor(this.y - p.y);
    }
  }
}
}
