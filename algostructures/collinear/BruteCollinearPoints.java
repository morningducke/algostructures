import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int n = 0;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        //check for nulls
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
        Arrays.sort(points);

        //check for duplicates
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();
        }

        segments = new LineSegment[1];

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        if (Double.compare(slope1, slope2) == 0) {
                            double slope3 = points[i].slopeTo(points[l]);
                            if (Double.compare(slope1, slope3) == 0) {
                                LineSegment ls = new LineSegment(points[i], points[l]);
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
    }        // the number of line segments

    public LineSegment[] segments() {
        return segments;
    }                // the line segments

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}