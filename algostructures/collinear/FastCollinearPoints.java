import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private Point[] points;
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        if (points == null) throw new IllegalArgumentException();
        this.points = new Point[points.length];
        //check for nulls
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            this.points[i] = points[i];
        }

        Arrays.sort(points);
        //check for duplicates
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();
        }
        segments = new LineSegment[1];
        Point[] slopeArr = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            slopeArr[i] = points[i];
        }
        int n = 0;
        for(int i = 0; i < points.length; i++) {
            Arrays.sort(slopeArr, slopeOrderBreak(points[i]));
            for (int j = 0; j < slopeArr.length-1; j++) {
                int count = 0;
                boolean flag = false;
                if(points[i].compareTo(slopeArr[j]) > 0) flag = true;
                while (points[i].slopeTo(slopeArr[j]) == points[i].slopeTo(slopeArr[j + 1])) {
                    j++;
                    count++;
                    if(j == slopeArr.length - 1) break;
                }
                if (count >= 2 && !flag) {
                    LineSegment ls = new LineSegment(points[i], slopeArr[j]);
                    segments[n] = ls;
                    n++;
                    if (n == segments.length) {
                        LineSegment[] copy = new LineSegment[n * 2];
                        for (int c = 0; c < n; c++) {
                            copy[c] = segments[c];
                        }
                        segments = copy;
                    }
                }
            }
        }
        LineSegment[] copy = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            copy[i] = segments[i];
        }
        segments = copy;




    }
    public int numberOfSegments() {
        return segments.length;
    }       // the number of line segments
    public LineSegment[] segments() { return segments; }          // the line segments

    private Comparator<Point> slopeOrderBreak(Point cur) {
        return new BySlope(cur);
    }

    private class BySlope implements Comparator<Point> {
        private Point cur;
        public BySlope(Point cur) {
            this.cur = cur;
        }
        public int compare(Point p1, Point p2) {
            int cmp = Double.compare(cur.slopeTo(p1), cur.slopeTo(p2));
            if(cmp != 0) return cmp;
            else return p1.compareTo(p2);
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}