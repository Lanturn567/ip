package duke.util;

import duke.exception.*;
import duke.task.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ChatbotTest {
    private Chatbot chatbot;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        chatbot = new Chatbot("TestBot");
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddTodo() throws DukeException {
        chatbot.addTodo("todo read book");
        assertTrue(outContent.toString().contains("Adding duke.task.Task: read book"));
    }

    @Test
    public void testAddTodoEmptyDescription() {
        assertThrows(DescriptionEmptyException.class, () -> chatbot.addTodo("todo "));
    }

    @Test
    public void testAddDeadlineValid() throws DukeException {
        String today = LocalDate.now().toString().replace("-", "/") + " 23:59";
        chatbot.addDeadline("deadline submit report /by " + today);
        assertTrue(outContent.toString().contains("submit report"));
    }

    @Test
    public void testAddDeadlineMissingBy() {
        assertThrows(IncorrectFormatException.class, () -> chatbot.addDeadline("deadline report"));
    }

    @Test
    public void testAddEventValid() throws DukeException {
        chatbot.addEvent("event meeting /from 2025/08/23 10:00 /to 2025/08/23 12:00");
        assertTrue(outContent.toString().contains("meeting"));
    }

    @Test
    public void testAddEventMissingFromTo() {
        assertThrows(IncorrectFormatException.class, () -> chatbot.addEvent("event party"));
    }

    @Test
    public void testMarkTaskValid() throws DukeException {
        chatbot.addTodo("todo code");
        outContent.reset();
        chatbot.markTask("mark 1", true);
        assertTrue(outContent.toString().contains("as done"));
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
        outContent.reset();
        chatbot.removeTask("remove 1");
        assertTrue(outContent.toString().contains("Removing duke.task.Task: sleep"));
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
        outContent.reset();
        chatbot.listTasks();
        assertTrue(outContent.toString().contains("[T][ ] test"));
    }

    @Test
    public void testListTasksByDeadlineToday() throws DukeException {
        String today = LocalDate.now().toString().replace("-", "/") + " 23:59";
        chatbot.addDeadline("deadline homework /by " + today);
        outContent.reset();
        chatbot.listTasksByDeadline();
        assertTrue(outContent.toString().contains("homework"));
    }

    @Test
    public void testListTasksByDeadlineNoTasksToday() throws DukeException {
        chatbot.addDeadline("deadline future /by 2099/01/01 10:00");
        outContent.reset();
        chatbot.listTasksByDeadline();
        assertTrue(outContent.toString().contains("Yay! No tasks due today! Yay!"));
    }
}
