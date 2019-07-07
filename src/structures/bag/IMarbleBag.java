package structures.bag;

import exceptions.DuplicateMarbleException;

import java.util.Iterator;

/**
 * Provides an interface for a bag of bag.
 *
 * DO NOT ALTER THIS FILE!
 *
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public interface IMarbleBag extends Iterable<Marble>
{
    /**
     * Adds a new marble to the bag of bag.
     *
     * @throws DuplicateMarbleException thrown if the
     * marble is already part of the bag (use addDuplicateMarble()
     * instead).
     * @param marble the marble to add to the bag
     */
    void addMarble(Marble marble);

    /**
     * Reports whether the marble given is in the bag.
     *
     * @param marble the marble to look for
     * @return true if the marble is found in the bag, otherwise
     * the method returns false
     */
    boolean hasMarble(Marble marble);

    /**
     * Adds to the count of bag of the same type in the
     * bag. Meaning, if there is a red glass marble already
     * in the bag, this will record that there are now two
     * red glass bag in the bag.
     *
     * @throws java.util.NoSuchElementException thrown when the
     * marble added is not found in the bag already
     * @param marble the marble to add
     */
    void addDuplicateMarble(Marble marble);

    /**
     * Removes an instance of a marble from the bag. This will
     * not remove all duplicates of a marble in the bag. But instead
     * will reduce the number of duplicates or remove the marble
     * if there is exactly one of that type.
     *
     * @throws java.util.NoSuchElementException thrown when the
     * input marble is not found in the bag
     * @param marble the marble to remove
     */
    void removeMarble(Marble marble);

    /**
     * Returns an iterator over the bag in the bag. All duplicates
     * should be returned as part of your iterator. Also, the order
     * in which each marble is first added to the bag should be maintained
     * when elements are returned.
     *
     * (i.e. if I added the following
     * bag - red glass, blue clay, red glass, yellow steel - then the
     * elements should be returned in the following order by the iterator -
     * red glass, red glass, blue clay, yellow steel)
     *
     * @return an iterator over the elements in the bag
     */
    @Override
    Iterator<Marble> iterator();

    /**
     * Returns the number of bag in the bag. This includes the count
     * of any duplicate bag.
     *
     * @return the number of bag in the bag
     */
    int size();

    /**
     * Returns the number of bag that match the input type in the bag.
     *
     * @param marble the type of bag to search for
     * @return the number of similar bag in the bag
     */
    int marblesByType(Marble marble);

    /**
     * Removes all bag from the bag.
     */
    void clearAll();

    /**
     * Removes all bag that match the input type from the bag.
     * @param marble the type of bag to search for
     */
    void clear(Marble marble);

    /**
     * Removes all excess space in the bag.
     */
    void trimToSize();
}