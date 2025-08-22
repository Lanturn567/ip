public enum CommandType {
    LIST,
    MARK,
    UNMARK,
    REMOVE,
    TODO,
    DEADLINE,
    EVENT,
    BYE,
    UNKNOWN;

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
        return UNKNOWN;
    }
}
