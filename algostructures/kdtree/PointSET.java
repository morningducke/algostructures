import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw()  {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> inside_points = new SET<>();
        for (Point2D p : points) {
            if (rect.contains(p)) inside_points.add(p);
        }
        return inside_points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if(points.isEmpty()) return null;
        double min = Double.POSITIVE_INFINITY;
        Point2D min_point = points.max();
        for(Point2D point_in_set : points) {
            double distance = p.distanceSquaredTo(point_in_set);
            if (distance < min) {
               min = distance;
               min_point = point_in_set;
            }
        }
        return min_point;
    }

    public static void main(String[] args) {

    }
}