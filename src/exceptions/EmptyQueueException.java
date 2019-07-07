package exceptions;

/**
 * This is my EmptyQueueException that returns exception message
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class EmptyQueueException extends RuntimeException {

    private String msg;

    /**
     * This is my constructor
     * @param msg is the message to be displayed when an exception is thrown
     */
    public EmptyQueueException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
