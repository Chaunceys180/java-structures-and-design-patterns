package structures.stacks;

import exceptions.StackUnderflowException;
import java.util.*;

/**
 * This stack class creates a stack of objects and follows the FILO rule
 * @param <T> is a generic type for different data types
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class Stack<T> implements IStack<T>, Iterable<T>
{
    private T[] stack; //this is the generic array
    private int modCount; //this will keep track of how many changes have been made

    /**
     * This function adds a new element to the top of the stack. The stack continues to grow for new elements.
     * @param element is the new element to be added
     */
    @Override
    public void push(T element) {

        //if the stack is empty, initialize and add element
        if(isEmpty()) {
            stack = (T[]) new Object[size()+1];
            stack[0] = element;
        } else {
            //else, make the stack bigger
            T[] biggerList = (T[]) new Object[size()+1];

            //copy the elements over
            for(int i = 0; i < size(); i++) {
                biggerList[i] = stack[i];
            }

            //add to the stack
            biggerList[size()] = element;
            stack = biggerList;
        }
        modCount++; //record the change
    }

    /**
     * This method adds several elements to the top of the stack.
     * @param elements is an array of elements
     */
    @Override
    public void pushAll(T[] elements) {
        //for every element to be added, add them to the stack
        for(T item : elements) {
            push(item);
            modCount++; //record the change
        }
    }

    /**
     * This method removes and returns the top element of the stack,
     * throwing appropriate exceptions if the stack is empty.
     * @return returns the top element on the stack
     */
    @Override
    public T pop() {

        //if the stack is empty, throw an exception
        if(isEmpty()) {
            throw new StackUnderflowException();
        } else {
            //else, remove the item at the top of the stack
            T topElement = stack[size()-1];
            stack[size()-1] = null;

            modCount++; //record the change
            return topElement;
        }
    }

    /**
     * The popAll() method removes and returns all elements from the stack
     * @return returns a List<T> object of which is a copy of the stack
     */
    @Override
    public List<T> popAll() {

        //if the stack is empty, throw an exception
        if(isEmpty()) {
            throw new StackUnderflowException();
        } else {
            //else, record the stack in a List<T> object
            List<T> popList = new ArrayList<>();
            for(int i = 0; i <= size()-1; i++) {
                popList.add(stack[i]);
            }

            //then, clear the stack and return the popped items
            clear();
            modCount++; //record the change
            return popList;
        }
    }

    /**
     * This method counts the elements in the stack
     * @return returns the total number of elements in the stack
     */
    @Override
    public int size() {
        //create a counter
        int count = 0;

        //if it's not empty, count
        if(!isEmpty()) {
            for(T element : stack) { //for every element count
                if(element != null) {
                    count++; //count is only incremented if there is a value
                }
            }
            return count;
        }
        return count;
    }

    /**
     * This method checks if the stack is empty
     * @return true or false if it is or isn't empty
     */
    @Override
    public boolean isEmpty() {
        return stack == null; //if stack == null return true, it's empty
    }

    /**
     * The iterator() method returns a working iterator over the elements of the stack.
     * @return returns an Iterator object to make the stack for in foreach loops
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator(stack, modCount); //returns an Iterator for the stack
    }

    /**
     * This method clears the stack
     */
    @Override
    public void clear() {
        //reset the stack
        stack = null;
        modCount++; //record the change
    }

    /**
     * This method returns a String of the stack
     * @return returns a String of the stack
     */
    @Override
    public String toString() {
        return "Stack{" +
                "stack=" + Arrays.toString(stack) +
                ", modCount=" + modCount +
                '}';
    }

    /**
     * This class will make the stack iterable
     * @author Chauncey Brown-Castro
     * @version 1.0
     */
    private class ListIterator implements Iterator<T> {

        private T[] data;
        private int index  = 0;
        private int comparableModCount;

        private ListIterator(T[] data, int modCount) {
            this.data = data;
            this.comparableModCount = modCount;
        }

        /**
         * This method tells the iterator class if the object is iterable
         * @return returns a true or false based on if there's another memory location at the top of the stack
         */
        @Override
        public boolean hasNext() {
            //if there's been any changes while iterating, throw an exception
            if(comparableModCount != modCount) {
                throw new ConcurrentModificationException("Changes were made while Iterating");
            }
            return index < Stack.this.size(); //return true if there's a next index
        }

        /**
         * This method returns the cerrent data and increments to the next element
         * @return returns the element in the current iterable index
         */
        @Override
        public T next() {
            //if there's been any changes while iterating, throw an exception
            if(comparableModCount != modCount) {
                throw new ConcurrentModificationException("Changes were made while Iterating");
            }
            T currentData = data[index]; //get the current element
            index++; //iterate to next
            return currentData;
        }

        /**
         * This method returns a String of the stack
         * @return returns a String of the stack and iterator elements
         */
        @Override
        public String toString() {
            return "ListIterator{" +
                    "data=" + Arrays.toString(data) +
                    ", index=" + index +
                    ", comparableModCount=" + comparableModCount +
                    '}';
        }
    }
}