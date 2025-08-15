public class IncorrectFormatException extends DukeException {
    private static String msg = "Boo... I'm sorry, but I do not know what that means D:";

    public IncorrectFormatException() {
        super(IncorrectFormatException.msg);
    }

    public IncorrectFormatException(String msg) {
        super(msg);
    }
}
