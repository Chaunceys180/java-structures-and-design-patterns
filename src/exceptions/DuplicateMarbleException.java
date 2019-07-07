package exceptions;

/**
 * This class is a custom exception for duplicate bag being
 * added in incorrectly
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class DuplicateMarbleException extends RuntimeException {

    private String msg;

    /**
     * @param msg is the message to be displayed when an exception is thrown
     */
    public DuplicateMarbleException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}