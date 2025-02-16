package task;
import static org.junit.jupiter.api.Assertions.assertEquals; // Static Import
import static org.junit.jupiter.api.Assertions.assertTrue;

// Standard Java Package Imports
import java.util.ArrayList;

// Special Imports (JUnit)
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Custom Imports
import command.CommandManager;
import dar.Storage;

public class CommandManagerTest {
    private CommandManager commandManager;
    private Storage mockStorage;

    @BeforeEach
    public void setUp() {
        mockStorage = new Storage("testTasks.txt") {
            @Override
            public ArrayList<Task> loadTasks() {
                return new ArrayList<>();
            }
        };
        commandManager = new CommandManager(mockStorage);
    }

    @Test
    public void addTodo_validDescription_success() {
        String result = commandManager.addTodo("Buy milk");
        assertTrue(result.contains("I've added this todo"));
        assertEquals(1, commandManager.getTaskList().size());
    }

    @Test
    public void addTodo_emptyDescription_errorMessage() {
        String result = commandManager.addTodo("   ");
        assertEquals("The description of a todo task cannot be empty :<\n", result);
        assertEquals(0, commandManager.getTaskList().size());
    }

    @Test
    public void addTodo_duplicateTask_detected() {
        commandManager.addTodo("Buy eggs");
        String result = commandManager.addTodo("Buy eggs");
        assertTrue(result.contains("This task has duplicates"));
    }
}
