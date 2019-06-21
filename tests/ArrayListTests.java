import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.array_lists.MyArrayList;

import java.util.Arrays;
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
        assertEquals(110, myList.size(), "added in 110 elements but size is not 110");

        //verify that null values cannot be added
        try {
            myList.add(null);
            Assertions.fail("Null pointer exception was not thrown when adding in null values");
        } catch (NullPointerException exception) {
            //Good! exception was thrown
        }
    }

    @Test
    public void testToArray() {
        addElements();

        //make sure i get an array of Integers back, 1-109
        Object[] array = myList.toArray();
        Assertions.assertEquals(109, array.length, "added in 109 elements, but the array returned didn't have all of them");
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void testRemove() {
        addElements();
        System.out.println(myList.toString());
        //make sure i can remove an element
        myList.remove((Object) 1);

        System.out.println(myList.toString());

        //make sure size went down
        assertEquals(108, myList.size(), "size did not go down in remove()");

        assertFalse(myList.contains(1), "element 1 was not removed, still found in contains()");

    }

    @Test
    public void testSize() {

        //on creation size should be zero
        assertEquals(0, myList.size(), "On creation size is incorrect");
    }

    private void addElements() {
        for (int i = 1; i <= 109; i++) {
            myList.add(i);
        }
    }

}
