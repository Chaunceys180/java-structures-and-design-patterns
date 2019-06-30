package structures.array_lists;

import java.util.*;

/**
 * This class is a custom ArrayList data structure that i'm making as proof of comprehension.
 * @param <T> is the generic element to be stored
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class MyArrayList<T> implements List<T> {

    private int size;
    private T[] structure;

    private static final int INITIAL_SIZE = 20;
    private static final double RESIZE_RATE = 0.75;

    /**
     * MyArrayList Constructor
     */
    public MyArrayList() {
        structure = (T[]) new Object[INITIAL_SIZE];
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
        T element = castElement(object);

        for (T item : structure) {
            if(item != null) {
                if (item.equals(element)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public T[] toArray() {
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
        if(size == 0) {
            return true;
        } else {
            return (size / structure.length) >= RESIZE_RATE;
        }
    }

    private void resize() {
        //create temp
        T[] temp = (T[]) new Object[(structure.length + 1) * 2];

        //copy over all elements to the temp, and reassign
        System.arraycopy(structure, 0, temp, 0, structure.length);

        structure = temp;
    }

    private void ensureCapacity(int minCapacity) {
        //create temp
        T[] temp = (T[]) new Object[minCapacity];

        //copy over all elements to the temp, and reassign
        System.arraycopy(structure, 0, temp, 0, structure.length);

        structure = temp;
    }

    private void trimToSize() {
        //this function gets rid of all t
        // he null values in the structure
        //create temp
        T[] temp = (T[]) new Object[size];

        //copy over all elements to the temp, and reassign
        int counter = 0;
        for(T item : structure) {
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
            trimToSize();
            return true;
        }
    }

    @Override
    public boolean addAll(Collection collection) {
        //save size before
        int initSize = size;

        for(Object item : collection) {
            add(item);
        }

        //then items added successfully
        return initSize < size;
    }

    @Override
    public boolean addAll(int index, Collection collection) {
        if(index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index is out of range");
        }

        if(collection == null) {
            throw new NullPointerException("the specified collection is null");
        }

        if(index > size) {
            index = size;
        }

        //make sure structure has the index
        if(structure.length < size + collection.size()) {
            ensureCapacity(structure.length + collection.size());
        }

        //then merge them starting with the collection at the desired index
        T[] temp = (T[]) new Object[structure.length];
        for(Object item : collection) {
            temp[index] = (T) item;
            index++;
        }

        //then add in the rest from structure
        int counter = 0;
        for (T item : structure) {
            if (item != null) { //then add item while i can find a spot
                if(temp[counter] != null) {
                    counter = index;
                }
                temp[counter] = item;
                counter++;
            }
        }

        //reset structure
        structure = temp;
        size+= collection.size();
        return true;
    }

    @Override
    public void clear() {
        structure = (T[]) new Object[INITIAL_SIZE];
        size = 0;
    }

    @Override
    public T get(int index) {
        if(index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        } else if(index >= size) {
            throw new IndexOutOfBoundsException("Index is greater than the size of the ArrayList");
        }
        return structure[index];
    }

    @Override
    public T set(int index, T element) {
        if(index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        } else if(index >= size) {
            throw new IndexOutOfBoundsException("Index is greater than the size of the ArrayList");
        }

        T prevItem = structure[index];
        structure[index] = element;
        return prevItem;
    }

    @Override
    public void add(int index, T element) {
        if(index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        } else if(index >= size) {
            throw new IndexOutOfBoundsException("Index is greater than the size of the ArrayList");
        }

        if(structure[index] == null) {
            structure[index] = element;
        } else {
            Collection<T> collection = new LinkedList<>();
            collection.add(element);
            addAll(index, collection);
        }
    }

    @Override
    public T remove(int index) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }
        T item = structure[index];
        structure[index] = null;
        size--;
        trimToSize();
        return item;
    }

    @Override
    public int indexOf(Object object) {
        if(object == null || size == 0) {
            return -1;
        }

        for(int i = 0; i < size; i++) {
            if(structure[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        if(object == null || size == 0) {
            return -1;
        }

        for(int i = size-1; i >= 0; i--) {
            if(structure[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {

        if(fromIndex < 0 || toIndex > size) {
            throw new IndexOutOfBoundsException("an endpoint index value is out of range");
        } else if(fromIndex > toIndex) {
            throw new IllegalArgumentException("the endpoint indices are out of order");
        }

        List<T> temp = new MyArrayList<>();

        while(fromIndex <= toIndex) {
            temp.add(structure[fromIndex]);
            fromIndex++;
        }

        return temp;
    }

    @Override
    public boolean retainAll(Collection collection) {
        collectionCheck(collection);

        //record size for comparison.
        int preSize = size();

        //if the structure contains the item, save it. if the structure doesnt have the item, dont add it.
        T[] temp = (T[]) new Object[collection.size()];

        //reset size
        size = 0;
        for(Object item : collection) {
            if(contains(item)) {
               temp[size] = (T) item;
               size++;
            }
        }
        //reassign
        structure = temp;
        return preSize != size;
    }

    @Override
    public boolean removeAll(Collection collection) {
        collectionCheck(collection);

        //record list size
        int preSize = size;

        for(Object item : collection) {
            if(contains(item)) {
                remove(item);
            }
        }

        return size != preSize; //if list changed
    }

    @Override
    public boolean containsAll(Collection collection) {
        collectionCheck(collection);

        for(Object item : collection) {
            if(!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T[] toArray(Object[] array) {
        return (T[]) new Object[0];
    }

    private T castElement(Object object) {
        return (T) object;
    }

    private T[] getNoNullCopy() {
        T[] array = (T[]) new Object[size];
        int counter = 0;
        for (T t : structure) {
            if (t != null) {
                array[counter] = t;
                counter++;
            }
        }
        return array;
    }

    private void collectionCheck(Collection<Object> collection) {
        if(collection == null || collection.contains(null)) {
            throw new NullPointerException("collection contains a null element or is null");
        }

        String myGenericType = structure[0].getClass().getTypeName();
        String otherGenericType = collection.iterator().next().getClass().getTypeName();

        if(!myGenericType.equals(otherGenericType)) {
            throw new ClassCastException("Incompatible classes. Current type: " + myGenericType +
                    ", Incompatible collection of: " + otherGenericType);
        }
    }

    @Override
    public String toString() {
        return "MyArrayList { " +
                "\n size = " + size +
                "\n structure = " + Arrays.toString(getNoNullCopy()) +
                "\n}";
    }
}
