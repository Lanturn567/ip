package duke.exception;

/**
 * Exception thrown when the description of a task is empty.
 */
public class DescriptionEmptyException extends DukeException {

    /**
     * Default error message when a task description is empty.
     */
    private static final String msg = "Boo... The description of a task cannot be empty...";

    /**
     * Creates a {@code DescriptionEmptyException} with the default error message.
     */
    public DescriptionEmptyException() {
        super(DescriptionEmptyException.msg);
    }

    /**
     * Creates a {@code DescriptionEmptyException} with a custom error message.
     *
     * @param msg the custom error message
     */
    public DescriptionEmptyException(String msg) {
        super(msg);
    }
}

