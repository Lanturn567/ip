package duke.util;

import duke.exception.*;
import duke.task.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ChatbotTest {
    private Chatbot chatbot;

    @BeforeEach
    public void setUp() {
        chatbot = new Chatbot("TestBot");
    }

    @Test
    public void testAddTodo() throws DukeException {
        String result = chatbot.addTodo("todo read book");
        assertTrue(result.contains("Adding Task: read book"));
    }

    @Test
    public void testAddTodoEmptyDescription() {
        assertThrows(DescriptionEmptyException.class, () -> chatbot.addTodo("todo "));
    }

    @Test
    public void testAddDeadlineValid() throws DukeException {
        String today = LocalDate.now().toString().replace("-", "/") + " 23:59";
        String result = chatbot.addDeadline("deadline submit report /by " + today);
        assertTrue(result.contains("submit report"));
    }

    @Test
    public void testAddDeadlineMissingBy() {
        assertThrows(IncorrectFormatException.class, () -> chatbot.addDeadline("deadline report"));
    }

    @Test
    public void testAddEventValid() throws DukeException {
        String result = chatbot.addEvent("event meeting /from 2025/08/23 10:00 /to 2025/08/23 12:00");
        assertTrue(result.contains("meeting"));
    }

    @Test
    public void testAddEventMissingFromTo() {
        assertThrows(IncorrectFormatException.class, () -> chatbot.addEvent("event party"));
    }

    @Test
    public void testMarkTaskValid() throws DukeException {
        chatbot.addTodo("todo code");
        String result = chatbot.markTask("mark 1", true);
        assertTrue(result.contains("as done"));
    }

    @Test
    public void testMarkTaskInvalidIndex() throws DukeException {
        chatbot.addTodo("todo homework");
        assertThrows(TaskNotFoundException.class, () -> chatbot.markTask("mark 5", true));
    }

    @Test
    public void testMarkTaskNoIndex() throws DukeException {
        chatbot.addTodo("todo homework");
        assertThrows(IncorrectFormatException.class, () -> chatbot.markTask("mark", true));
    }

    @Test
    public void testRemoveTaskValid() throws DukeException {
        chatbot.addTodo("todo sleep");
        String result = chatbot.removeTask("remove 1");
        assertTrue(result.contains("Removing Task: sleep"));
    }

    @Test
    public void testRemoveTaskInvalidIndex() throws DukeException {
        chatbot.addTodo("todo homework");
        assertThrows(TaskNotFoundException.class, () -> chatbot.removeTask("remove 10"));
    }

    @Test
    public void testListTasksEmpty() {
        assertThrows(ListEmptyException.class, () -> chatbot.listTasks());
    }

    @Test
    public void testListTasksNonEmpty() throws DukeException {
        chatbot.addTodo("todo test");
        String result = chatbot.listTasks();
        assertTrue(result.contains("[T][ ] test"));
    }

    @Test
    public void testListTasksByDeadlineToday() throws DukeException {
        String today = LocalDate.now().toString().replace("-", "/") + " 23:59";
        chatbot.addDeadline("deadline homework /by " + today);
        String result = chatbot.listTasksByDeadline();
        assertTrue(result.contains("homework"));
    }

    @Test
    public void testListTasksByDeadlineNoTasksToday() throws DukeException {
        chatbot.addDeadline("deadline future /by 2099/01/01 10:00");
        String result = chatbot.listTasksByDeadline();
        assertTrue(result.contains("Yay! No tasks due today! Yay!"));
    }
}
