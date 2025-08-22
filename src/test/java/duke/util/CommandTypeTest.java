package duke.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTypeTest {

    @Test
    public void testFromInputExactMatch() {
        assertEquals(CommandType.LIST, CommandType.fromInput("list"));
        assertEquals(CommandType.BYE, CommandType.fromInput("bye"));
        assertEquals(CommandType.DUE, CommandType.fromInput("due"));
    }

    @Test
    public void testFromInputStartsWith() {
        assertEquals(CommandType.MARK, CommandType.fromInput("mark 1"));
        assertEquals(CommandType.UNMARK, CommandType.fromInput("unmark 2"));
        assertEquals(CommandType.REMOVE, CommandType.fromInput("remove 3"));
        assertEquals(CommandType.TODO, CommandType.fromInput("todo read book"));
        assertEquals(CommandType.DEADLINE, CommandType.fromInput("deadline project /by 2025/08/30 23:59"));
        assertEquals(CommandType.EVENT, CommandType.fromInput("event party /from 2025/08/30 18:00 /to 2025/08/30 22:00"));
    }

    @Test
    public void testFromInputCaseInsensitive() {
        assertEquals(CommandType.LIST, CommandType.fromInput("LIST"));
        assertEquals(CommandType.BYE, CommandType.fromInput("ByE"));
        assertEquals(CommandType.TODO, CommandType.fromInput("ToDo read"));
    }

    @Test
    public void testFromInputUnknown() {
        assertEquals(CommandType.UNKNOWN, CommandType.fromInput("hello world"));
        assertEquals(CommandType.UNKNOWN, CommandType.fromInput("foobar"));
        assertEquals(CommandType.UNKNOWN, CommandType.fromInput(""));
    }
}
