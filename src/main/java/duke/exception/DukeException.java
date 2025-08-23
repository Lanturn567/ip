package duke.exception;

/** Exception thrown when something goes wrong related to the Chatbot
 *
  */
public class DukeException extends Exception{

    /**
     * Creates a {@code DescriptionEmptyException} with the default error message.
     * @param msg the custom error message.
     */
    public DukeException(String msg) {
        super(msg);
    }
}
