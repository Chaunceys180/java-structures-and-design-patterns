package structures.bag;

import exceptions.DuplicateMarbleException;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is stores a bag of Marble objects that can be manipulated to
 * add/remove more bag or stack duplicate bag
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class BagOfMarbles implements IMarbleBag, Iterable<Marble>{

    private Marble[] data;
    private int size;
    private int modCount;

    private static final int DEFAULT_CAPACITY = 10;

    /**
     * This is the BagOfMarbles constructor that sets the initial capacity
     * @param capacity is the initial size of the bag
     */
    public BagOfMarbles(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("The bag has to have a greater size than 0");
        }
        data = new Marble[capacity];
    }

    @Override
    public void addMarble(Marble marble) {
        //if the bag is full, resize the bag
        if(size == data.length) {
            Marble[] tempBag = new Marble[size*2];

            //copy over elements
            System.arraycopy(data, 0, tempBag, 0, data.length);

            //re-assign data
            data = tempBag;

            //then add the new element
            addMarble(marble);
        } else {
            //bag doesn't need to be re-sized so check if there is any duplicates
            for(int i = 0; i <= size; i++) {
                //if duplicate, throw exception
                if(data[i] != null && data[i].equals(marble)) {
                    throw new DuplicateMarbleException("The marble is already apart of the bag." +
                            "addDuplicateMarble() should be called instead.");
                }
                //find the next open spot, add the marble in there
                if(data[i] == null) {
                    data[i] = new Marble(marble);
                    size++;
                    modCount++;
                    break;
                }
            }
        }
    }

    @Override
    public boolean hasMarble(Marble marble) {
        int index = 0;
        while(data[index] != null) { //cycle through all bag
            if(data[index].equals(marble)) { //if there's a duplicate, return true
                return true;
            }
            index++;
        }
        return false; //no match, so return false
    }

    @Override
    public void addDuplicateMarble(Marble marble) {
        //if there's no duplicate, throw an exception
        if(!hasMarble(marble)) {
            throw new NoSuchElementException("There is no duplicate marble stored, addMarble() should be used");
        }
        int index = 0;
        while(data[index] != null) { //cycle through all bag
            if(data[index].equals(marble)) { //find the duplicate, and increment the counter
                data[index].setCounter(data[index].getCounter()+1);
                size++;
                modCount++;
            }
            index++;
        }
    }

    @Override
    public void removeMarble(Marble marble) {
        if(!hasMarble(marble)) { //if it doesn't have the marble, i cant remove it
            throw new NoSuchElementException("This marble doesn't exist in the bag");
        }

        BagOfMarbles tempBag = new BagOfMarbles(data.length);
        for(Marble element : data) {
            if(element != null && element.equals(marble)) { //if found, check the count
                if(element.getCounter() > 1) { //if it just needs to be incremented down & not removed
                    element.setCounter(element.getCounter()-1);
                    tempBag.addMarble(element);
                }
            } else { //it's not equal to the removed marble so copy it over
                if(element != null){
                    tempBag.addMarble(element);
                }
            }
        }
        data = tempBag.getBag();
        size--;
        modCount++;
    }

    @Override
    public Iterator<Marble> iterator() {
        return new BagIterator(data, modCount);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int marblesByType(Marble marble) {
        int counter = 0;
        for(Marble element : data) { //cycle through all the bag
            //look at the type, and increment if they're equal types
            if( element != null && element.getMaterial().equals(marble.getMaterial())) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public void clearAll() {
        data = new Marble[DEFAULT_CAPACITY];
        size = 0;
        modCount++;
    }

    @Override
    public void clear(Marble marble) {
        BagOfMarbles tempBag = new BagOfMarbles(data.length);

        for(Marble element : data) {
            //if valid element & element is not the same material
            if(element != null && !element.getMaterial().equals(marble.getMaterial())) {
                //then add in the element
                tempBag.addMarble(element);
            }
        }
        data = tempBag.getBag();
        size = tempBag.size();
        modCount++;
    }

    @Override
    public void trimToSize() {
        Marble[] tempBag = new Marble[size];

        //for all elements in data, add to tempBag if valid element
        for(int i = 0; i < data.length; i++) {
            if(data[i] != null) {
                tempBag[i] = data[i];
            }
        }
        data = tempBag;
    }

    @Override
    public String toString() {
        return "BagOfMarbles{" +
                "data=" + Arrays.toString(data) +
                ", size=" + size +
                ", modCount=" + modCount +
                '}';
    }

    /**
     * This function returns an array of bag
     * @return returns the array of bag
     */
    public Marble[] getBag() {
        return data;
    }

    private class BagIterator implements Iterator<Marble> {

        private Marble[] data;
        private int index = 0;
        private int comparableModCount;

        private BagIterator(Marble[] data, int modCount) {
            this.data = new Marble[size];

            int counter;
            for(int i = 0; i < size; i++) { //cycle through the array

                if(data[i] == null) { //if out of bounds, this will stop the loop
                    break;
                }

                //if the item is multiple bag
                counter = data[i].getCounter();
                if( counter > 1) {
                    //make a copy of the marble
                    Marble marble = new Marble(data[i].getColor(), data[i].getMaterial(), data[i].getCounter());
                    marble.setCounter(1);

                    //for the number of duplicate bag, add in that marble, then move to next position
                    for(int j = 0; j < counter; j++) {
                        this.data[i+j] = marble;
                    }
                } else {
                    //else it's not a duplicate so just add it in an empty space
                    counter = 0;
                    for(Marble element : this.data) { //looking for the next empty space
                        if(element != null) {
                            counter++;
                        }
                    }

                    //add in the marble
                    this.data[counter] = data[i];
                }
            }
            this.comparableModCount = modCount;
        }


        @Override
        public boolean hasNext() {
            //if there's been any changes while iterating, throw an exception
            if(comparableModCount != modCount) {
                throw new ConcurrentModificationException("Changes were made while Iterating");
            }
            return index < size; //return true if there's a next index
        }

        @Override
        public Marble next() {
            //if there's been any changes while iterating, throw an exception
            if(comparableModCount != modCount) {
                throw new ConcurrentModificationException("Changes were made while Iterating");
            }
            Marble currentMarble = data[index];
            index++;
            return currentMarble;
        }

        @Override
        public String toString() {
            return "BagIterator{" +
                    "data=" + Arrays.toString(data) +
                    ", index=" + index +
                    ", comparableModCount=" + comparableModCount +
                    '}';
        }
    }
}
