import design_patterns.facade.TestFacade;
import exceptions.EmptyQueueException;
import org.junit.Before;
import org.junit.Test;
import structures.queues.ITwoWayQueue;
import structures.queues.TwoWayQueue;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * This file tests and verifies functionality of my two-way queue
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class QueueTests extends TestFacade
{
    private ITwoWayQueue<Integer> queue;

    @Before
    public void setup()
    {
        queue = new TwoWayQueue<>();
    }

    @Test
    public void testEnqueueFirst()
    {
        //test adding to first, removing from last
        testEnqueue(
                element -> queue.enqueueFirst(element),
                () -> queue.dequeueLast(),
            "enqueueFirst()"
        );
    }

    private void testEnqueue(SingleArgDelegate<Integer> enqueue,
                             NoArgDelegate<Integer> dequeue,
                             String methodName)
    {
        //add a few items to the start of the queue
        final int NUM_ELEMENTS = 5;
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            enqueue.execute(i);
        }

        //verify that the size of the structure matches
        equals("size() is incorrect after calling " + methodName,
                NUM_ELEMENTS, queue.size());

        //verify that we can retrieve them in the same order
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            equals("Elements are not accessible in FIFO order",
                    i, dequeue.execute());
        }
    }

    @Test
    public void testEnqueueLast()
    {
        //test adding to first, removing from last
        testEnqueue(
                element -> queue.enqueueLast(element),
                () -> queue.dequeueFirst(),
            "enqueueLast()"
        );
    }

    @Test
    public void testEnqueueAllFirst()
    {
        testEnqueueAll(
                elements -> queue.enqueueAllFirst(elements),
                element -> queue.enqueueFirst(element),
                (NoArgDelegate<Integer>) () -> queue.dequeueLast(),
            "enqueueAllFirst()"
        );
    }

    private void testEnqueueAll(SingleArgDelegate<Integer[]> enqueueAll,
                                SingleArgDelegate<Integer> enqueue,
                                NoArgDelegate dequeue,
                                String methodName)
    {
        Integer[] elements = {1, 2, 3, 4, 5};

        //add a group of elements
        enqueueAll.execute(elements);

        //verify our elements were added
        equals("size() is incorrect after calling " + methodName,
                elements.length, queue.size());

        //add some elements, then add the array
        for (int i = 0; i < elements.length; i++)
        {
            equals("Elements are not accessible in FIFO order",
                    elements[i], dequeue.execute());
        }

        //verify insert order
        enqueue.execute(0);
        enqueueAll.execute(elements);
        enqueueAll.execute(elements);
        enqueue.execute(6);

        Integer[] order = {0, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6};

        //verify our elements were added
        equals("size() is incorrect after calling testEnqueueAllFirst()",
                order.length, queue.size());

        //add some elements, then add the array
        for (int i = 0; i < order.length; i++)
        {
            equals("Elements are not accessible in FIFO order",
                    order[i], dequeue.execute());
        }
    }

    @Test
    public void testEnqueueAllLast()
    {
        testEnqueueAll(
                elements -> queue.enqueueAllLast(elements),
                element -> queue.enqueueLast(element),
                (NoArgDelegate<Integer>) () -> queue.dequeueFirst(),
            "enqueueAllLast()"
        );
    }

    @Test
    public void testDequeueFirst()
    {
        testDequeue(
                element -> queue.enqueueLast(element),
                () -> queue.dequeueFirst(),
            "dequeueFirst()"
        );
    }

    private void testDequeue(SingleArgDelegate<Integer> enqueue,
            NoArgDelegate<Integer> dequeue, String methodName)
    {
        emptyQueueCheck(dequeue, methodName);

        //verify the FIFO ordering
        enqueue.execute(1);
        checkElement(1, dequeue);

        enqueue.execute(2);
        enqueue.execute(3);
        checkElement(2, dequeue);
        checkElement(3, dequeue);

        enqueue.execute(4);
        checkElement(4, dequeue);
    }

    private void checkElement(int element, NoArgDelegate<Integer> dequeue)
    {
        equals("Elements are not returned in FIFO order",
                element, dequeue.execute());
    }

    private void emptyQueueCheck(NoArgDelegate dequeue, String methodName)
    {
        //make sure you can't dequeue an empty queue
        try
        {
            dequeue.execute();
            fail("No exception thrown after invoking " + methodName +
                 " on an empty queue");
        }
        catch (EmptyQueueException ex)
        {
            //do nothing...
        }
    }

    @Test
    public void testDequeueLast()
    {
        testDequeue(
                element -> queue.enqueueLast(element),
                () -> queue.dequeueFirst(),
            "dequeueFirst()"
        );
    }

    @Test
    public void testDequeueAll()
    {
        emptyQueueCheck((NoArgDelegate<List<Integer>>) () -> queue.dequeueAll(),
        "dequeueAll()");

        //add several elements
        Integer[] elements = {1, 2, 3, 4, 5};
        queue.enqueueAllFirst(elements);

        //see if we can retrieve them
        List<Integer> dequeued = queue.dequeueAll();

        equals("number of elements returned from dequeueAll() is incorrect",
                dequeued.size(), elements.length);

        for (int i = 0; i < dequeued.size(); i++)
        {
            equals("Elements returned from dequeueAll() are incorrect",
                    dequeued.get(i), elements[i]);
        }

        //verify the structure is empty now
        isTrue("Queue should be empty after calling dequeueAll()", queue.isEmpty());
    }

    @Test
    public void testSizeAndIsEmpty()
    {
        //an empty queue?
        equals("isEmpty() should be zero for an empty queue",
                0, queue.size());
        isTrue("isEmpty() should return true for an empty queue",
                queue.isEmpty());

        //add a few elements and confirm size
        queue.enqueueFirst(1);
        testSize(1);
        queue.enqueueLast(2);
        testSize(2);

        //confirm not empty
        isFalse("isEmpty() should return false for a queue with elements",
                queue.isEmpty());
    }

    private void testSize(int size)
    {
        equals("size() not updating when adding elements to queue",
                size, queue.size());
    }

    @Test
    public void testClear()
    {
        final int NUM_ELEMENTS = 3;

        //add a few items
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            queue.enqueueFirst(i);
        }

        queue.clear();
        equals("Unexpected size() after removing elements",
                0, queue.size());
    }

    @Test
    public void testIterator()
    {
        //iterator should still work with zero elements
        for (int element : queue)
        {
            fail("Iterator returns an element from an empty stack");
        }

        final int NUM_ELEMENTS = 5;

        //add a few items
        for (int i = 1; i <= NUM_ELEMENTS; i++)
        {
            queue.enqueueFirst(i);
        }

        //verify that we can use an iterator with the for-each loop
        int count = 1;
        for (int element : queue)
        {
            equals("Unexpected element found using an iterator after adding elements",
                    count, element);
            count++;
        }

        //verify that you cannot concurrently alter the structure
        try
        {
            boolean first = true;
            for (int element : queue)
            {
                if (first)
                {
                    queue.dequeueFirst();
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

    private interface SingleArgDelegate<T>
    {
        void execute(T element);
    }

    private interface NoArgDelegate<T>
    {
        T execute();
    }
}
