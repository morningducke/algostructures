import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Set;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private Point2D point;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree

        public Node(Point2D p) {
            this.point = p;
        }
    }
    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) {
            root = new Node(p);
            root.rect = new RectHV(0, 0, 1, 1);
            size = 1;
        } else {
            root = insert(root, p, false);
        }
    }

    //flag: false if we compare by x coordinate, true if by y coordinate
    private Node insert(Node node, Point2D p, boolean flag) {
        if (node == null) return null;

        int cmp = 0;
        if (!flag) {
            cmp = Double.compare(p.x(), node.point.x());
        } else {
            cmp = Double.compare(p.y(), node.point.y());
        }

        if (cmp < 0) {

            if (node.left == null) {
                node.left = new Node(p);
                if (!flag) {
                    node.left.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax());
                } else {
                    node.left.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y());
                }
                size++;
                return node;
            } else {

                node.left = insert(node.left, p, !flag);
            }
        }
        else {
            if (node.right == null) {
                node.right = new Node(p);
                if (!flag) {
                    node.right.rect = new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(),
                                                 node.rect.ymax());
                }
                else {
                    node.right.rect = new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(),
                                                 node.rect.ymax());
                }
                size++;
                return node;
            } else {
                node.right = insert(node.right, p, !flag);
            }
        }

        return node;

    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(root , p, false) != null;
    }

    private Node get(Node node, Point2D p, boolean flag) {
        if (node == null) return null;

        int cmp = 0;
        if (!flag) {
            cmp = Double.compare(p.x(), node.point.x());
        } else {
            cmp = Double.compare(p.y(), node.point.y());
        }

        if (cmp < 0) return get(node.left, p, !flag);
        else if (cmp > 0) return get(node.right, p, !flag);
        else  {

            if (flag) {
                cmp = Double.compare(p.x(), node.point.x());
            } else {
                cmp = Double.compare(p.y(), node.point.y());
            }
            if (cmp == 0) return node;
            else return get(node.right, p, !flag);
        }

    }
    // draw all points to standard draw
    public void draw()  {
        draw(root, false);
    }

    private void draw(Node node, boolean flag) {
        if(node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();


        StdDraw.setPenRadius();
        if (!flag) {
            StdDraw.setPenColor(StdDraw.RED);
            RectHV line = new RectHV(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            line.draw();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            RectHV line = new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
            line.draw();
        }

        draw(node.left, !flag);
        draw(node.right, !flag);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        SET<Point2D> inside_points = new SET<>();


        return range(root, rect, inside_points);
    }

    private SET<Point2D> range(Node node, RectHV rect, SET<Point2D> inside_points) {
        if (node == null) { return inside_points; }

        // if(!rect.intersects(node.rect)) {
        //     return inside_points;
        // }

        if (rect.contains(node.point)) {
            inside_points.add(node.point);
        }

        if (node.left != null && rect.intersects(node.left.rect)) {
            inside_points = range(node.left, rect, inside_points);
        }

        if (node.right != null && rect.intersects(node.right.rect)) {
            inside_points = range(node.right, rect, inside_points);
        }

        return inside_points;
    }



    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;

        return nearest(root, root, p, p.distanceSquaredTo(root.point)).point;
    }

    private Node nearest(Node node, Node min, Point2D p, double min_dist) {
        if (node == null) return min;

        double dist = p.distanceSquaredTo(node.point);
        if (dist < min_dist) {
            min_dist = dist;
            min = node;
        }

        if (node.left != null && node.left.rect.distanceSquaredTo(p) < min_dist) {
            min = nearest(node.left, min, p, min_dist);
        }

        min_dist = min.point.distanceSquaredTo(p);
        if (node.right != null && node.right.rect.distanceSquaredTo(p) < min_dist) {
            min = nearest(node.right, min, p, min_dist);
        }

        return min;

    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();

        ArrayList<Point2D> points = new ArrayList<>();

        points.add(new Point2D(0.1, 0.2));
        points.add(new Point2D(0.05, 0.3));
        points.add(new Point2D(0.5, 0.1));
        points.add(new Point2D(0.25, 0.3));
        points.add(new Point2D(0.54, 0.1));

        for (Point2D p : points) {
            tree.insert(p);
        }

        System.out.println("True points: ");
        for (Point2D p : points) {
            System.out.println(p.toString() + " " + tree.contains(p));
        }
        // System.out.println("False points: ");
        // System.out.println(tree.contains(new Point2D(0, 0)));
        // System.out.println(tree.contains(new Point2D(1, 1)));
        // System.out.println(tree.contains(new Point2D(0, 0)));
        // System.out.println(tree.contains(new Point2D(0.5, 0.5)));
        // System.out.println(tree.contains(new Point2D(0.1, 0.4)));

        System.out.println(tree.size);
        tree.draw();






    }

}
