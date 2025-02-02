package task;

import dar.Storage;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    @Test
    public void testSaveAndLoadTasks() {
        Storage storage = new Storage("test_data.txt");

        // Create sample tasks
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("Finish homework"));
        tasks.add(new Deadline("Submit assignment by 12/12/2222 1600"));
        tasks.add(new Event("Project meeting from monday to wednesday"));

        // Save tasks
        storage.saveTasks(tasks);

        // Load tasks
        List<Task> loadedTasks = storage.loadTasks();

        // Validate the loaded tasks match the saved ones
        assertEquals(tasks.size(), loadedTasks.size());
        assertEquals(tasks.get(0).toString(), loadedTasks.get(0).toString());
        assertEquals(tasks.get(1).toString(), loadedTasks.get(1).toString());
        assertEquals(tasks.get(2).toString(), loadedTasks.get(2).toString());

        // Cleanup: Delete test file
        new File("test_data.txt").delete();
    }
}