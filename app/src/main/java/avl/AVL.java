package avl;

public class AVL {
    public Node root;
    private int size;
    public int getSize() {
        return size;
    }

    /**
     * find w in the tree. return the node containing w or null if not found
     */
    public Node search(String w) {
        return search(root, w);
    }
    private Node search(Node n, String w) {
        if (n == null) {
            return null;
        }
        if (w.equals(n.word)) {
            return n;
        } else if (w.compareTo(n.word) < 0) {
            return search(n.left, w);
        } else {
            return search(n.right, w);
        }
    }

    /**
     * insert w into the tree as a standard BST, ignoring balance
     */
    public void bstInsert(String w) {
        if (root == null) {
            root = new Node(w);
            size = 1;
            return;
        }
        bstInsert(root, w);
    }

    /* insert w into the tree rooted at n, ignoring balance
     * pre: n is not null */

    private void bstInsert(Node n, String w) {
        // if w is before n.word
        if(w.compareTo(n.word) < 0) {
            if(n.left == null) {
                n.left = new Node(w, n);
                size++;
            } else {
                bstInsert(n.left, w);
            }
        }
        // if w is after n.word
        else if(w.compareTo(n.word) > 0) {
            if(n.right == null) {
                n.right = new Node(w, n);
                size++;
            } else {
                bstInsert(n.right, w);
            }
        }
        n.height = Math.max(height(n.left) , height(n.right)) + 1;
    }

    /**
     * insert w into the tree, maintaining AVL balance precondition: the tree is AVL balanced and any prior insertions
     * have been performed by this method.
     */
    public void avlInsert(String w) {
        // TODO
        if (root == null) {
            root = new Node(w);
            size = 1;
            return;
        }
        avlInsert(root, w);
    }

    /* insert w into the tree, maintaining AVL balance
     *  precondition: the tree is AVL balanced and n is not null */
    private void avlInsert(Node n, String w) {
        // if w is before n.word
        if(w.compareTo(n.word) < 0) {
            if(n.left == null) {
                n.left = new Node(w, n);
                size++;
            } else {
                avlInsert(n.left, w);
            }
        }
        // if w is after n.word
        else if(w.compareTo(n.word) > 0) {
            if(n.right == null) {
                n.right = new Node(w, n);
                size++;

            } else {
                avlInsert(n.right, w);
            }
        }
        n.height = Math.max(height(n.left) , height(n.right)) + 1;
        rebalance(n);

    }

    /**
     * do a left rotation: rotate on the edge from x to its right child. precondition: x has a non-null right child
     */
    public void leftRotate(Node x) {
        // guard
        if(x.right == null) {
            return;
        }
        Node xRight = x.right;
        Node rlst = xRight.left;
        // if rotating at root of BST
        if(x.parent == null) {
            root = xRight;
        }
        // if x.parent is not null
        else {
            // change parent pointers
            // checks which direction parent is pointing to
            if(x.parent.left == x) {
                x.parent.left = xRight;
            } else if (x.parent.right == x){
                x.parent.right = xRight;
            }
        }
        // change xLeft pointers
        xRight.parent = x.parent;
        xRight.left = x;
        x.parent = xRight;
        x.right = rlst;
        // change rightLeftSubTree pointers
        if(rlst != null){
            rlst.parent = x;
        }


        x.height = Math.max(height(x.left) , height(x.right)) + 1;
        xRight.height = Math.max(height(xRight.left) , height(xRight.right)) + 1;
    }

    /**
     * do a right rotation: rotate on the edge from x to its left child. precondition: y has a non-null left child
     */
    public void rightRotate(Node y) {

        if(y.left == null) {
            return;
        }
        Node yLeft = y.left;

        Node lrst = y.left.right;
        //if y is the root of AVL
        if(y.parent == null) {
            root = yLeft;
        }
        //if y is not root of AVL
        else {
            if (y.parent.left == y) {
                y.parent.left = yLeft;
            }
            else if(y.parent.right == y) {
                y.parent.right = yLeft;
            }
        }
        // change yRight pointers
        yLeft.parent = y.parent;
        yLeft.right = y;
        y.parent = yLeft;
        y.left = lrst;
        //change lrst pointers
        if(lrst != null) {
            lrst.parent = y;
        }


        y.height = Math.max(height(y.left) , height(y.right)) + 1;
        yLeft.height = Math.max(height(yLeft.left) , height(yLeft.right)) + 1;
    }

    /**
     * rebalance a node N after a potentially AVL-violoting insertion. precondition: none of n's descendants violates
     * the AVL property
     */
    public void rebalance(Node n) {
        int bal = balance(n);
        if(bal < -1) {
            if (balance(n.left) <= 0) {
                rightRotate(n);
            } else {
                leftRotate(n.left);
                rightRotate(n);
            }
        } else if(bal > 1) {
            if (balance(n.right) <= 0) {
                rightRotate(n.right);
                leftRotate(n);
            } else {
                leftRotate(n);
            }
        }
    }


    /**
     * remove the word w from the tree
     */
    public void remove(String w) {
        remove(root, w);
    }

    /* remove w from the tree rooted at n */
    private void remove(Node n, String w) {
        // (enhancement TODO - do the base assignment first)
    }

    /**
     * print a sideways representation of the tree - root at left, right is up, left is down.
     */
    public void printTree() {
        printSubtree(root, 0);
    }

    private void printSubtree(Node n, int level) {
        if (n == null) {
            return;
        }
        printSubtree(n.right, level + 1);
        for (int i = 0; i < level; i++) {
            System.out.print("        ");
        }
        System.out.print(n.height);
        System.out.println(n);
        printSubtree(n.left, level + 1);
    }

    // specification: returns the height of Node n
    private int height(Node n) {
        if (n == null) {
            return -1;
        } else {
            return n.height;
        }
    }

    // specification: returns the balance of Node n
    private int balance(Node n) {
        return height(n.right) - height(n.left);
    }

    /**
     * inner class representing a node in the tree.
     */
    public class Node {
        public String word;
        public Node parent;
        public Node left;
        public Node right;
        public int height;

        /**
         * constructor: gives default values to all fields
         */
        public Node() {
        }

        /**
         * constructor: sets only word
         */
        public Node(String w) {
            word = w;
        }

        /**
         * constructor: sets word and parent fields
         */
        public Node(String w, Node p) {
            word = w;
            parent = p;
        }

        /**
         * constructor: sets all fields
         */
        public Node(String w, Node p, Node l, Node r) {
            word = w;
            parent = p;
            left = l;
            right = r;
        }
        public String toString() {
            return word + "(" + height + ")";
        }
    }
}
