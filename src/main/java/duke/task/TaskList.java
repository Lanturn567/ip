package duke.task;

import duke.exception.DukeException;
import duke.exception.ListEmptyException;
import duke.exception.TaskNotFoundException;
import duke.exception.TooManyTasksException;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();
    private static final int MAX_TASKS = 100;

    public TaskList() {
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) throws DukeException {
        if (tasks.size() >= MAX_TASKS) {
            throw new TooManyTasksException();
        }
        tasks.add(task);
        System.out.println("Adding duke.task.Task: " + task.getName() + " to list! :D");
        System.out.println("Now there are " + tasks.size() + " tasks!");
    }

    public void removeTask(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException();
        }
        Task task = tasks.remove(index);
        System.out.println("Removing duke.task.Task: " + task.getName() + " from list! D:");
        System.out.println("Now there are " + tasks.size() + " tasks!");
    }

    public void markTask(int index, boolean done) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new TaskNotFoundException();
        }
        Task task = tasks.get(index);
        if (done) {
            task.markDone();
            System.out.println("Ok! Marking duke.task.Task: " + task.getName() + " as done!");
        } else {
            task.markUndone();
            System.out.println("Ok! Marking duke.task.Task: " + task.getName() + " as undone!");
        }
    }

    public void listTasks() throws DukeException {
        if (tasks.isEmpty()) {
            throw new ListEmptyException();
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void listTasksByDeadline() throws DukeException {
        if (tasks.isEmpty()) {
            throw new ListEmptyException();
        }
        LocalDate today = LocalDate.now();
        int count = 0;
        for (Task task : tasks) {
            if (task instanceof Deadline d && !d.getDone() && d.getDeadline().toLocalDate().equals(today)) {
                count++;
                System.out.println(count + ". " + d);
            }
        }
        if (count == 0) {
            System.out.println("Yay! No tasks due today! Yay! :D");
        }
    }
}
