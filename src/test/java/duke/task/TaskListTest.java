package duke.task;

import duke.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class TaskListTest {
    private TaskList taskList;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(new ArrayList<>());
        System.setOut(new PrintStream(outContent));
    }

    @org.junit.jupiter.api.AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddTask() throws DukeException {
        Task todo = new Todo("read book");
        taskList.addTask(todo);

        assertEquals(1, taskList.getTasks().size());
        assertTrue(outContent.toString().contains("Adding duke.task.Task: read book"));
    }

    @Test
    public void testAddTooManyTasks() throws DukeException {
        for (int i = 0; i < 100; i++) {
            taskList.addTask(new Todo("task" + i));
        }
        assertThrows(TooManyTasksException.class, () -> {
            taskList.addTask(new Todo("overflow task"));
        });
    }

    @Test
    public void testRemoveTask() throws DukeException {
        taskList.addTask(new Todo("write code"));
        taskList.removeTask(0);

        assertEquals(0, taskList.getTasks().size());
        assertTrue(outContent.toString().contains("Removing duke.task.Task: write code"));
    }

    @Test
    public void testRemoveTaskInvalidIndex() {
        assertThrows(TaskNotFoundException.class, () -> {
            taskList.removeTask(0);
        });
    }

    @Test
    public void testMarkTaskDoneAndUndone() throws DukeException {
        Task todo = new Todo("exercise");
        taskList.addTask(todo);

        taskList.markTask(0, true);
        assertTrue(todo.getDone());
        assertTrue(outContent.toString().contains("as done"));

        outContent.reset();
        taskList.markTask(0, false);
        assertFalse(todo.getDone());
        assertTrue(outContent.toString().contains("as undone"));
    }

    @Test
    public void testMarkTaskInvalidIndex() {
        assertThrows(TaskNotFoundException.class, () -> {
            taskList.markTask(5, true);
        });
    }

    @Test
    public void testListTasks() throws DukeException {
        taskList.addTask(new Todo("sleep"));
        outContent.reset();
        taskList.listTasks();

        assertTrue(outContent.toString().contains("1. [T][ ] sleep"));
    }

    @Test
    public void testListTasksEmpty() {
        assertThrows(ListEmptyException.class, () -> taskList.listTasks());
    }

    @Test
    public void testListTasksByDeadlineDueToday() throws DukeException {
        String today = LocalDate.now().toString().replace("-", "/") + " 23:59"; // e.g., 2025/08/23 23:59
        taskList.addTask(new Deadline("submit homework", today));
        outContent.reset();

        taskList.listTasksByDeadline();
        assertTrue(outContent.toString().contains("1. [D][ ] submit homework"));
    }

    @Test
    public void testListTasksByDeadlineNoTasksToday() throws DukeException {
        taskList.addTask(new Deadline("future task", "2099/01/01 10:00"));
        outContent.reset();

        taskList.listTasksByDeadline();
        assertTrue(outContent.toString().contains("Yay! No tasks due today! Yay! :D"));
    }
}

