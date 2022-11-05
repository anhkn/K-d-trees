import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeST<Value> {
    private Node root; // root node

    private class Node {
        private Point2D p;     // the point
        private Value val;     // the symbol table maps the point to this value
        private RectHV rect;   // the axis-aligned rectangle corresponding to this node
        private Node lb;       // the left/bottom subtree
        private Node rt;       // the right/top subtree
        private boolean vertical; // determines if the node is vertical or not
        private int size;      // amount of points

        // constructs a node
        public Node(Point2D p, Value val, RectHV rect,
                    Node lb, Node rt, boolean vertical, int size) {
            this.p = p;
            this.val = val;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.vertical = vertical;
            this.size = size;
        }
    }

    // construct an empty symbol table of points
    public KdTreeST() {

    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points
    public int size() {
        return size(root);
    }

    // returns size of node
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null)
            throw new IllegalArgumentException("Invalid argument.");
        RectHV rect = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                                 Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        root = put(root, p, val, rect, true);
    }

    // helper method
    private Node put(Node x, Point2D p, Value val, RectHV rect, boolean vertical) {
        if (x == null) return new Node(p, val, rect, null, null, vertical, 1);
        if (vertical) {
            if (p.x() < x.p.x()) {
                RectHV rectangle = new RectHV(x.rect.xmin(), x.rect.ymin(),
                                              x.p.x(), x.rect.ymax());
                x.lb = put(x.lb, p, val, rectangle, !vertical);
            }
            else if (p.x() > x.p.x()) {
                RectHV rectangle = new RectHV(x.p.x(), x.rect.ymin(),
                                              x.rect.xmax(), x.rect.ymax());
                x.rt = put(x.rt, p, val, rectangle, !vertical);
            }
            else if (p.equals(x.p)) x.val = val;
            else {
                RectHV rectangle = new RectHV(x.p.x(), x.rect.ymin(),
                                              x.rect.xmax(), x.rect.ymax());
                x.rt = put(x.rt, p, val, rectangle, !vertical);
            }
        }
        else {
            if (p.y() < x.p.y()) {
                RectHV rectangle = new RectHV(x.rect.xmin(), x.rect.ymin(),
                                              x.rect.xmax(), x.p.y());
                x.lb = put(x.lb, p, val, rectangle, !vertical);
            }
            else if (p.y() > x.p.y()) {
                RectHV rectangle = new RectHV(x.rect.xmin(), x.p.y(),
                                              x.rect.xmax(), x.rect.ymax());
                x.rt = put(x.rt, p, val, rectangle, !vertical);
            }
            else if (p.equals(x.p)) x.val = val;
            else {
                RectHV rectangle = new RectHV(x.rect.xmin(), x.p.y(),
                                              x.rect.xmax(), x.rect.ymax());
                x.rt = put(x.rt, p, val, rectangle, !vertical);
            }
        }
        x.size = 1 + size(x.lb) + size(x.rt);
        return x;
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point.");
        return get(root, p, true);

    }


    // helper method
    private Value get(Node x, Point2D p, boolean vertical) {
        if (x == null) return null;
        if (vertical) {
            if (p.x() < x.p.x()) return get(x.lb, p, !vertical);
            else if (p.x() > x.p.x()) return get(x.rt, p, !vertical);
            else if (p.equals(x.p)) return x.val;
            else return get(x.rt, p, !vertical);
        }
        else {
            if (p.y() < x.p.y()) return get(x.lb, p, !vertical);
            else if (p.y() > x.p.y()) return get(x.rt, p, !vertical);
            else if (p.equals(x.p)) return x.val;
            else return get(x.rt, p, !vertical);
        }
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point.");
        return get(p) != null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        Queue<Node> queue = new Queue<Node>();
        Queue<Point2D> keys = new Queue<Point2D>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.p);
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
        return keys;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null rectangle.");
        Stack<Point2D> stack = new Stack<>();
        return range(root, rect, stack);
    }

    // helper method
    private Iterable<Point2D> range(Node x, RectHV rect, Stack<Point2D> stack) {
        if (x == null) return null;
        if (!x.rect.intersects(rect)) return stack;
        if (rect.contains(x.p)) stack.push(x.p);
        if (x.lb != null) range(x.lb, rect, stack);
        if (x.rt != null) range(x.rt, rect, stack);
        return stack;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point.");
        if (isEmpty()) return null;
        return nearest(root, p, root.p);
    }

    // helper method
    private Point2D nearest(Node x, Point2D p, Point2D min) {
        if (x == null) return min;
        double distSquared = min.distanceSquaredTo(p);
        if (distSquared < x.rect.distanceSquaredTo(p)) return min;
        if (x.p.distanceSquaredTo(p) < distSquared) min = x.p;
        if (x.rt != null && (x.vertical && (p.x() > x.p.x())) ||
                !x.vertical && (p.y() > x.p.y())) {
            min = nearest(x.rt, p, min);
            min = nearest(x.lb, p, min);
        }
        else {
            min = nearest(x.lb, p, min);
            min = nearest(x.rt, p, min);
        }
        return min;
    }

    // unit testing (required)
    public static void main(String[] args) {
/*        // read me timing tests
        In in = new In("input1M.txt");

        // initialize the two data structures with point from input
        PointST<Integer> brute = new PointST<Integer>();
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
            brute.put(p, i);
        }


        int m = 0;
        StopwatchCPU st = new StopwatchCPU();
        while (m < 300000) {
            Point2D query = new Point2D(StdRandom.uniform(0.0, 1.0),
                                        StdRandom.uniform(0.0, 1.0));
            kdtree.nearest(query);
            m++;
        }
        StdOut.println(st.elapsedTime());*/

        KdTreeST<Integer> kd = new KdTreeST<>();
        Point2D a = new Point2D(1, 1);
        Point2D b = new Point2D(2, 2);
        Point2D c = new Point2D(0, 0);
        RectHV rectHV = new RectHV(0, 0, 1, 1);
        // add points
        kd.put(a, 1);
        kd.put(b, 2);

        StdOut.println("Contains a?: " + kd.contains(a));
        StdOut.println("Value of point a: " + kd.get(a));


        StdOut.println("All points: " + kd.points());
        StdOut.println("All points in rect: " + kd.range(rectHV));
        StdOut.println("Nearest point: " + kd.nearest(c));

        StdOut.println("Size: " + kd.size()); // check size : 2
        StdOut.println("Empty?: " + kd.isEmpty()); // check if empty : false

    }

}