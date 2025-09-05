package duke.exception;

/**
 * Exception thrown when user input is not in the correct format.
 */
public class IncorrectFormatException extends DukeException {

    /**
     * Default exception message for incorrect format.
     */
    private static final String msg = "Boo... I'm sorry, but I do not know what that means D:";

    /**
     * Default constructor for {@code IncorrectFormatException}.
     */
    public IncorrectFormatException() {
        super(IncorrectFormatException.msg);
    }

    /**
     * Constructor with custom message for {@code IncorrectFormatException}.
     * @param msg the custom error message.
     */
    public IncorrectFormatException(String msg) {
        super(msg);
    }
}
