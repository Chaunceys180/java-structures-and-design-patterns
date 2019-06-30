import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.array_lists.MyArrayList;
import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test file confirms functionality of my own array list class
 *
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class ArrayListTests {

    private static List<Integer> myList;

    @BeforeEach
    public void create() {
        myList = new MyArrayList<>();

        //tell the garbage collector to run more frequently to reclaim memory
        System.gc();
        Runtime.getRuntime().gc();
        System.runFinalization();
    }

    @Test
    public void testSize() {

        //on creation size should be zero
        assertEquals(0, myList.size(), "On creation size is incorrect");
    }

    @Test
    public void testIsEmpty() {
        myList.add(1);

        assertFalse(myList.isEmpty(), "added one element but returns true for isEmpty()");

        myList.remove((Integer) 1);

        assertTrue(myList.isEmpty(), "removed all the elements but isEmpty() is false");

        myList.add(2);

        myList.clear();

        assertTrue(myList.isEmpty(), "cleared all the elements but isEmpty() is false");
    }

    @Test
    public void testContains() {

        addElements();

        //make sure they're in there
        for(int i = 1; i <= 19; i++) {
            assertTrue(myList.contains((Integer) i));
        }

        //make sure elements not in there aren't found
        for(int i = 20; i <= 30; i++) {
            assertFalse(myList.contains((Integer) i));
        }

    }

    @Test
    public void testIterator() {
        fail();
    }

    @Test
    public void testAdd() {
        myList.add(7);

        //make sure a single element can be added
        assertTrue(myList.contains(7), "Added element 7, but did not find it in contains()");
        System.out.println(myList.toString());

        //make sure i can add in over 100 elements so that resize is working
        addElements();

        System.out.println(myList.toString());

        //i should have 110 elements
        assertEquals(20, myList.size(), "added in 110 elements but size is not 110");

        //verify that null values cannot be added
        try {
            myList.add(null);
            Assertions.fail("Null pointer exception was not thrown when adding in null values");
        } catch (NullPointerException exception) {
            //Good! exception was thrown
        }
    }

    @Test
    public void testRemove() {
        addElements();
        System.out.println(myList.toString());
        //make sure i can remove an element
        myList.remove((Object) 1);

        System.out.println(myList.toString());

        //make sure size went down
        assertEquals(18, myList.size(), "size did not go down in remove()");

        assertFalse(myList.contains(1), "element 1 was not removed, still found in contains()");

    }

    @Test
    public void testAddAll() {

        //create a collection
        Collection<Integer> collection = new LinkedList<>();

        //add in numbers 10-15
        for(int i = 10; i <= 15; i++) {
            collection.add(i);
        }

        //add in numbers 0-9
        for(int i = 0; i <= 9; i++) {
            myList.add(i);
        }

        //add my collection
        myList.addAll(collection);

        //verify that mylist has everything in the collection
        for(Integer item : collection) {
            assertTrue(myList.contains(item), "Item was added from a collection but not found");
        }

        //make sure size is being kept track of

        assertEquals(16, myList.size(), "16 elements added but size is incorrect (0-15)");

        System.out.println(myList.toString());

    }

    @Test
    public void testAddAllWithIndex() {
        //create a collection
        Collection<Integer> collection = new LinkedList<>();

        //add in numbers 10-15
        for(int i = 10; i <= 15; i++) {
            collection.add(i);
        }

        //add in numbers 0-9
        for(int i = 0; i <= 9; i++) {
            myList.add(i);
        }

        //add into the middle at index 4
        myList.addAll(4, collection);

        for(Integer item : collection) {
            assertTrue(myList.contains(item), "Item was added from a collection but not found");
        }

        for(int i = 0; i <= 9; i++) {
            assertTrue(myList.contains(i), "original elements were not found in list: " + i);
        }

        for(int i = 4; i < collection.size() + 4; i++) {
            assertTrue(collection.contains(myList.get(i)), "Element from collection not found at appropriate index");
        }

        //make sure that exceptions get thrown



        //make sure i can't add elements at any index way out there

        myList.addAll(16, collection);
        for(int i = 16; i < collection.size() + 16; i++) {
            assertTrue(collection.contains(myList.get(i)), "Element from collection not found at appropriate index");
        }

        System.out.println(myList.toString());
    }

    @Test
    public void testClear() {

        addElements();

        myList.clear();

        assertTrue(myList.isEmpty(), "list is not empty after clear");

        assertEquals(0, myList.size(), "size was not 0 after being cleared");

    }

    @Test
    public void testGet() {
        addElements();

        assertEquals(1, myList.get(0), "1 was added at index 0, 1 was not found: " + myList.get(0));

        try {
            myList.get(-5);
            fail("Index out of bounds exception not thrown when given a negative index to get()");
        } catch (IndexOutOfBoundsException exception) {
            //good
        }

        try {
            myList.get(50);
            fail("Index out of bounds exception not thrown when given a index greater than the structure length");
        } catch (IndexOutOfBoundsException exception) {
            //good
        }

    }

    @Test
    public void testSet() {

        addElements();

        try {
            myList.set(-5, 8);
            fail("Index out of bounds exception not thrown when given a negative index to set()");
        } catch (IndexOutOfBoundsException exception) {
            //good
        }

        try {
            myList.set(50, 9);
            fail("Index out of bounds exception not thrown when given a index greater than the structure length");
        } catch (IndexOutOfBoundsException exception) {
            //good
        }

        //make sure appropriate element is returned
        assertEquals(1, myList.set(0, 50), "set element 1 at index 0 to 50, element 1 was not returned");

        //make sure that element gets replaced
        assertTrue(myList.contains(50), "list did not contain 50 after setting 1 to 50");

        //make sure that 1 is no longer in there
        assertFalse(myList.contains(1), "set 1 to 50 but element 1 was still found");


    }

    @Test
    public void testAddWithIndex() {

        addElements();

        try {
            myList.set(-5, 8);
            fail("Index out of bounds exception not thrown when given a negative index to set()");
        } catch (IndexOutOfBoundsException exception) {
            //good
        }

        try {
            myList.set(50, 9);
            fail("Index out of bounds exception not thrown when given a index greater than the structure length");
        } catch (IndexOutOfBoundsException exception) {
            //good
        }

        //add in element 20 at index 3
        myList.add(3, 20);

        //make sure list still contains all old values
        for(int i = 1; i <= 19; i++) {
            assertTrue(myList.contains(i), "old element lost while adding with index");
        }

        //make sure new element is in there
        assertTrue(myList.contains(20), "element 20 added but not found");

        //make sure it's at the appropriate index
        assertEquals(myList.get(3), 20, "element added was not at the appropriate index");

        //make sure size is correct after adding
        assertEquals(20, myList.size(), "size is not correct after adding new element by index");
    }

    @Test
    public void testRemoveWithIndex() {
        addElements();

        //make sure exception is thrown if invalid index
        try {
            myList.remove(-5);
            fail("Index out of bounds exception not thrown when given a negative index in remove(int index)");
        } catch (IndexOutOfBoundsException exception) {
            //good
        }

        //make sure exception is thrown if invalid index
        try {
            myList.remove(myList.size());
            fail("Index out of bounds exception not thrown when given a index too large");
        } catch (IndexOutOfBoundsException exception) {
            //good
        }

        System.out.println(myList.toString());


        //make sure element is returned when removed
        //i know element 1 is at index 0, so remove it
        assertEquals(myList.remove(0), 1, "removed element 1 at index 0, but element 1 wasn't returned");

        assertFalse(myList.contains((Integer) 1), "element still contains element 1 after removing");

        assertEquals(18, myList.size(), "size not correct after removal");

        System.out.println(myList.toString());


    }

    @Test
    public void testIndexOf() {
        addElements();

        //make sure the index's of everything match

        for(int i = 1; i <= 19; i++) {
            assertEquals(i-1, myList.indexOf((Integer) i), "Index returned does not match the element");
        }

        //make sure -1 gets returned if it doesn't work
        assertEquals(-1, myList.indexOf(null), "expected -1 from searching for a null value");

        assertEquals(-1, myList.indexOf(90), "expected -1 from an object that isn't in the list");

        myList.remove(0);
        assertEquals(-1, myList.indexOf((Integer) 1), "expected -1, because 1 was removed and doesn't have an index");

        myList.remove((Integer) 2);
        assertEquals(-1, myList.indexOf((Integer) 2), "removed element two by index and was still found afterwards");

        myList.add(76);
        assertEquals(myList.size()-1, myList.indexOf(76), "added element 76 but it was not found at the last index");

        myList.add(3, 23);
        assertEquals(3, myList.indexOf(23), "added 23 to index 3, index 3 was not returned when looking for 23");

        myList.clear();
        assertEquals(-1, myList.indexOf(3), "expected -1 when looking for an index on an empty list");
    }

    @Test
    public void testLastIndexOf() {
        addElements();

        //make sure the index's of everything match

        for(int i = 19; i >= 1; i--) {
            assertEquals(i-1, myList.indexOf((Integer) i), "Index returned does not match the element");
        }

        //make sure -1 gets returned if it doesn't work
        assertEquals(-1, myList.indexOf(null), "expected -1 from searching for a null value");

        assertEquals(-1, myList.indexOf(90), "expected -1 from an object that isn't in the list");

        myList.remove(0);
        assertEquals(-1, myList.indexOf((Integer) 1), "expected -1, because 1 was removed and doesn't have an index");

        myList.remove((Integer) 2);
        assertEquals(-1, myList.indexOf((Integer) 2), "removed element two by index and was still found afterwards");

        myList.add(76);
        assertEquals(myList.size()-1, myList.indexOf(76), "added element 76 but it was not found at the last index");

        myList.add(3, 23);
        assertEquals(3, myList.indexOf(23), "added 23 to index 3, index 3 was not returned when looking for 23");

        myList.clear();
        assertEquals(-1, myList.indexOf(3), "expected -1 when looking for an index on an empty list");
    }

    @Test
    public void testListIterator() {
        fail();
    }

    @Test
    public void testListIteratorWithIndex() {
        fail();
    }

    @Test
    public void testSubList() {
        addElements();
        List<Integer> subList;
        //there's 20 elements in the list now

        //make sure proper exceptions are thrown
        try {
            subList = myList.subList(50, 8);
            fail();
        } catch (IllegalArgumentException exception) {
            //good
        }

        try {
            subList = myList.subList(-3, 21);
            fail();
        } catch (IndexOutOfBoundsException exception) {
            //good
        }

        //get a sub list from 5-10
        subList = myList.subList(5, 10);

        //verify size is 6.  [6,7,8,9,10,11]
        int counter = 6;
        for (int i = 0; i < subList.size(); i++) {
            assertEquals(counter, subList.get(i), "item did not match what was expected in the sublist.");
            counter++;
        }


    }

    @Test
    public void testRetainAll() {
        addElements();

        Collection<Paint> badCollection = new ArrayList<>();

        //test for exceptions
        try {
            myList.retainAll(null);
            fail("exception not thrown will null collection");
        } catch (NullPointerException exception) {
            //good
        }

        try {
            Paint color = new Color(222,222,222);
            badCollection.add(color);
            myList.retainAll(badCollection);
            fail("exception not thrown with different type collections being retained");
        } catch (ClassCastException exception) {
            //good
        }

        Collection<Integer> goodCollection = new ArrayList<>();

        for(int i = 5; i <= 10; i++) {
            goodCollection.add(i);
        }

        myList.retainAll(goodCollection);

        //verify that size is correct
        assertEquals(6, myList.size(), "size is incorrect after retaining all from a collection");

        for(int i = 5; i <= 10; i++) {
            assertTrue(myList.contains(i), "Items that were meant to be retained are missing");
        }

        for(int i = 11; i <= 19; i++) {
            assertFalse(myList.contains(i), "Items that were meant to be dismissed are still in the list");
        }

    }

    @Test
    public void testRemoveAll() {
        addElements();

        Collection<Integer> other = new ArrayList<>();
        for(int i = 5; i <= 10; i++) {
            other.add(i);
        }

        myList.removeAll(other);

        assertFalse(myList.containsAll(other), "list still contains elements from collection even after removal");
    }

    @Test
    public void testContainsAll() {
        addElements();

        Collection<Integer> other = new ArrayList<>();

        for(int i = 5; i <= 10; i++) {
            other.add(i);
        }

        assertTrue(myList.containsAll(other), "collection of items returns false even though all elements are in list");

        other.add(9090);

        assertFalse(myList.containsAll(other), "collection has elements not in list but still says it contains them");

        try {
            myList.containsAll(null);
            fail("exception not thrown when given a null collection in containsAll()");
        } catch (NullPointerException exception) {
            //good
        }

        try {
            Collection<Paint> badCollection = new ArrayList<>();
            badCollection.add(new Color(234,234,234));
            myList.containsAll(badCollection);
            fail("exception not thrown when given an incompatible collection");
        } catch (ClassCastException exception) {
            //good
        }
    }

    @Test
    public void testToArray() {
        addElements();

        //make sure i get an array of Integers back, 1-19
        Object[] array = myList.toArray();
        Assertions.assertEquals(19, array.length, "added in 20 elements, but the array " +
                "returned didn't have all of them");
        System.out.println(Arrays.toString(array));
    }

    private void addElements() {
        for (int i = 1; i <= 19; i++) {
            myList.add(i);
        }
    }

}
