import design_patterns.facade.TestFacade;
import exceptions.StackUnderflowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.stacks.IStack;
import structures.stacks.Stack;

import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * This set of unit tests verifies the behavior of all methods
 * in the IStack interface
 *
 * @author Chanucey Brown-Castro
 * @version 1.0
 */
public class StackTests extends TestFacade
{
    private IStack<Integer> stack;

    @BeforeEach
    public void setup()
    {
        stack = new Stack<Integer>();
    }

    @Test
    public void testPushEntry()
    {
        final int NUM_ELEMENTS = 10;

        //add a few items
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            stack.push(i);
        }

        //verify the items are present in the stack
        equals("size() is incorrect after adding elements",
                NUM_ELEMENTS, stack.size());

        //verify each item
        for (int i = NUM_ELEMENTS; i >= 1; i--)
        {
            equals("Unexpected element found when retrieving element",
                    i, stack.pop());
        }
    }

    @Test
    public void testPushEntries()
    {
        Integer[] elements = new Integer[] {1, 2, 3};
        stack.pushAll(elements);

        //verify the items are present in the stack
        equals("size() is not correct after adding an array of elements",
                elements.length, stack.size());

        //verify each item
        for (int i = elements.length - 1; i >= 0; i--)
        {
            equals("Unexpected element found when retrieving element",
                    elements[i], stack.pop());
        }

        //add a sequence of arrays
        stack.pushAll(elements);
        stack.pushAll(elements);
        equals("size() is not correct after adding several arrays",
                elements.length * 2, stack.size());
    }

    @Test
    public void testPopEntry()
    {
        //using pop on an empty stack should throw an exception

        try
        {
            stack.pop();
            fail("Calling pop() on an empty stack should throw an exception");
        }
        catch (StackUnderflowException ex)
        {
            //do nothing...
        }

        stack.push(1);
        stack.push(2);
        equals("Incorrect element returned from pop()",
                2, stack.pop());

        stack.push(3);
        equals("Incorrect element returned from pop()",
                3, stack.pop());
        equals("Incorrect element returned from pop()",
                1, stack.pop());
    }

    @Test
    public void testPopAll()
    {
        final int NUM_ELEMENTS = 10;

        //add a few items
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            stack.push(i);
        }

        //remove all elements and verify that the elements returned match
        List<Integer> elements = stack.popAll();
        equals("Number of elements returned from popAll() is incorrect",
                NUM_ELEMENTS, elements.size());

        for (int i = 0; i < NUM_ELEMENTS; i++)
        {
            equals("Incorrect element found in array returned from popAll()",
                    i + 1, elements.get(i));
        }

        //verify that the stack is now empty
        isTrue("Stack should be empty after calling popAll()", stack.isEmpty());
    }

    @Test
    public void testSizeAndIsEmpty()
    {
        //the size of the stack should be zero at first
        equals("size() should be zero initially", 0, stack.size());
        isTrue("isEmpty() should be true initially", stack.isEmpty());

        final int NUM_ELEMENTS = 3;

        //add a few items, verifying the size as we go
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            stack.push(i);

            equals("Unexpected size() after adding elements", i, stack.size());
        }

        //isEmpty() should be false at this point
        isFalse("isEmpty() should be false after adding elements", stack.isEmpty());

        //remove elements, verifying the size as we go
        for (int i = NUM_ELEMENTS; i >= 1; i--)
        {
            equals("Unexpected element when removing elements",
                    i, stack.pop());
        }
    }

    @Test
    public void testClear()
    {
        final int NUM_ELEMENTS = 3;

        //add a few items
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            stack.push(i);
        }

        stack.clear();
        equals("Unexpected size() after removing elements",
                0, stack.size());
    }

    @Test
    public void testIterator()
    {
        isFalse("Iterator object returned is null", stack.iterator() == null);

        //iterator should still work with zero elements
        for (int element : stack)
        {
            fail("Iterator returns an element from an empty stack");
        }

        final int NUM_ELEMENTS = 5;

        //add a few items
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            stack.push(i);
        }

        //verify that we can use an iterator with the for-each loop
        int count = 1;
        for (int element : stack)
        {
            equals("Unexpected element found using an iterator after adding 10 elements",
                    count, element);
            count++;
        }

        //verify that you cannot concurrently alter the structure
        try
        {
            boolean first = true;
            for (int element : stack)
            {
                if (first)
                {
                    stack.pop();
                    first = false;
                }
            }
            fail("Concurrent modification allowed with iterator");
        }
        catch (ConcurrentModificationException ex)
        {
            //do nothing...
        }
    }
}
