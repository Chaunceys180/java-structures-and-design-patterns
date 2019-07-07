import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import structures.bag.BagOfMarbles;
import exceptions.DuplicateMarbleException;
import structures.bag.Marble;
import structures.bag.Material;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests all the functions in my BagOfMarbles class
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class BagTests {

    private BagOfMarbles marbleBag;

    @Before
    public void setup() {
        marbleBag = new BagOfMarbles(5);
    }

    @Test
    public void testConstructor() {
        try {
            marbleBag = new BagOfMarbles(0);

            //this should throw an exception, otherwise:
            fail("IllegalArgumentException was not thrown with an invalid capacity");
        } catch (IllegalArgumentException e) {
            //I want an exception to be thrown
        }
    }

    @Test
    public void testAddMarble() {
        //test that a marble can be added to the bag
        Marble marble = new Marble("Red", Material.STEEL, 1);
        marbleBag.addMarble(marble);

        Assert.assertEquals("The size of the bag isn't 1 so the marble wasn't added", 1, marbleBag.size());

        //make sure an exception is thrown if a duplicate is added in with this method
        try {
            marbleBag.addMarble(marble);

            //this should throw an exception, otherwise:
            Assert.fail("DuplicateMarbleException was not thrown with a duplicate marble");
        } catch (DuplicateMarbleException e) {
            //I want an exception to be thrown
        }

        //check if the bag re-sizes to be bigger when it's full
        marbleBag.addMarble(new Marble("Red", Material.CLAY, 1));
        marbleBag.addMarble(new Marble("Blue", Material.GLASS, 1));
        marbleBag.addMarble(new Marble("Green", Material.STEEL, 1));
        marbleBag.addMarble(new Marble("Yellow", Material.CLAY, 1));
        marbleBag.addMarble(new Marble("Brown", Material.GLASS, 1));

        Assert.assertEquals("The size of the bag isn't 6 so the bag wasn't re-sized",
                6, marbleBag.size());

        // it should not be possible to change the Marble object outside
        // of the bag after it has been added to the bag

        //originally the first marble is red, so if it is yellow in my bag, fail the test
        marble.setColor("YELLOW"); //originally it is red

        Marble[] bagElements = marbleBag.getBag();

        Assert.assertFalse("The marble shouldn't be editable from outside of the bag after insertion.",
                bagElements[1].equals(marble));

        //System.out.println(marbleBag.toString());
    }

    @Test
    public void testHasMarble() {
        //test to see if i can find a duplicate marble and return true if found, false otherwise
        Marble marble1 = new Marble("Red", Material.CLAY, 1);
        Marble marble2 = new Marble("Red", Material.CLAY, 1);
        Marble differentMarble = new Marble("Blue", Material.STEEL, 1);

        marbleBag.addMarble(marble1);

        //test that it can find the duplicate
        Assert.assertTrue("hasMarble() was not able to find the duplicate marble" +
         "even though there is a duplicate being searched for", marbleBag.hasMarble(marble2));

        //test that it can't find the duplicate
        Assert.assertFalse("A duplicate marble was found even though there is no duplicate",
                marbleBag.hasMarble(differentMarble));
    }

    @Test
    public void testAddDuplicate() {
        //i need to test if an exception is thrown if there is no duplicate being added
        Marble marble1 = new Marble("Red", Material.CLAY, 1);
        Marble duplicateMarble = new Marble("Red", Material.CLAY, 1);
        Marble differentMarble = new Marble("Blue", Material.STEEL, 1);

        marbleBag.addMarble(marble1);

        try {
            marbleBag.addDuplicateMarble(differentMarble);

            //i shouldn't reach this line
            Assert.fail("NoSuchElementException was not thrown when a duplicate was not being added in");
        } catch (NoSuchElementException e) {
            //I want to be here!
        }

        //i need to test that if a duplicate is being added in, the counter is being updated
        marbleBag.addDuplicateMarble(duplicateMarble);

        Marble[] bagElements = marbleBag.getBag();

        Assert.assertEquals("Marble counter is not being updated when duplicate bag are being added in",
                2, bagElements[0].getCounter());
    }

    @Test
    public void testRemoveMarble() {
        //i need to test that an element is being removed from my bag
        Marble marble1 = new Marble("Red", Material.CLAY, 1);
        marbleBag.addMarble(marble1);
        marbleBag.removeMarble(marble1);

        Assert.assertEquals("An element is still inside the bag", 0, marbleBag.size());
        //System.out.println(marbleBag.toString()); proof

        //i need to test that a duplicate element is being decremented.
        marbleBag.addMarble(marble1);
        marbleBag.addDuplicateMarble(marble1);
        marbleBag.removeMarble(marble1);

        Marble[] bagElements = marbleBag.getBag();

        Assert.assertEquals("Duplicate bag are not being decremented",
                1, bagElements[0].getCounter());

        //i need to test that NoSuchElementException is being thrown if the marble doesn't exist
        try {
            Marble newMarble = new Marble("Gold", Material.STEEL, 1);
            marbleBag.removeMarble(newMarble);

            //i should not be able to reach this line
            Assert.fail("NoSuchElementException was not thrown when expected to be thrown");
        } catch (NoSuchElementException e) {
            //I want to be here
        }
    }

    @Test
    public void testIterator() {

        //test to make sure an iterator is being returned
        Assert.assertFalse("Iterator object returned is null", marbleBag.iterator() == null);

        //iterator should work with zero elements
        for (Marble element : marbleBag)
        {
            Assert.fail("Nothing should happen here because no elements");
        }

        //add in bag
        marbleBag.addMarble(new Marble("Blue", Material.CLAY, 1));
        marbleBag.addDuplicateMarble(new Marble("Blue", Material.CLAY, 1));
        marbleBag.addDuplicateMarble(new Marble("Blue", Material.CLAY, 1));

        Marble[] bagElements = marbleBag.getBag();

        //verify that i can use it correctly with elements in a foreach loop
        for(Marble element : marbleBag) {
            Assert.assertTrue("The Marbles were not correctly iterated through",
                    bagElements[0].equals(element));
        }

        //make sure i can't alter the structure while in the for each loop
        try {
            for (Marble element : marbleBag) {
                marbleBag.clearAll();
            }

            //i shouldn't be able to reach this line
            Assert.fail("Concurrent modification error isn't being thrown on change");
        } catch(ConcurrentModificationException e) {
            //i want to be here
        }

    }

    @Test
    public void testSize() {

        //test the default size
        Assert.assertEquals("size() is not 0 with no bag in the bag of bag",
                0, marbleBag.size());

        //test the size with one element
        Marble marble1 = new Marble("Red", Material.STEEL, 1);
        marbleBag.addMarble(marble1);
        Assert.assertEquals("size() is not 1 with 1 marble in the bag of bag",
                1, marbleBag.size());

        //test size after adding in a duplicate
        marbleBag.addDuplicateMarble(marble1);
        Assert.assertEquals("size() is not 2 with a duplicate marble in the bag of bag",
                2, marbleBag.size());

        //test size after adding in two unique bag
        Marble marble2 = new Marble("Green", Material.GLASS, 1);
        marbleBag.addMarble(marble2);
        Assert.assertEquals("size() is not 3 with a duplicate marble in the bag of bag," +
                        " as well as a unique marble", 3, marbleBag.size());

        //test size after removing a marble
        marbleBag.removeMarble(marble2);
        Assert.assertEquals("size() is not 2 after removing a marble", 2, marbleBag.size());

        //test size after removing a duplicate
        marbleBag.removeMarble(marble1);
        Assert.assertEquals("size() is not 1 after removing a duplicate marble", 1, marbleBag.size());

        //test removing a marble from a full bag
        Marble marble3 = new Marble("Yellow", Material.CLAY, 1);
        Marble marble4 = new Marble("Brown", Material.GLASS, 1);
        Marble marble5 = new Marble("Teal", Material.STEEL, 1);
        marbleBag.addMarble(marble2);
        marbleBag.addMarble(marble3);
        marbleBag.addMarble(marble4);
        marbleBag.addMarble(marble5);

        marbleBag.removeMarble(marble5);

        Assert.assertEquals("size() is not 4 after removing a marble", 4, marbleBag.size());

        //test removing the first element
        marbleBag.removeMarble(marble1);
        Assert.assertEquals("size() is not 3 after removing a marble", 3, marbleBag.size());

        //test size after clearing all
        marbleBag.clearAll();
        Assert.assertEquals("size() is not 0 after clearing the bag", 0, marbleBag.size());
    }

    @Test
    public void testMarblesByType() {
        //test to see if i can track type count
        Marble marble1 = new Marble("Yellow", Material.CLAY, 1);
        Marble marble2 = new Marble("Brown", Material.STEEL, 1);
        Marble marble3 = new Marble("Teal", Material.STEEL, 1);

        marbleBag.addMarble(marble1);
        marbleBag.addMarble(marble2);
        marbleBag.addMarble(marble3);

        Assert.assertEquals("There should be two material matches for STEEL", 2,
                marbleBag.marblesByType(marble3));

        //make sure it is still correct after removing an element
        marbleBag.removeMarble(marble3);
        Assert.assertEquals("There should be one material match for STEEL", 1,
                marbleBag.marblesByType(marble3));
    }

    @Test
    public void testClear() {
        //test to see if i can clear a single marble by type
        Marble marble1 = new Marble("Yellow", Material.CLAY, 1);
        Marble marble2 = new Marble("Brown", Material.STEEL, 1);
        Marble marble3 = new Marble("Teal", Material.STEEL, 1);

        marbleBag.addMarble(marble1);
        marbleBag.addMarble(marble2);
        marbleBag.addMarble(marble3);

        marbleBag.clear(marble1);
        Assert.assertEquals("There should be one material match for CLAY when" +
                        "removing the clay marble for a total size() of 2 in the end", 2, marbleBag.size());

        //test to see if i can remove more than 1 marble by type
        marbleBag.addMarble(marble1);
        marbleBag.clear(marble3);

        Assert.assertEquals("There should be two material match for STEEL when" +
                "removing the steel marble for a total size() of 1 in the end", 1, marbleBag.size());
    }

    @Test
    public void testClearAll() {
        //add bag in  and clear them, to make sure all bag are gone
        Marble marble1 = new Marble("Yellow", Material.CLAY, 1);
        Marble marble2 = new Marble("Brown", Material.STEEL, 1);
        Marble marble3 = new Marble("Teal", Material.STEEL, 1);

        marbleBag.addMarble(marble1);
        marbleBag.addMarble(marble2);
        marbleBag.addMarble(marble3);

        marbleBag.clearAll();
        Assert.assertEquals("size() should return zero after clearing all elements", 0,
                marbleBag.size());

    }

    @Test
    public void testTrimToSize() {
        //need to test that the length of the bag is shortened to not have any null values.
        Marble marble1 = new Marble("Yellow", Material.CLAY, 1);
        Marble marble2 = new Marble("Brown", Material.STEEL, 1);

        marbleBag.addMarble(marble1);
        marbleBag.addMarble(marble2);

        //the length of the data is 5, but size is two.
        //so i need to make sure it is resized to two
        //and that only index's 0 and 1 are there

        marbleBag.trimToSize();

        try{
            System.out.println(marbleBag.getBag()[2]); //this index should not exist after resize

            Assert.fail("The structure is not being resized correctly.");
        } catch (ArrayIndexOutOfBoundsException e) {
            //i want to be here
        }
    }
}