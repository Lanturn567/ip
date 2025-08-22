package duke.exception;

public class TaskNotFoundException extends DukeException {
    private static String msg = "Boo... I'm sorry, but that task is not in the list... D:";

    public TaskNotFoundException() {
        super(TaskNotFoundException.msg);
    }

    public TaskNotFoundException(String msg) {
        super(msg);
    }
}
