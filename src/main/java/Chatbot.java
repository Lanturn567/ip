import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Chatbot {
    private String name;
    private TaskList tasks = new TaskList();

    public Chatbot(String name) {
        this.name = name;
        try {
            Save.read(this.tasks.getTasks());
            System.out.println("Welcome back!!! :D");
        } catch (FileNotFoundException e) {
            System.out.println("Welcome, new user! :D");
        }
    }

    public void run() {
        System.out.println("--------------------------------------");
        System.out.println("Hello, I am " + this.name + "! :D");
        System.out.println("What can I do for you today?");
        System.out.println("--------------------------------------");

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (true) {
            CommandType type = CommandType.fromInput(command);
            if (type == CommandType.BYE) {
                break;
            }

            try {
                switch (type) {
                    case LIST -> {
                        listTasks();
                    }
                    case DUE -> {
                        listTasksByDeadline();
                    }
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
                    case UNKNOWN -> {
                        throw new IncorrectFormatException();
                    }
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("--------------------------------------");
                command = scanner.nextLine();
            }
        }

        System.out.println("Goodbye! Hope to see you again! :D");
        System.out.println("--------------------------------------");
    }

    // --- Abstracted methods ---

    private void listTasksByDeadline() throws DukeException {
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        LocalDate today = LocalDate.now();
        int count = 0;
        for (Task task : tasklist) {
            if (task instanceof Deadline) {
                Deadline d = (Deadline) task;
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

    private void listTasks() throws DukeException {
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        for (int i = 0; i < tasklist.size(); i++) {
            System.out.println((i + 1) + ". " + tasklist.get(i));
        }
    }

    private void markTask(String command, boolean done) throws DukeException {
        String[] parts = command.trim().split("\\s+");
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        // Check if there is a second part
        if (parts.length < 2) {
            throw new IncorrectFormatException("Boo... Format is \"mark/unmark <number> \"... D:");
        }
        int num;
        try {
            num = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectFormatException("Boo... Format is \"mark/unmark <number> \"... D:");
        }
        // Check if number is within range
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

    private void addTodo(String command) throws DukeException {
        String desc = command.substring(command.indexOf(" ") + 1);
        if (desc.isEmpty()) {
            throw new DescriptionEmptyException();
        }
        addTask(new Todo(desc));
    }

    private void addDeadline(String command) throws DukeException {
        if (!command.contains("/by")) {
            throw new IncorrectFormatException();
        }

        int firstSpace = command.indexOf(" ");
        int byIndex = command.indexOf(" /by ");

        // Check indices
        if (firstSpace == -1 || byIndex == -1 || byIndex <= firstSpace) {
            throw new IncorrectFormatException();
        }

        String desc = command.substring(firstSpace + 1, byIndex).trim();
        if (desc.isEmpty()) {
            throw new DescriptionEmptyException();
        }

        String by = command.substring(byIndex + 5).trim(); // everything after "/by "
        if (by.isEmpty()) {
            throw new TimestampEmptyException();
        }

        addTask(new Deadline(desc, by));
    }

    private void addEvent(String command) throws DukeException {
        if (!command.contains("/from") || !command.contains("/to")) {
            throw new IncorrectFormatException();
        }

        int firstSpace = command.indexOf(" ");
        int fromIndex = command.indexOf(" /from ");
        int toIndex = command.indexOf(" /to ");

        // Check indices
        if (firstSpace == -1 || fromIndex == -1 || toIndex == -1
                || fromIndex <= firstSpace || toIndex <= fromIndex) {
            throw new IncorrectFormatException();
        }

        String desc = command.substring(firstSpace + 1, fromIndex).trim();
        if (desc.isEmpty()) {
            throw new DescriptionEmptyException();
        }

        String start = command.substring(fromIndex + 7, toIndex).trim(); // after "/from "
        String end = command.substring(toIndex + 5).trim();               // after "/to "
        if (start.isEmpty() || end.isEmpty()) {
            throw new TimestampEmptyException();
        }

        addTask(new Event(desc, start, end));
    }

    private void addTask(Task task) throws DukeException {
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.size() >= 100) {
            throw new TooManyTasksException();
        }
        tasklist.add(task); // Add to the end of the ArrayList
        System.out.println("Adding Task: " + task.getName() + " to list! :D");
        System.out.println("Now there are " + tasklist.size() + " tasks!");
    }

    private void removeTask(String command) throws DukeException {
        String[] parts = command.trim().split("\\s+");
        ArrayList<Task> tasklist = this.tasks.getTasks();
        if (tasklist.isEmpty()) {
            throw new ListEmptyException();
        }
        // Check if there is a second part
        if (parts.length < 2) {
            throw new IncorrectFormatException("Boo... Format is \"remove <number> \"... D:");
        }
        int num;
        try {
            num = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new IncorrectFormatException("Boo... Format is \"remove <number> \"... D:");
        }
        // Check if number is within range
        if (num < 0 || num >= tasklist.size()) {
            throw new TaskNotFoundException();
        }
        Task task = tasklist.get(num);
        tasklist.remove(task);
        System.out.println("Removing Task: " + task.getName() + " from list! D:");
        System.out.println("Now there are " + tasklist.size() + " tasks!");
    }
}