package duke.main;

import duke.util.Chatbot;

/**
 * Entrance point to chatbot
 */
public class Lanturn {
    /**
     * The name of the chatbot
     */
    public static String name = "duke.main.Lanturn";
    public static void main(String[] args) {
        Chatbot chatbot = new Chatbot(Lanturn.name);
        chatbot.run();
    }
}
