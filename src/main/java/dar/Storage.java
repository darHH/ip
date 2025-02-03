package dar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import task.Task;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
        doesFileExist();
    }

    /**
     * Ensures that the data file and its parent directory exist.
     * <p>
     * If the parent directory or data file does not exist, it is created.
     * If an error occurs during file creation, an error message is displayed.
     */
    private void doesFileExist() {
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    /**
     * Saves a list of tasks to the specified file (data/dardata.txt).
     * <p>
     * Each task is converted into data format before being saved.
     * If an error occurs during writing, an error message is displayed.
     *
     * @param tasks The list of tasks to save.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(task.toDataFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the specified file and returns them as an array list.
     * <p>
     * Each line in the file is converted into a `Task` object.
     * If an error occurs while reading a line, an error message is displayed, and skipped.
     *
     * @return An array list of tasks loaded from the dardata.txt file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    tasks.add(Task.fromDataFormat(line)); // Deserialize each line
                } catch (Exception e) {
                    System.out.println("Error loading task: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading tasks from file: " + e.getMessage());
        }
        return tasks;
    }
}
