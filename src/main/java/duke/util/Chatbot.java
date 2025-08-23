package duke.util;

import duke.exception.*;
import duke.task.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Represents the main chatbot that interacts with the user.
 * <p>
 * The {@code Chatbot} manages a {@link TaskList}, processes user commands,
 * executes them, and saves the task list to persistent storage.
 */
public class Chatbot {

    /**
     * The name of the chatbot.
     */
    private String name;

    /**
     * The list of tasks managed by the chatbot.
     */
    private TaskList tasks = new TaskList();

    /**
     * Creates a new {@code Chatbot} with the given name.
     * Loads any previously saved tasks if available.
     *
     * @param name the name of the chatbot
     */
    public Chatbot(String name) {
        this.name = name;
        try {
            Save.read(this.tasks.getTasks());
            System.out.println("Welcome back!!! :D");
        } catch (FileNotFoundException e) {
            System.out.println("Welcome, new user! :D");
        }
    }

    /**
     * Starts the chatbot, processes user commands,
     * and responds until the user exits.
     */
    public void run() {
        UI.showWelcome(this.name);

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (true) {
            CommandType type = CommandType.fromInput(command);
            if (type == CommandType.BYE) {
                break;
            }

            try {
                switch (type) {
                    case LIST -> listTasks();
                    case FIND -> find(command);
                    case DUE -> listTasksByDeadline();
                    case MARK -> {
                        markTask(command, true);
                        Save.write(this.tasks.getTasks());
                    }
                    case UNMARK -> {
                        markTask(command, false);
                        Save.write(this.tasks.getTasks());
                    }
                    case REMOVE -> {
                        removeTask(command);
                        Save.write(this.tasks.getTasks());
                    }
                    case TODO -> {
                        addTodo(command);
                        Save.write(this.tasks.getTasks());
                    }
                    case DEADLINE -> {
                        addDeadline(command);
                        Save.write(this.tasks.getTasks());
                    }
                    case EVENT -> {
                        addEvent(command);
                        Save.write(this.tasks.getTasks());
                    }
                    case UNKNOWN -> throw new IncorrectFormatException();
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("--------------------------------------");
                command = scanner.nextLine();
            }
        }
        UI.showGoodbye();
    }

    /**
     * Finds all tasks with descriptions containing the given search string.
     *
     * @param command the user command, expected format: {@code "find <keyword>"}
     * @throws DukeException if the task list is empty or if the command format is invalid
     */
    public void find(String command) throws DukeException {
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }

        String[] elems = command.trim().split("\\s+", 2);
        if (elems.length < 2 || elems[1].isEmpty()) {
            throw new IncorrectFormatException("Format is \"find <keyword>\"... D:");
        }

        String keyword = elems[1];
        System.out.println("Finding all tasks...");
        int count = 0;
        for (Task t : tasklist) {
            if (t.getName().contains(keyword)) {
                System.out.println(++count + ". " + t);
            }
        }
        if (count == 0) {
            System.out.println("No tasks found... D:");
        }
    }

    /**
     * Lists all tasks with deadlines due today.
     *
     * @throws DukeException if the task list is empty
     */
    public void listTasksByDeadline() throws DukeException {
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String formatted = today.format(formatter);
        System.out.println("Today is " + formatted + "! Here are your due tasks! :D");
        int count = 0;
        for (Task task : tasklist) {
            if (task instanceof Deadline d) {
                if (!d.getDone() && d.getDeadline().toLocalDate().equals(today)) {
                    count++;
                    System.out.println(count + ". " + d);
                }
            }
        }
        if (count == 0) {
            System.out.println("Yay! No tasks due today! Yay! :D");
        }
    }

    /**
     * Lists all tasks currently stored in the task list.
     *
     * @throws DukeException if the task list is empty
     */
    public void listTasks() throws DukeException {
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        for (int i = 0; i < tasklist.size(); i++) {
            System.out.println((i + 1) + ". " + tasklist.get(i));
        }
    }

    /**
     * Marks or unmarks a task as done based on user input.
     *
     * @param command the user command specifying the task number
     * @param done    {@code true} to mark as done, {@code false} to unmark
     * @throws DukeException if the task list is empty, the format is incorrect,
     *                       or the task number is invalid
     */
    public void markTask(String command, boolean done) throws DukeException {
        String[] parts = command.trim().split("\\s+");
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        if (parts.length < 2) {
            throw new IncorrectFormatException("Boo... Format is \"mark/unmark <number> \"... D:");
        }
        int num;
        try {
            num = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectFormatException("Boo... Format is \"mark/unmark <number> \"... D:");
        }
        if (num < 0 || num >= tasklist.size()) {
            throw new TaskNotFoundException();
        }
        Task curr = tasklist.get(num);
        if (done) {
            curr.markDone();
            System.out.println("Ok! Marking Task: " + curr.getName() + " as done!");
        } else {
            curr.markUndone();
            System.out.println("Ok! Marking Task: " + curr.getName() + " as undone!");
        }
    }

    /**
     * Adds a {@link Todo} task based on user input.
     *
     * @param command the user command containing the description
     * @throws DukeException if the description is empty
     */
    public void addTodo(String command) throws DukeException {
        String desc = command.substring(command.indexOf(" ") + 1);
        if (desc.isEmpty()) {
            throw new DescriptionEmptyException();
        }
        addTask(new Todo(desc));
    }

    /**
     * Adds a {@link Deadline} task based on user input.
     *
     * @param command the user command containing the description and deadline
     * @throws DukeException if the format is invalid, the description is empty,
     *                       or the timestamp is empty
     */
    public void addDeadline(String command) throws DukeException {
        if (!command.contains("/by")) {
            throw new IncorrectFormatException();
        }

        int firstSpace = command.indexOf(" ");
        int byIndex = command.indexOf(" /by ");

        if (firstSpace == -1 || byIndex == -1 || byIndex <= firstSpace) {
            throw new IncorrectFormatException();
        }

        String desc = command.substring(firstSpace + 1, byIndex).trim();
        if (desc.isEmpty()) {
            throw new DescriptionEmptyException();
        }

        String by = command.substring(byIndex + 5).trim();
        if (by.isEmpty()) {
            throw new TimestampEmptyException();
        }

        addTask(new Deadline(desc, by));
    }

    /**
     * Adds an {@link Event} task based on user input.
     *
     * @param command the user command containing the description,
     *                start time, and end time
     * @throws DukeException if the format is invalid, the description is empty,
     *                       or timestamps are missing
     */
    public void addEvent(String command) throws DukeException {
        if (!command.contains("/from") || !command.contains("/to")) {
            throw new IncorrectFormatException();
        }

        int firstSpace = command.indexOf(" ");
        int fromIndex = command.indexOf(" /from ");
        int toIndex = command.indexOf(" /to ");

        if (firstSpace == -1 || fromIndex == -1 || toIndex == -1
                || fromIndex <= firstSpace || toIndex <= fromIndex) {
            throw new IncorrectFormatException();
        }

        String desc = command.substring(firstSpace + 1, fromIndex).trim();
        if (desc.isEmpty()) {
            throw new DescriptionEmptyException();
        }

        String start = command.substring(fromIndex + 7, toIndex).trim();
        String end = command.substring(toIndex + 5).trim();
        if (start.isEmpty() || end.isEmpty()) {
            throw new TimestampEmptyException();
        }

        addTask(new Event(desc, start, end));
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the task to be added
     * @throws DukeException if the maximum number of tasks (100) is exceeded
     */
    public void addTask(Task task) throws DukeException {
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.size() >= 100) {
            throw new TooManyTasksException();
        }
        tasklist.add(task);
        System.out.println("Adding Task: " + task.getName() + " to list! :D");
        System.out.println("Now there are " + tasklist.size() + " tasks!");
    }

    /**
     * Removes a task from the task list based on user input.
     *
     * @param command the user command specifying the task number
     * @throws DukeException if the task list is empty, the format is incorrect,
     *                       or the task number is invalid
     */
    public void removeTask(String command) throws DukeException {
        String[] parts = command.trim().split("\\s+");
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        if (parts.length < 2) {
            throw new IncorrectFormatException("Boo... Format is \"remove <number> \"... D:");
        }
        int num;
        try {
            num = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectFormatException("Boo... Format is \"remove <number> \"... D:");
        }
        if (num < 0 || num >= tasklist.size()) {
            throw new TaskNotFoundException();
        }
        Task task = tasklist.get(num);
        tasklist.remove(task);
        System.out.println("Removing Task: " + task.getName() + " from list! D:");
        System.out.println("Now there are " + tasklist.size() + " tasks!");
    }
}
