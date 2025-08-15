public class Task {
    private String name;
    private Boolean done = false;

    public Task(String name) {
        this.name = name;
    }

    public void markDone() {
        this.done = true;
    }

    @Override
    public String toString() {
        String complete = this.done ? "X" : " ";
        return "[" + complete + "] " + this.name;
    }
}
