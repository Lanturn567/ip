package duke.task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoTest {

    @Test
    public void testToString() {
        Todo todo = new Todo("read book");
        // assuming Task.toString() returns "[ ] read book" when not marked as done
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void testMarking() {
        Todo todo = new Todo("write code");
        todo.markDone();
        assertEquals("[T][X] write code", todo.toString());
        todo.markUndone();
        assertEquals("[T][ ] write code", todo.toString());
    }
}

