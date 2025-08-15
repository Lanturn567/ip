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
                int num = Integer.parseInt(command.split(" ")[1]);
                Task curr = this.tasks[num];
                curr.markDone();
                System.out.println("Ok! Marking Task: " + curr.getName() + " as done!");
            } else if (command.startsWith("unmark")) {
                int num = Integer.parseInt(command.split(" ")[1]);
                Task curr = this.tasks[num];
                curr.markUndone();
                System.out.println("Ok! Marking Task: " + curr.getName() + " as undone!");
            } else {
                System.out.println(command);
                Task task = new Task(command);
                this.tasks[this.count] = task;
                this.count++;
            }
            System.out.println("--------------------------------------");
            command = scanner.nextLine();
        }

        System.out.println("Goodbye! Hope to see you again! :D");
        System.out.println("--------------------------------------");
    }
}
