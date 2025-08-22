package duke.util;

public class UI {

    public static void showWelcome(String name) {
        System.out.println("--------------------------------------");
        System.out.println("Hello, I am " + name + "! :D");
        System.out.println("What can I do for you today?");
        System.out.println("--------------------------------------");
    }

    public static void showGoodbye() {
        System.out.println("Goodbye! Hope to see you again! :D");
        System.out.println("--------------------------------------");
    }
}
