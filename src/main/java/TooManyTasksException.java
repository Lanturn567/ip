public class TooManyTasksException extends DukeException{
    private static String msg = "Boo... There are too many tasks in the list... D:";

    public TooManyTasksException() {
        super(TooManyTasksException.msg);
    }
}
