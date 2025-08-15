public class DescriptionEmptyException extends DukeException{
    private static String msg = "Boo... The description of a task cannot be empty...";

    public DescriptionEmptyException() {
        super(DescriptionEmptyException.msg);
    }
}
