package duke.util;

/**
 * Represents the types of commands supported by the Duke chatbot.
 * <p>
 * Each {@code CommandType} corresponds to a specific user command
 * that can be entered in the chatbot interface.
 */
public enum CommandType {
    /**
     * Command to list all tasks.
     */
    LIST,

    /**
     * Command to mark a task as done.
     */
    MARK,

    /**
     * Command to unmark a task (set as not done).
     */
    UNMARK,

    /**
     * Command to remove a task from the task list.
     */
    REMOVE,

    /**
     * Command to add a {@code Todo} task.
     */
    TODO,

    /**
     * Command to list all tasks due today.
     */
    DUE,

    /**
     * Command to add a {@code Deadline} task.
     */
    DEADLINE,

    /**
     * Command to add an {@code Event} task.
     */
    EVENT,

    /**
     * Command to exit the chatbot.
     */
    BYE,

    /**
     * Command type for unrecognized input.
     */
    UNKNOWN;

    /**
     * Returns the {@code CommandType} corresponding to the given user input.
     *
     * @param input the raw user input string
     * @return the matching {@code CommandType}, or {@link #UNKNOWN} if none match
     */
    public static CommandType fromInput(String input) {
        String lower = input.toLowerCase();
        if (lower.equals("list")) {
            return LIST;
        }
        if (lower.startsWith("mark")) {
            return MARK;
        }
        if (lower.startsWith("unmark")) {
            return UNMARK;
        }
        if (lower.startsWith("remove")) {
            return REMOVE;
        }
        if (lower.startsWith("todo")) {
            return TODO;
        }
        if (lower.startsWith("deadline")) {
            return DEADLINE;
        }
        if (lower.startsWith("event")) {
            return EVENT;
        }
        if (lower.equals("bye")) {
            return BYE;
        }
        if (lower.equals("due")) {
            return DUE;
        }
        return UNKNOWN;
    }
}
