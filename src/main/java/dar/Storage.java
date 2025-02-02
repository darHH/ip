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

    // Your code must handle the case where the data file doesn't exist at the start.
    private void doesFileExist() {
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs(); // Create parent directory
            }
            if (!file.exists()) {
                file.createNewFile(); // Create file if it doesn't exist
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    // Saves tasks to the file
    public void saveTasks(ArrayList<Task> tasks) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(task.toDataFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Loads tasks from the file
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