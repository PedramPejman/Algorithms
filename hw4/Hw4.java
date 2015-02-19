import java.util.*;
import java.io.*;

public class Hw4 {
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
            minDist = process(points);
            points = new ArrayList<Point>();
            n = Integer.parseInt(br.readLine());
      }
    } catch (Exception e) {
      e.printStackTrace(); 
    }
  }


 public static double findMinDist(List<Point> points, int beg, int end) {
  double minR, minL, d;
  if ((end - beg)<4) {
    return minSquaredDist(points, beg, end);
  }
  minR = findMinDist(points, (beg+end)/2, end);
  minL = findMinDist(points, beg, (beg+end)/2);
  //System.out.printf("*******In findMinDist - Range: (%d, %d)************\n",beg,end);
  //System.out.printf("MinR: %f, MinL: %f\n",minR, minL );
  d = Math.min(minR, minL);
  return Math.min(d, findMidMin(points, beg, end, d));
 }
 public static double findMidMin(List<Point> points, int beg, int end, double d) { 
    int mid = (beg + end)/2;
    int start = mid;
    int finish = mid;
    while (d > distance(points.get(start) , points.get(mid))) start--;
    while (d > distance(points.get(finish) , points.get(mid))) finish++;
    for (int i=start; i<=finish; i++) {
      for (int j=i+1; j<=Math.min(7,finish); j++) {
        //System.out.printf("Min of (%d, %d) is between (%d:%s, %d:%s) = %f\n",start,finish,i,points.get(i),j,points.get(j),Math.min(d, distance(points.get(i), points.get(j))));
        d = Math.min(d, distance(points.get(i), points.get(j)));
      }
    }
    //System.out.printf("*******In findMidMin - Range: (%d, %d) - midMin: %f************\n",start,finish,d);
    return d;
 }
 public static double minSquaredDist(List<Point> points, int beg, int end) {
    double min = distance(points.get(beg),points.get(beg+1));
    for (int i=beg; i<(end-1); i++) {
      min = Math.min(min, distance(points.get(i), points.get(i+1)));
    }
    return min;
 }
 public static double distance (Point a, Point b) {
  return Math.sqrt(Math.pow(a.x-b.x,2) + Math.pow(a.y-b.y,2));
 }
 public static double process(List<Point> points) {
     Collections.sort(points);
     for (Point p:points) {
        //System.out.println(p);
     }

     //System.out.println("min distance: "+ findMinDist(points, 0, points.size()));
     cleanOutput(findMinDist(points,0,points.size()));
     sortX = false; 
     Collections.sort(points);
     for (Point p:points) {
        //System.out.println(p);
     }
  return 0.0;
 }
 public static void cleanOutput(double output) {
  if (output>1000.0) System.out.println("infinity");
  else System.out.printf("%.4f\n",output);
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
    return this.x + " " + this.y;
  }
  public int compareTo(Point p) {
    if (Hw4.sortX) {
      return (int)Math.floor(this.x - p.x);
    } else {
      return (int)Math.floor(this.y - p.y);
    }
  }
}
}
