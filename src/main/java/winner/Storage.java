package winner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Handles the loading and saving of tasks to a file.
 * It also ensures that the necessary file and directories are created if they do not exist.
 */
public class Storage {
    private static final String HOME = System.getProperty("user.home");
    private static final Path FOLDER_PATH = Paths.get(HOME, "Winner");
    private static final Path TASKLIST_PATH = Paths.get(HOME, "Winner", "tasklist.txt");

    /**
     * Checks if the Winner folder and tasklist file exist. If not, it creates them.
     */
    public static void checkAndCreateFile() { //Storage
        assert FOLDER_PATH != null;
        assert TASKLIST_PATH != null;
        try {
            if (Files.exists(FOLDER_PATH)) {
                Files.createFile(TASKLIST_PATH);
            } else {
                Files.createDirectories(FOLDER_PATH);
                Files.createFile(TASKLIST_PATH);
            }
        } catch (IOException e) {
            System.out.println("Error creating file or directory: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the tasklist file into the provided ArrayList of tasks.
     *
     * @param tasks List where tasks will be loaded into.
     */
    public static void loadTasks(ArrayList<Task> tasks) { //Storage
        try (BufferedReader br = Files.newBufferedReader(TASKLIST_PATH)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                String whichTask = parts[0].trim();
                boolean isDone = parts[1].trim().equals("1");
                String description = parts[2].trim();

                Task task = null;
                switch (whichTask) {
                case "T":
                    task = new ToDo(description);
                    break;
                case "D":
                    LocalDateTime byDateTime = LocalDateTime.parse(parts[3].trim());
                    task = new Deadline(description, byDateTime);
                    break;
                case "E":
                    String start = parts[3].trim();
                    String end = parts[4].trim();
                    task = new Event(description, start, end);
                    break;
                default:
                    break;
                }
                assert line == null;
                if (task != null) {
                    if (isDone) {
                        task.markDone();
                    }
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
    }

    /**
     * Saves the list of tasks to the tasklist file.
     *
     * @param tasks List of tasks to be saved.
     */
    public static void saveTasks(ArrayList<Task> tasks) { //Storage
        try (BufferedWriter bw = Files.newBufferedWriter(TASKLIST_PATH)) {
            for (Task i : tasks) {
                String description = i.description;
                String isDone = i.isDone ? "1" : "0";
                if (i instanceof ToDo) {
                    bw.write(String.format("T | %s | %s%n", isDone, description));
                } else if (i instanceof Deadline) {
                    LocalDateTime byDateTime = ((Deadline) i).byDateTime;
                    bw.write(String.format("D | %s | %s | %s%n", isDone, description, byDateTime));
                } else if (i instanceof Event) {
                    String start = ((Event) i).start;
                    String end = ((Event) i).end;
                    bw.write(String.format("E | %s | %s | %s | %s%n", isDone, description, start, end));
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing tasks to file: " + e.getMessage());
        }
    }

}
