package duke.exception;

/**
 * Exception thrown when trying to process a Tasklist which is empty.
 */
public class ListEmptyException extends DukeException {
    /**
     * Default error message when trying to process an empty tasklist.
     */
    private static String msg = "The list is currently empty... Add some tasks! :D";

    /**
     * Default constructor for a {@code ListEmptyException}
     */
    public ListEmptyException() {
        super(ListEmptyException.msg);
    }

    /**
     * Constructor with custom message for a {@code ListEmptyException}
     * @param msg the custom error message
     */
    public ListEmptyException(String msg) {
        super(msg);
    }
}
