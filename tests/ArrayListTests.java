import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.array_lists.MyArrayList;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
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
        fail();
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
        fail();
    }

    @Test
    public void testAddWithIndex() {
        fail();
    }

    @Test
    public void testRemoveWithIndex() {
        fail();
    }

    @Test
    public void testIndexOf() {
        fail();
    }

    @Test
    public void testLastIndexOf() {
        fail();
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
        fail();
    }

    @Test
    public void testRetainAll() {
        fail();
    }

    @Test
    public void testRemoveAll() {
        fail();
    }

    @Test
    public void testContainsAll() {
        fail();
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

    @Test
    public void testCompareTo() {
        fail();
    }

    @Test
    public void test() {
        fail();
    }

    private void addElements() {
        for (int i = 1; i <= 19; i++) {
            myList.add(i);
        }
    }

}
