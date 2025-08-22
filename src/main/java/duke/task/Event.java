package duke.task;

import duke.exception.IncorrectFormatException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private String start;
    private String end;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Event(String name, String start, String end) {
        super(name);
        this.convertToDate(start, end);
    }

    public void convertToDate(String start, String end) {
        try {
            this.start = start;
            // Split date and time
            String[] elems = start.split(" ");
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
            this.startDate = dateTime;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
            this.start = dateTime.format(formatter);
        } catch (IncorrectFormatException | DateTimeException e) {
            //do nothing
        }

        try {
            this.end = end;
            // Split date and time
            String[] elems = end.split(" ");
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
            this.endDate = dateTime;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
            this.end = dateTime.format(formatter);
        } catch (IncorrectFormatException | DateTimeException e) {
            //do nothing
        }
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public String getStart() {
        return this.start;
    }

    public String getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }
}
