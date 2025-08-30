package duke.util;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testShowWelcome() {
        Ui.showWelcome("Duke");
        String output = outContent.toString();
        assertTrue(output.contains("Hello, I am Duke!"));
        assertTrue(output.contains("What can I do for you today?"));
        assertTrue(output.contains("--------------------------------------"));
    }

    @Test
    public void testShowGoodbye() {
        Ui.showGoodbye();
        String output = outContent.toString();
        assertTrue(output.contains("Goodbye! Hope to see you again!"));
        assertTrue(output.contains("--------------------------------------"));
    }
}
