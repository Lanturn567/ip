package duke.exception;

public class ListEmptyException extends DukeException {
    private static String msg = "The list is currently empty... Add some tasks! :D";

    public ListEmptyException() {
        super(ListEmptyException.msg);
    }

    public ListEmptyException(String msg) {
        super(msg);
    }
}
