public class ListEmptyException extends DukeException{
    private static String msg = "The list is currently empty... Add some tasks! :D";

    public ListEmptyException() {
        super(ListEmptyException.msg);
    }
}
