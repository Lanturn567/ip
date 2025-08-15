public class TimestampEmptyException extends DukeException{
    private static String msg = "Boo... The timestamp of a task cannot be empty...";

    public TimestampEmptyException() {
        super(TimestampEmptyException.msg);
    }
}
