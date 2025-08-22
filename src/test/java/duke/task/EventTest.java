package duke.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EventTest {

    @Test
    public void testValidEventParsing() {
        Event event = new Event("Team meeting", "2025/08/22 14:30", "2025/08/22 16:00");

        LocalDateTime expectedStart = LocalDateTime.of(2025, 8, 22, 14, 30);
        LocalDateTime expectedEnd = LocalDateTime.of(2025, 8, 22, 16, 0);

        assertEquals(expectedStart, event.getStartDate(), "Start date should be parsed correctly");
        assertEquals(expectedEnd, event.getEndDate(), "End date should be parsed correctly");

        assertEquals("Aug 22 2025 14:30", event.getStart(), "Formatted start string should match");
        assertEquals("Aug 22 2025 16:00", event.getEnd(), "Formatted end string should match");
    }

    @Test
    public void testInvalidEventParsing() {
        Event event = new Event("Broken event", "invalid start", "2025/99/99 99:99");

        // Parsing should fail
        assertNull(event.getStartDate(), "Start date should remain null when parsing fails");
        assertNull(event.getEndDate(), "End date should remain null when parsing fails");

        // Original strings should remain
        assertEquals("invalid start", event.getStart());
        assertEquals("2025/99/99 99:99", event.getEnd());
    }

    @Test
    public void testToStringOutput() {
        Event event = new Event("Hackathon", "2025/12/01 09:00", "2025/12/01 18:00");

        String expected = "[E][ ] Hackathon (from: Dec 01 2025 09:00 to: Dec 01 2025 18:00)";
        assertEquals(expected, event.toString(), "toString() should format event details correctly");
    }

    @Test
    public void testMarkAsDone() {
        Event event = new Event("Conference", "2025/11/10 08:00", "2025/11/10 17:00");
        event.markDone();
        String expected = "[E][X] Conference (from: Nov 10 2025 08:00 to: Nov 10 2025 17:00)";
        assertEquals(expected, event.toString(), "markDone() should update status to [X]");
        event.markUndone();
        expected = "[E][ ] Conference (from: Nov 10 2025 08:00 to: Nov 10 2025 17:00)";
        assertEquals(expected, event.toString(), "markUndone() should update status to [ ]");
    }
}
