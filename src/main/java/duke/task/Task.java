package duke.task;

public class Task {
    private String name;
    private Boolean done = false;

    public Task(String name) {
        this.name = name;
    }

    public void markDone() {
        this.done = true;
    }

    public void markUndone() {
        this.done = false;
    }

    public String getName() {
        return this.name;
    }

    public boolean getDone() {
        return this.done;
    }

    @Override
    public String toString() {
        String complete = this.done ? "X" : " ";
        return "[" + complete + "] " + this.name;
    }
}
