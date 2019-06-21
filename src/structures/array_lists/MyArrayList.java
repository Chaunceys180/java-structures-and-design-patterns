package structures.array_lists;

import java.util.*;

/**
 * @param <E> is the generic element to be stored
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class MyArrayList<E> implements List<E>, Comparable<E>, Iterable<E>{

    private int size;
    private E[] structure;

    private static final int INITIAL_SIZE = 100;
    private static final double RESIZE_RATE = 0.75;

    public MyArrayList() {
        structure = (E[]) new Object[INITIAL_SIZE];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object object) {
        E element = castElement(object);

        for (E item : structure) {
            if (item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public E[] toArray() {
        return getNoNullCopy();
    }

    @Override
    public boolean add(Object object) {
        //check to see if there is room
        if(needsResize()) {
            resize();
        }

        //check for a null value, otherwise anything else is allowed
        if(object == null) {
            throw new NullPointerException("Cannot add null values into the list");
        } else {
            structure[size] = castElement(object);
            size++;
            return true;
        }
    }

    private boolean needsResize() {
        return (size / structure.length) >= RESIZE_RATE;
    }

    private void resize() {
        //create temp
        E[] temp = (E[]) new Object[structure.length * 2];

        //copy over all elements to the temp, and reassign
        System.arraycopy(structure, 0, temp, 0, structure.length);

        structure = temp;
    }

    private void condense() {
        //create temp
        E[] temp = (E[]) new Object[size];

        //copy over all elements to the temp, and reassign
        int counter = 0;
        for(E item : structure) {
            if(item != null) {
                temp[counter] = item;
                counter++;
            }
        }
        structure = temp;
    }

    @Override
    public boolean remove(Object object) {
        //if object is null or structure is empty, throw exception
        if(object == null) {
            throw new NullPointerException("Cannot remove null object from the list");
        } else if(isEmpty()) {
            throw new NoSuchElementException("Cannot remove object from an empty list");
        }

        if(!contains(object)) { //cant remove it if it's not in structure
            return false;
        } else {
            object = castElement(object);
            for(int i = 0; i < size; i++) {
                if(structure[i].equals(object)) {
                    structure[i] = null;
                    size--;
                }
            }
            condense();
            return true;
        }
    }

    @Override
    public boolean addAll(Collection collection) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object object) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new MyArrayList<E>();
    }

    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }

    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }

    @Override
    public E[] toArray(Object[] array) {
        return (E[]) new Object[0];
    }

    @Override
    public int compareTo(Object collection) {
        return 0;
    }

    private E castElement(Object object) {
        return (E) object;
    }

    private E[] getNoNullCopy() {
        E[] array = (E[]) new Object[size];
        System.arraycopy(structure, 0, array, 0, size);
        return array;
    }

    @Override
    public String toString() {
        return "MyArrayList { " +
                "\n size = " + size +
                "\n structure = " + Arrays.toString(getNoNullCopy()) +
                "\n}";
    }
}
