package duke.util;

public class Command {
    private String name;
    private Action action;

    public Command(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public String toString() {
        return this.name;
    }
}