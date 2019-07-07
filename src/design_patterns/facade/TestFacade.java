package design_patterns.facade;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This creates an easier interface to interact with
 * JUnit 5.
 *
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class TestFacade
{
    /**
     * Hides the Assert.assertEquals() method and follows the same rules
     * as that method.
     *
     * @param message the message to print out if the test fails
     * @param expected the expected value
     * @param actual the actual value
     */
    public void equals(String message, Object expected, Object actual)
    {
        assertEquals(expected, actual, message);
    }

    /**
     * Hides the Assert.assertNotEquals() method and follows the same rules
     * as that method.
     *
     * @param message the message to print out if the test fails
     * @param expected the expected value
     * @param actual the actual value
     */
    public void notEquals(String message, Object expected, Object actual)
    {
        assertNotEquals(expected, actual, message);
    }

    /**
     * Hides the Assert.assertTrue() method and follows the same rules
     * as that method.
     *
     * @param message the message to print out if the test fails
     * @param expression the expression that should be true
     */
    public void isTrue(String message, boolean expression)
    {
        assertTrue(expression, message);
    }

    /**
     * Hides the Assert.assertFalse() method and follows the same rules
     * as that method.
     *
     * @param message the message to print out if the test fails
     * @param expression the expression that should be false
     */
    public void isFalse(String message, boolean expression)
    {
        assertFalse(expression, message);
    }

    /**
     * Hides the Assert.assertNotEquals() method and follows the same rules
     * as that method.
     *
     * @param message the message to print out if the test fails
     * @param expected the expected value
     * @param actual the actual value
     */
    public void arrayEquals(String message, Object[] expected, Object[] actual)
    {
        assertArrayEquals(expected, actual, message);
    }

    /**
     * Hides the Assert.fail() method and follows the same rules
     * as that method.
     *
     * @param message the message to display with the test failure
     */
    public void fail(String message)
    {
        fail(message);
    }
}

