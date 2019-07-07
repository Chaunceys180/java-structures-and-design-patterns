package structures.queues;

import exceptions.EmptyQueueException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class creates a queue from a doubly linked list of node objects.
 *
 * @author  Chauncey Brown-Castro
 * @version 1.0
 * @param <T> is a generic type of data
 */
public class TwoWayQueue<T> implements ITwoWayQueue<T> {

    //This variable is for the queue
    private DoubleLinkedList<T> queue = new DoubleLinkedList<>();
    private int modCount; //this is for tracking changes

    @Override
    public T dequeueFirst() {
        //Check exception
        emptyQueueCheck();

        //get the first element, remove it, and return it.
        T data = queue.removeFirst();

        modCount++; //keep track of changes
        return data;
    }

    @Override
    public T dequeueLast() {
        //Check exception
        emptyQueueCheck();

        //get the last element, remove it, and return it.
        T data = queue.removeLast();

        modCount++; //keep track of changes
        return data;
    }

    @Override
    public List<T> dequeueAll() {
        //Check exception
        emptyQueueCheck();

        List<T> list = new LinkedList<>(); //creating a List object to copy all queue information

        while(!isEmpty()){ //while the queue isn't empty, add to the list from the queue
            list.add(queue.removeLast());
        }

        //update the modCount, clear the queue, return the list
        modCount++;
        clear();
        return list;
    }

    @Override
    public void enqueueFirst(T element) {
        //add to the front of the queue, then update count
        queue.addToFront(element);
        modCount++;
    }

    @Override
    public void enqueueLast(T element) {
        //add to the back of the queue, then update count
        queue.addToLast(element);
        modCount++;
    }

    @Override
    public void enqueueAllFirst(T[] elements) {
        for(T element : elements) { //for every item, add to the front of the queue, then update count
            enqueueFirst(element);
            modCount++;
        }
    }

    @Override
    public void enqueueAllLast(T[] elements) {
        for(T element : elements) { //for every item, add to the back of the queue, then update count
            queue.addToLast(element);
            modCount++;
        }
    }

    @Override
    public int size() {
        //start a counter
        int size = 0;
        Node<T> index = queue.getHead();

        //while the queue isn't empty, increment size, move to the next item
        while(index.getData() != null) {
            size++;
            index = index.getNext();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0; //if size = 0 (true) else (false)
    }

    @Override
    public void clear() {
        queue = new DoubleLinkedList<>(); //set queue to be new, update modCount
        modCount++;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator<>(queue, modCount) ; //return my queue iterator object
    }

    /**
     * This method throws an EmptyQueueException if the queue of the
     * DoubleLinkedList nodes are empty.
     * @throws EmptyQueueException is the Exception thrown if the queue is empty
     */
    public void emptyQueueCheck() {
        if(isEmpty()) { //if empty
            throw new EmptyQueueException("The queue was empty");
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node<T> index = queue.getHead();
        while(index.getNext() != null){
            index = index.getNext();
            result.append(index.getData()).append(", ");
        }
        return "List: " + result;
    }

    /*---------------------------------------------LINKED LIST CLASS-------------------------------------------------*/

    private class DoubleLinkedList<T> {

        //This is the first node in the linked list
        private Node<T> head = new Node<>(null);
        //This will be the last node in the list
        private Node<T> tail = new Node<>(null);

        private DoubleLinkedList() {
            //link head and tail together by default
            head.setNext(tail);
            tail.setPrev(head);
        }

        private void addToFront(T data) {
            if(isEmpty()) {
                head.setData(data);
            } else {
                //create the new head
                Node<T> newHead = new Node<>(data);

                //link it to the previous head
                newHead.setNext(head);

                //link the previous head to the new head
                head.setPrev(newHead);

                //set head to be the new head
                head = newHead;
            }
        }

        private void addToLast(T data) {
            if(isEmpty()) {
                head.setData(data);
            } else {
                //Make a new node
                Node<T> newNode = new Node<>(data);

                Node<T> lastItem = tail.getPrev();

                //add to the last node
                lastItem.setNext(newNode);
                newNode.setPrev(lastItem);

                //add update the new last item to point to tail
                newNode.setNext(tail);

                //update tail
                tail.setPrev(newNode);
            }
        }

        private T removeLast() {
            if(size() == 1) {
                //no need to update references
                T data = head.getData();
                head.setData(null);
                return data;
            } else {
                //get the last item. This would be previous to tail
                Node<T> lastItem = tail.getPrev();
                T data = lastItem.getData();

                //remove the item
                lastItem.setData(null);

                //update tail, and return the removed item
                tail = lastItem;
                return data;
            }

        }

        private T removeFirst() {
            if(size() == 1) {
                //no need to update references
                T data = head.getData();
                head.setData(null);
                return data;
            } else {
                //get the second item, to be the new head. This should be the next node
                T data = head.getData();
                Node<T> newHead = head.getNext();

                //newHead should have no previous node, since it's at the top
                newHead.setPrev(null);

                //reset head, and return data
                head = newHead;
                return data;
            }

        }

        private Node<T> getHead() {
            return head;
        }

        private Node<T> getTail() {
            return tail;
        }

        @Override
        public String toString() {
            //getting the head values
            String headValue = (head.getData() == null) ? "null" : head.getData().toString();
            String headNextValue = (head.getNext().getData() == null) ? "null" : head.getNext().getData().toString();

            //getting tail values
            String tailValue = (tail.getData() == null) ? "null" : tail.getData().toString();
            String tailPrevValue = (tail.getPrev().getData() == null) ? "null" : tail.getPrev().getData().toString();

            //returning my string
            return "DoubleLinkedList{ " +
                    "head= [ null <-- " + headValue + " --> " + headNextValue +
                    "], tail= [ " + tailPrevValue + " <-- " + tailValue + " --> null ] }";
        }
    }

    /*--------------------------------------------------NODE CLASS---------------------------------------------------*/

    private class Node<T> {

        private T data;
        private Node<T> next;
        private Node<T> prev;

        private Node(T data) {
            this.prev = null;
            this.data = data;
            this.next = null;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public T getData() {
            return data;
        }

        public String toString() {
            //ternary statements to check for null values
            String dataValue = (data == null) ? "null" : data.toString();
            String nextValue = (next.data == null) ? "null" : next.data.toString();
            String prevValue = (prev.data == null) ? "null" : prev.data.toString();
            return prevValue + "<-- " + dataValue + " --> " + nextValue;
        }
    }

    /*----------------------------------------------ITERATOR CLASS---------------------------------------------------*/

    private class QueueIterator<T> implements Iterator<T> {

        private int index = 0;
        private Node<T> tail;
        private int comparableModCount;

        private QueueIterator(DoubleLinkedList<T> queue, int modCount) {
            //get the tail, and modCount
            this.tail = queue.getTail();
            this.comparableModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            checkMod(); //check for exception
            return index < TwoWayQueue.this.size(); //true if there's a next iteration
        }

        @Override
        public T next() {
            checkMod(); //check for exception

            //work backwards
            T element = tail.getPrev().getData();
            tail = tail.getPrev();

            index++; //increment index to make sure if we go over the size
            return element;
        }

        private void checkMod() {
            //throw ConcurrentModificationException if there's been a change
            if(comparableModCount != modCount) {
                throw new ConcurrentModificationException("Changes were made while Iterating");
            }
        }

        @Override
        public String toString() {
            return "QueueIterator{" +
                    "index=" + index +
                    ", tail=" + tail +
                    ", comparableModCount=" + comparableModCount +
                    '}';
        }
    }
}
