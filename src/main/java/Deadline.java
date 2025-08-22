import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private String by;
    private LocalDateTime deadline;

    public Deadline(String name, String by) {
        super(name);
        this.convertToDate(by);
    }

    public String getBy() {
        return this.by;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    public void convertToDate(String by) {
        try {
            this.by = by;
            // Split date and time
            String[] elems = by.split(" ");
            String[] dates = elems[0].split("/");   // e.g., "2025/08/22"
            String[] times = elems[1].split(":");   // e.g., "14:30"

            // Parse strings to integers
            if (dates.length < 2 || times.length < 1) {
                throw new IncorrectFormatException();
            }
            int year = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            int day = Integer.parseInt(dates[2]);
            int hour = Integer.parseInt(times[0]);
            int minute = Integer.parseInt(times[1]);

            // Create LocalDateTime
            LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
            this.deadline = dateTime;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            this.by = dateTime.format(formatter);
        } catch (IncorrectFormatException | DateTimeException e) {
            //do nothing
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
