package duke.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiTest {

    @Test
    public void testShowWelcome() {
        String output = Ui.showWelcome("Duke");
        assertTrue(output.contains("Hello, I am Duke!"));
        assertTrue(output.contains("What can I do for you today?"));
    }

    @Test
    public void testShowGoodbye() {
        String output = Ui.showGoodbye();
        assertTrue(output.contains("Goodbye! Hope to see you again!"));
    }
}
