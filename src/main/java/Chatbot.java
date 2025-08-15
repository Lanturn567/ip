import java.util.Scanner;
import java.util.ArrayList;

public class Chatbot {
    private String name;
    private ArrayList<Task> tasks = new ArrayList<>();

    public Chatbot(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println("--------------------------------------");
        System.out.println("Hello, I am " + this.name + "! :D");
        System.out.println("What can I do for you today?");
        System.out.println("--------------------------------------");

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (!command.equals("bye")) {
            try {
                if (command.equals("list")) {
                    listTasks();
                } else if (command.startsWith("mark")) {
                    markTask(command, true);
                } else if (command.startsWith("unmark")) {
                    markTask(command, false);
                } else if (command.startsWith("remove")) {
                    removeTask(command);
                } else if (command.startsWith("todo")) {
                    addTodo(command);
                } else if (command.startsWith("deadline")) {
                    addDeadline(command);
                } else if (command.startsWith("event")) {
                    addEvent(command);
                } else {
                    throw new IncorrectFormatException();
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

    private void listTasks() throws DukeException {
        if (tasks.isEmpty()) {
            throw new ListEmptyException();
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private void markTask(String command, boolean done) throws DukeException {
        int num = Integer.parseInt(command.split(" ")[1]) - 1;
        if (num < 0 || num >= tasks.size()) {
            throw new TaskNotFoundException();
        }
        Task curr = tasks.get(num);
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
        int firstSpace = command.indexOf(" ");
        int firstSlash = command.indexOf(" /by ");
        String desc = command.substring(firstSpace + 1, firstSlash);
        if (desc.isEmpty()) {
            throw new DescriptionEmptyException();
        }
        String by = command.substring(firstSlash + 5);
        if (by.isEmpty()) {
            throw new TimestampEmptyException();
        }
        addTask(new Deadline(desc, by));
    }

    private void addEvent(String command) throws DukeException {
        int firstSpace = command.indexOf(" ");
        int fromSlash = command.indexOf(" /from ");
        int toSlash = command.indexOf(" /to ");
        String desc = command.substring(firstSpace + 1, fromSlash);
        if (desc.isEmpty()) {
            throw new DescriptionEmptyException();
        }
        String start = command.substring(fromSlash + 7, toSlash);
        String end = command.substring(toSlash + 5);
        if (start.isEmpty() || end.isEmpty()) {
            throw new TimestampEmptyException();
        }
        addTask(new Event(desc, start, end));
    }

    private void addTask(Task task) throws DukeException {
        if (tasks.size() >= 100) {
            throw new TooManyTasksException();
        }
        tasks.add(task); // Add to the end of the ArrayList
        System.out.println("Adding Task: " + task.getName() + " to list! :D");
        System.out.println("Now there are " + tasks.size() + " tasks!");
    }

    private void removeTask(String command) throws DukeException {
        int num = Integer.parseInt(command.split(" ")[1]) - 1;
        if (num < 0 || num >= this.tasks.size()) {
            throw new TaskNotFoundException();
        }
        Task task = this.tasks.get(num);
        tasks.remove(task);
        System.out.println("Removing Task: " + task.getName() + " from list! D:");
        System.out.println("Now there are " + tasks.size() + " tasks!");
    }
}
