package duke.task;

import duke.exception.IncorrectFormatException;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTest {

    @Test
    public void testValidDeadlineParsing() {
        Deadline deadline = new Deadline("submit report", "2025/08/22 14:30");
        LocalDateTime expected = LocalDateTime.of(2025, 8, 22, 14, 30);

        assertEquals(expected, deadline.getDeadline(), "Deadline LocalDateTime should be parsed correctly");
        assertEquals("Aug 22 2025 14:30", deadline.getBy(), "Deadline should be formatted nicely");
    }

    @Test
    public void testInvalidDeadlineParsing() {
        Deadline deadline = new Deadline("submit report", "invalid date");

        // should have failed to parse
        assertNull(deadline.getDeadline(), "Deadline should remain null if parsing fails");
        assertEquals("invalid date", deadline.getBy(), "Original string should remain unchanged on failure");
    }

    @Test
    public void testToStringOutput() {
        Deadline deadline = new Deadline("finish project", "2025/12/01 09:00");

        String expected = "[D][ ] finish project (by: Dec 01 2025 09:00)";
        assertEquals(expected, deadline.toString(), "toString() should return formatted deadline");
    }

    @Test
    public void testMarkAsDone() {
        Deadline deadline = new Deadline("study for exam", "2025/11/05 20:00");
        deadline.markDone();
        String expected = "[D][X] study for exam (by: Nov 05 2025 20:00)";
        assertEquals(expected, deadline.toString(), "markDone() should change status to [X]");
        deadline.markUndone();
        expected = "[D][ ] study for exam (by: Nov 05 2025 20:00)";
        assertEquals(expected, deadline.toString(), "markUndone() should change status to [ ]");
    }
}
