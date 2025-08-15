import java.util.Scanner;

public class Chatbot {
    private String name;
    private String[] text = new String[100];
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
                    System.out.println(i + 1 + ". " + this.text[i]);
                }
            } else {
                System.out.println(command);
                this.text[this.count] = command;
                this.count++;
            }
            System.out.println("--------------------------------------");
            command = scanner.nextLine();
        }

        System.out.println("Goodbye! Hope to see you again! :D");
        System.out.println("--------------------------------------");
    }
}
