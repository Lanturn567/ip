import jdk.jshell.Snippet;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Save {
    private static String filePath = "./data/duke.txt";

    public static void read(ArrayList<Task> to) throws FileNotFoundException {
        File file = new File(Save.filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            try {
                to.add(Save.unserialize(line));
            } catch (IncorrectFormatException e) {
                System.out.println("Data corrupted. D:");
            }
        }
    }

    public static void write(ArrayList<Task> from) {
        File file = new File(Save.filePath);
        File parentDir = file.getParentFile(); // get parent directory

        try {
            // Create directory if it doesn't exist
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileWriter writer = new FileWriter(file)) {
                for (Task t : from) {
                    try {
                        writer.write(Save.serialize(t) + System.lineSeparator());
                    } catch (IncorrectFormatException e) {
                        System.out.println("Error in serializing.. D:");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong! D:");
        }
    }

    public static String serialize(Task task) throws IncorrectFormatException {
        String done = task.getDone() ? "1" : "0";
        if (task instanceof Todo) {
            return "T | " + done + " | " + task.getName();
        } else if (task instanceof Deadline d) {
            return "D | " + done + " | " + d.getName() + " | " + d.getBy();
        } else if (task instanceof Event e) {
            return "E | " + done + " | " + e.getName() + " | " + e.getStart() + " | " + e.getEnd();
        } else {
            throw new IncorrectFormatException();
        }
    }

    public static Task unserialize(String s) throws IncorrectFormatException {
        String[] elems = s.split(" \\| ");
        for (String elem: elems) {
            System.out.println(elem);
        }
        Task task;
        if (s.startsWith("D")) {
            task = new Deadline(elems[2], elems[3]);
        } else if (s.startsWith("E")) {
            task = new Event(elems[2], elems[3], elems[4]);
        } else if (s.startsWith("T")) {
            task = new Todo(elems[2]);
        } else {
            throw new IncorrectFormatException();
        }

        boolean done = elems[1].equals("1");
        if (done) {
            task.markDone();
        }
        return task;
    }
}
