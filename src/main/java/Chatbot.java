import java.util.Scanner;

public class Chatbot {
    private String name;
    private Task[] tasks = new Task[100];
    private int count = 0;

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
            if (command.equals("list")) {
                listTasks();
            } else if (command.startsWith("mark")) {
                markTask(command, true);
            } else if (command.startsWith("unmark")) {
                markTask(command, false);
            } else if (command.startsWith("todo")) {
                addTodo(command);
            } else if (command.startsWith("deadline")) {
                addDeadline(command);
            } else if (command.startsWith("event")) {
                addEvent(command);
            } else {
                System.out.println("Invalid format specified... D:");
            }
            System.out.println("--------------------------------------");
            command = scanner.nextLine();
        }

        System.out.println("Goodbye! Hope to see you again! :D");
        System.out.println("--------------------------------------");
    }

    // --- Abstracted methods ---

    private void listTasks() {
        for (int i = 0; i < this.count; i++) {
            System.out.println((i + 1) + ". " + this.tasks[i]);
        }
    }

    private void markTask(String command, boolean done) {
        int num = Integer.parseInt(command.split(" ")[1]) - 1;
        Task curr = this.tasks[num];
        if (done) {
            curr.markDone();
            System.out.println("Ok! Marking Task: " + curr.getName() + " as done!");
        } else {
            curr.markUndone();
            System.out.println("Ok! Marking Task: " + curr.getName() + " as undone!");
        }
    }

    private void addTodo(String command) {
        String desc = command.substring(command.indexOf(" ") + 1);
        addTask(new Todo(desc));
    }

    private void addDeadline(String command) {
        int firstSpace = command.indexOf(" ");
        int firstSlash = command.indexOf(" /by ");
        String desc = command.substring(firstSpace + 1, firstSlash);
        String by = command.substring(firstSlash + 5);
        addTask(new Deadline(desc, by));
    }

    private void addEvent(String command) {
        int firstSpace = command.indexOf(" ");
        int fromSlash = command.indexOf(" /from ");
        int toSlash = command.indexOf(" /to ");
        String desc = command.substring(firstSpace + 1, fromSlash);
        String start = command.substring(fromSlash + 7, toSlash);
        String end = command.substring(toSlash + 5);
        addTask(new Event(desc, start, end));
    }

    private void addTask(Task task) {
        this.tasks[this.count] = task;
        this.count++;
        System.out.println("Adding Task: " + task.getName() + " to list! :D");
        System.out.println("Now there are " + this.count + " tasks!");
    }
}
