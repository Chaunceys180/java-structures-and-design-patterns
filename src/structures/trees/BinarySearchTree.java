package structures.trees;

import java.util.*;

/**
 * This is my BinarySearchTree
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T>{

    //fields
    private Node root;
    private int size;

    /**
     * This method adds an element into the binary tree
     * @param element the element to be added into the tree
     */
    public void add(T element) {
        //empty tree?
        if(root == null) {
            root = new Node(element);
            size++;
        } else {
            //recursively find the position of our new element
            root = add(element, root);
        }
    }

    private Node add(T element, Node current) {
        //if we have a null current node, then we found an open spot
        if(current == null) {
            size++;
            return new Node(element);
        }

        //are we looking to the left and right
        int compare = current.data.compareTo(element);

        if(compare < 0) { //current is smaller
            current.right = add(element, current.right);
        } else if (compare > 0) { //current is bigger
            current.left = add(element, current.left);
        }
        return current;
    }

    /**
     * Takes an element and looks for it in the tree
     * @param element the element in question to be searched for
     * @return true || false if found or not
     */
    public boolean contains(T element) {
        return contains(element, root);
    }

    private boolean contains(T element, Node current) {
        //base case?
        if(current == null) {
            return false; //we never found the element
        }

        int compare = current.data.compareTo(element);

        if(compare < 0) { //the current is smaller
            return contains(element, current.right);
        } else if(compare > 0) { //the current is larger
            return contains(element, current.left);
        } else { //the current is equal
            return true; //element found
        }
    }

    /**
     * removes an element in the tree
     * @param element is the element to be removed in the structure
     * @return returns true if the element is found and removed, otherwise returns false
     */
    public boolean remove(T element) {
        root = remove(element, root);
        return root != null;
    }

    private Node remove(T element, Node current) {
        //base case?
        if(current == null) {
            return null; // element was not found
        }

        int compare = current.data.compareTo(element);

        if(compare < 0) { //current is smaller
            current.right = remove(element, current.right);
        } else if(compare > 0) { //current is larger
            current.left = remove(element, current.left);
        } else {
            //current is equal

            //check for two children
            if(current.left != null && current.right != null) {
                //replace the data at our current node
                Node maxLeft = findMax(current.left);
                current.data = maxLeft.data;

                //recursively remove the largest element in the left subtree
                current.left = remove(maxLeft.data, current.left);

            } else if(current.left != null) { //one child
                current = current.left;
                size--;
            } else if(current.right != null) { //one child
                current = current.right;
                size--;
            } else { //no children
                current = null;
                size--;
            }

        }
        return current;
    }

    private Node findMax(Node current) {
        if(current.right != null) {
            return findMax(current.right);
        }
        return current;
    }

    /**
     * @return returns the size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * This method tells you if the tree is empty or not
     * @return true || false if structure is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * This method whipes the tree clean
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * This method prints out all the elements in the tree in order
     */
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node current) {
        if(current != null) {
            inOrder(current.left); //left
            System.out.println(current.data);
            inOrder(current.right); //right
        }
    }

    /**
     * This method prints out all the elements in the tree in post-order
     */
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node current) {
        if(current != null) {
            postOrder(current.left); //left
            postOrder(current.right); //right
            System.out.println(current.data);
        }
    }

    /**
     * This method prints out all the elements in the tree in pre-order
     */
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node current) {
        if(current != null) {
            System.out.println(current.data);
            preOrder(current.left); //left
            preOrder(current.right); //right
        }
    }

    /**
     * generates an array list of the elements in the structure
     * @return a list version of the tree
     */
    public List<T> toList() {
        List<T> results = new ArrayList<>();
        toList(root, results);
        return results;
    }

    private void toList(Node current, List<T> results) {
        if(current != null) {
            inOrder(current.left); //left
            results.add(current.data);
            inOrder(current.right); //right
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BSTIterator(root);
    }

    private class BSTIterator implements Iterator<T> {

        private Stack<Node> nodeStack = new Stack<>();

        public BSTIterator(Node current) {
            //move to the first node
            while(current != null) {
                nodeStack.push(current);
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }

        @Override
        public T next() {
            //step#1: retrieve the next element to report
            Node next = nodeStack.pop();

            //step#2: if there is a right sub-tree, find the smallest element
            //adding nodes to the stack as we go
            if(next.right != null) {
                //add the right child
                nodeStack.push(next.right);

                //and dive to the left of our right child
                Node current = next.right;
                while(current.left != null) {
                    nodeStack.push(current.left);
                    current = current.left;
                }
            }
            return next.data;
        }

        @Override
        public String toString() {
            return "BSTIterator{" +
                    "nodeStack=" + nodeStack +
                    '}';
        }
    }

    private class NaiveIterator implements Iterator<T> {

        private Object[] data;
        private int position;

        public NaiveIterator(BinarySearchTree owner) {
            data = owner.toList().toArray();
        }

        @Override
        public boolean hasNext() {
            return position < data.length;
        }

        @Override
        public T next() {
            T item = (T) data[position];
            position++;
            return item;
        }

        @Override
        public String toString() {
            return "NaiveIterator{" +
                    "data=" + Arrays.toString(data) +
                    ", position=" + position +
                    '}';
        }
    }


    //binary tree node
    private class Node {
        //fields
        private T data;
        private Node left;
        private Node right;

        public Node(T data) {
            this.data = data;
        }

        public String toString() {
            String dataString = (data == null) ? "null" : data.toString();
            String leftChild = (left == null) ? "null" : left.data.toString();
            String rightChild = (right == null) ? "null" : right.data.toString();

            return leftChild + " <-- " + dataString + " --> " + rightChild;
        }
    }

    @Override
    public String toString() {
        return "BinarySearchTree{" +
                "root=" + root +
                ", size=" + size +
                '}';
    }
}