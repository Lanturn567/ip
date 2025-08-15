import java.util.Scanner;

public class Lanturn {
    public static String name = "Lanturn";
    public static void main(String[] args) {
        System.out.println("--------------------------------------");
        System.out.println("Hello, I am " + Lanturn.name + "! :D");
        System.out.println("What can I do for you today?");
        System.out.println("--------------------------------------");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (!command.equals("bye")) {
            System.out.println(command);
            System.out.println("--------------------------------------");
            command = scanner.nextLine();
        }

        System.out.println("Goodbye! Hope to see you again! :D");
        System.out.println("--------------------------------------");

    }
}
