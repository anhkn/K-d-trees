import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class PointST<Value> {
    // Initialize Red-Black BST of Point2Ds
    private RedBlackBST<Point2D, Value> points;

    // construct an empty symbol table of points
    public PointST() {
        points = new RedBlackBST<>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return points.size() == 0;
    }

    // number of points
    public int size() {
        return points.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null)
            throw new IllegalArgumentException("Invalid argument.");
        points.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point.");
        return points.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point.");
        return points.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return points.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null rectangle.");
        Stack<Point2D> stack = new Stack<>(); // any Iterable
        for (Point2D i : points()) // iterate through all points
            if (rect.contains(i)) stack.push(i); // add to stack if point not in rect
        return stack;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point.");
        if (points.isEmpty()) return null;

        // Init nearest point to null and shortest distance to maximum value
        Point2D nearest = null;
        double shortestDistance = Double.POSITIVE_INFINITY;

        // Iterate through all points and find minimum value & closest point
        for (Point2D point : points()) {
            double squaredDist = p.distanceSquaredTo(point);
            if (squaredDist < shortestDistance) {
                shortestDistance = squaredDist;
                nearest = point;
            }
        }
        return nearest; // Return closest point
    }

    // unit testing (required)
    public static void main(String[] args) {
        Point2D a = new Point2D(0, 0);
        Point2D b = new Point2D(1, 0);
        Point2D c = new Point2D(0, 1);
        Point2D d = new Point2D(-1, 0);
        Point2D e = new Point2D(0, -1);
        PointST<Integer> pst = new PointST<>();
        StdOut.println("Should return true: (is empty PointST empty)? "
                               + pst.isEmpty());
        pst.put(a, 1);
        pst.put(b, 2);
        pst.put(c, 3);
        pst.put(d, 4);
        pst.put(e, 5);
        Point2D notInPST = new Point2D(4, 4);
        StdOut.println("Just added 1-5 into five points into PointST, "
                               + "test iteration: ");
        for (Point2D x : pst.points()) {
            StdOut.println(pst.get(x));
        }
        StdOut.println("Is filled PST empty (expected false): " + pst.isEmpty());
        StdOut.println("Size (should be five): " + pst.size());
        StdOut.println("Is point 'a' in PST, should be true: " + pst.contains(a));
        StdOut.println("Is point 'notInPST' in PST, should be false: " +
                               pst.contains(notInPST));
        RectHV rect = new RectHV(-1, -1, 1, 1);
        StdOut.println("All points in rectangle:" + pst.range(rect));
        StdOut.println("Nearest point to point 'notInPST' is: " +
                               pst.nearest(notInPST));

    }
}