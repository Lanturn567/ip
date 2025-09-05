package duke.task;

/**
 * Class representing a task
 */
public class Task {
    /** The task name. */
    private String name;
    /** Whether the task is completed. */
    private boolean isDone = false;

    public Task(String name) {
        this.name = name;
    }

    /**
     * Marks the task as complete.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as uncomplete.
     */
    public void markUndone() {
        this.isDone = false;
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
        return this.isDone;
    }

    @Override
    public String toString() {
        String complete = this.isDone ? "X" : " ";
        return "[" + complete + "] " + this.name;
    }
}
