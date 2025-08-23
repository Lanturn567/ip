package duke.task;

/**
 * Class representing a task
 */
public class Task {
    /** The task name. */
    private String name;
    /** Whether the task is completed. */
    private Boolean done = false;

    public Task(String name) {
        this.name = name;
    }

    /**
     * Marks the task as complete.
     */
    public void markDone() {
        this.done = true;
    }

    /**
     * Marks the task as uncomplete.
     */
    public void markUndone() {
        this.done = false;
    }

    /**
     * Returns the name of the task.
     * @return Name of the task.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a boolean representing whether the task has been completed.
     * @return Completion of the task.
     */
    public boolean getDone() {
        return this.done;
    }

    @Override
    public String toString() {
        String complete = this.done ? "X" : " ";
        return "[" + complete + "] " + this.name;
    }
}
