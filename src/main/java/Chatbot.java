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
        System.out.println("Hello, I am " + Lanturn.name + "! :D");
        System.out.println("What can I do for you today?");
        System.out.println("--------------------------------------");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (!command.equals("bye")) {
            if (command.equals("list")) {
                for (int i = 0; i < this.count; i++) {
                    System.out.println(i + 1 + ". " + this.tasks[i]);
                }
            } else if (command.startsWith("mark")) {
                int num = Integer.parseInt(command.split(" ")[1]) - 1;
                Task curr = this.tasks[num];
                curr.markDone();
                System.out.println("Ok! Marking Task: " + curr.getName() + " as done!");
            } else if (command.startsWith("unmark")) {
                int num = Integer.parseInt(command.split(" ")[1]) - 1;
                Task curr = this.tasks[num];
                curr.markUndone();
                System.out.println("Ok! Marking Task: " + curr.getName() + " as undone!");
            } else if (command.startsWith("todo")) {
                int firstSpace = command.indexOf(" ");
                String desc = command.substring(firstSpace + 1);
                Todo task = new Todo(desc);
                this.tasks[this.count] = task;
                this.count++;
                System.out.println("Adding Task: " + task.getName() + " to list! :D");
                System.out.println("Now there are " + this.count + " tasks!");
            } else if (command.startsWith("deadline")) {
                int firstSpace = command.indexOf(" ");
                int firstSlash = command.indexOf(" /by ");
                String desc = command.substring(firstSpace + 1, firstSlash);
                String start = command.substring(firstSlash + 5);
                Deadline task = new Deadline(desc, start);
                this.tasks[this.count] = task;
                this.count++;
                System.out.println("Adding Task: " + task.getName() + " to list! :D");
                System.out.println("Now there are " + this.count + " tasks!");
            } else if (command.startsWith("event")) {
                int firstSpace = command.indexOf(" ");
                int firstSlash = command.indexOf(" /from ");
                int secondSlash = command.indexOf(" /to ");
                String desc = command.substring(firstSpace + 1, firstSlash);
                String start = command.substring(firstSlash + 7, secondSlash);
                String end = command.substring(secondSlash + 5);
                Event task = new Event(desc, start, end);
                this.tasks[this.count] = task;
                this.count++;
                System.out.println("Adding Task: " + task.getName() + " to list! :D");
                System.out.println("Now there are " + this.count + " tasks!");
            } else {
                System.out.println("Invalid format specified... D:");
            }
            System.out.println("--------------------------------------");
            command = scanner.nextLine();
        }

        System.out.println("Goodbye! Hope to see you again! :D");
        System.out.println("--------------------------------------");
    }
}
