Programming Assignment 5: K-d Trees

/* *****************************************************************************
 *  First, fill out the mid-semester survey:
 *  https://forms.gle/LdhX4bGvaBYYYXs97
 *
 *  If you're working with a partner, please do this separately.
 *
 *  Type your initials below to confirm that you've completed the survey.
 **************************************************************************** */
A.N.
N.B.



/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */
The instance variables for the Node were a Point2D, a value that the symbol table
uses to asssociate with the point, a RectHV rectangle that corresponds with the
node, a Node pointer to the left/bottom subtree, a Node pointer to the right/top
subtree, a boolean to determine if the current node is on a vertical or
horizontal level, and an int to keep track of the size of the tree.


/* *****************************************************************************
 *  Describe your method for range search in a k-d tree.
 **************************************************************************** */
 Our range search method creates a stack to store all the points within range,
 then calls a helper method, starting at the root, checking if the given
 rectangle interescts with the current node's rectangle, adding the point to the
 stack if it does, then recursively checks the left/bottom subtree then the
 right/top subtree.


/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */
Our nearest neighbor search method calls a helper method,
that calculates the distance between the query point and points in the kd tree,
starting at the root. If a shorter distance is found, the champion point is
updated. Then, recursively search the left/bottom and right/top subtrees.

/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, rounding each value to use one digit after
 *  the decimal point. Use at least 1 second of CPU time. Do not use -Xint.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */


                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST:             50           /     4.3          =   11.6

KdTreeST:         300000            /    1.4       =  214,285.7

Note: more calls per second indicates better performance.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */


/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
All partner protocols were followed.
Partner 1 helped implement the KdTreeSt and part of the PointSt class
Partner 2 assisted Partner 1 on all methods, including unit testing.



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on  how helpful the class meeting was and on how much you learned
 * from doing the assignment, and whether you enjoyed doing it.
 **********************************\****************************************** */
