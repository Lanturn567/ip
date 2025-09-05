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
        Ui.showWelcome(this.name);

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
                    case FIND -> findTask(command);
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
        Ui.showGoodbye();
    }

    public String getResponse(String command) {
        CommandType type = CommandType.fromInput(command);
        String wish = "Someting wong. D:";
        try {
            switch (type) {
            case LIST -> wish = listTasks();
            case FIND -> wish = findTask(command);
            case DUE -> wish = listTasksByDeadline();
            case MARK -> {
                wish = markTask(command, true);
                Save.write(this.tasks.getTasks());
            }
            case UNMARK -> {
                wish = markTask(command, false);
                Save.write(this.tasks.getTasks());
            }
            case REMOVE -> {
                wish = removeTask(command);
                Save.write(this.tasks.getTasks());
            }
            case TODO -> {
                wish =addTodo(command);
                Save.write(this.tasks.getTasks());
            }
            case DEADLINE -> {
                wish = addDeadline(command);
                Save.write(this.tasks.getTasks());
            }
            case EVENT -> {
                wish = addEvent(command);
                Save.write(this.tasks.getTasks());
            }
            case BYE -> {
                wish = Ui.showGoodbye();
            }
            case UNKNOWN -> throw new IncorrectFormatException();
            }
        } catch (DukeException e) {
            wish = e.getMessage();
        }
        return wish;
    }

    /**
     * Finds all tasks with descriptions containing the given search string.
     *
     * @param command the user command, expected format: {@code "find <keyword>"}
     * @throws DukeException if the task list is empty or if the command format is invalid
     */
    public String findTask(String command) throws DukeException {
        StringBuilder wish = new StringBuilder();
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }

        String[] elems = command.trim().split("\\s+", 2);
        if (elems.length < 2 || elems[1].isEmpty()) {
            throw new IncorrectFormatException("Format is \"find <keyword>\"... D:");
        }

        String keyword = elems[1];
        // System.out.println("Searching for tasks...");
        int count = 0;
        for (Task t : tasklist) {
            if (t.getName().contains(keyword)) {
                String curr = ++count + ". " + t + "\n";
                wish.append(curr);
            }
        }
        if (count == 0) {
            return "No tasks found... D:";
        } else {
            return wish.toString();
        }
    }

    /**
     * Lists all tasks with deadlines due today.
     *
     * @throws DukeException if the task list is empty
     */
    public String listTasksByDeadline() throws DukeException {
        StringBuilder wish = new StringBuilder();
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String formatted = today.format(formatter);
        wish.append("Today is ").append(formatted).append("! Here are your due tasks! :D\n");
        int count = 0;
        for (Task task : tasklist) {
            if (task instanceof Deadline d) {
                if (!d.getDone() && d.getDeadline().toLocalDate().equals(today)) {
                    wish.append(++count + ". " + d + "\n");
                }
            }
        }
        if (count == 0) {
            return "Yay! No tasks due today! Yay! :D";
        } else {
            return wish.toString();
        }
    }

    /**
     * Lists all tasks currently stored in the task list.
     *
     * @throws DukeException if the task list is empty
     */
    public String listTasks() throws DukeException {
        StringBuilder wish = new StringBuilder();
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        for (int i = 0; i < tasklist.size(); i++) {
            wish.append((i + 1) + ". " + tasklist.get(i) + "\n");
        }
        return wish.toString();
    }

    /**
     * Marks or unmarks a task as done based on user input.
     *
     * @param command the user command specifying the task number
     * @param done    {@code true} to mark as done, {@code false} to unmark
     * @throws DukeException if the task list is empty, the format is incorrect,
     *                       or the task number is invalid
     */
    public String markTask(String command, boolean done) throws DukeException {
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
            return "Ok! Marking Task: " + curr.getName() + " as done!\n";
        } else {
            curr.markUndone();
            return "Ok! Marking Task: " + curr.getName() + " as undone!\n";
        }
    }

    /**
     * Adds a {@link Todo} task based on user input.
     *
     * @param command the user command containing the description
     * @throws DukeException if the description is empty
     */
    public String addTodo(String command) throws DukeException {
        String desc = command.substring(command.indexOf(" ") + 1);
        if (desc.isEmpty()) {
            throw new DescriptionEmptyException();
        }
        return addTask(new Todo(desc));
    }

    /**
     * Adds a {@link Deadline} task based on user input.
     *
     * @param command the user command containing the description and deadline
     * @throws DukeException if the format is invalid, the description is empty,
     *                       or the timestamp is empty
     */
    public String addDeadline(String command) throws DukeException {
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

        return addTask(new Deadline(desc, by));
    }

    /**
     * Adds an {@link Event} task based on user input.
     *
     * @param command the user command containing the description,
     *                start time, and end time
     * @throws DukeException if the format is invalid, the description is empty,
     *                       or timestamps are missing
     */
    public String addEvent(String command) throws DukeException {
        if (!command.contains("/from") || !command.contains("/to")) {
            throw new IncorrectFormatException();
        }

        int firstSpace = command.indexOf(" ");
        int fromIndex = command.indexOf(" /from ");
        int toIndex = command.indexOf(" /to ");

        if (firstSpace == -1 || fromIndex == -1 || toIndex == -1 || fromIndex <= firstSpace || toIndex <= fromIndex) {
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

        return addTask(new Event(desc, start, end));
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the task to be added
     * @throws DukeException if the maximum number of tasks (100) is exceeded
     */
    public String addTask(Task task) throws DukeException {
        StringBuilder wish = new StringBuilder();
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.size() >= 100) {
            throw new TooManyTasksException();
        }
        tasklist.add(task);
        wish.append("Adding Task: " + task.getName() + " to list! :D\n");
        wish.append("Now there are " + tasklist.size() + " tasks!\n");
        return wish.toString();
    }

    /**
     * Removes a task from the task list based on user input.
     *
     * @param command the user command specifying the task number
     * @throws DukeException if the task list is empty, the format is incorrect,
     *                       or the task number is invalid
     */
    public String removeTask(String command) throws DukeException {
        StringBuilder wish = new StringBuilder();
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
        wish.append("Removing Task: " + task.getName() + " from list! D:\n");
        wish.append("Now there are " + tasklist.size() + " tasks!\n");
        return wish.toString();
    }
}
