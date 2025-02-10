package dar;

import java.util.HashMap;
import java.util.function.Function;

import command.CommandManager;

/**
 * Main class for the Dar task management application. This class serves as the entry point
 * and handles user interaction through a command-line interface.
 *
 * Tasks are persisted to a file and loaded on startup.
 */
public class Dar {

    /**
     * This HashMap links command strings (e.g., "todo", "list", "delete")
     * to their corresponding functions that return a response string.
     */
    private static final HashMap<String, Function<String, String>> instructionMap = new HashMap<>();

    /**
     * Handles persistence of tasks to and from disk storage.
     * Tasks are stored in "./data/dardata.txt".
     */
    private static final Storage storage = new Storage("./data/dardata.txt");

    /**
     * Manages the execution of commands and maintains the task list.
     */
    private static final CommandManager commandManager = new CommandManager(storage);


    /**
     * Handles user interface interactions.
     */
    private static final Ui ui = new Ui();

    /**
     * Initializes Dar and sets up the instruction map.
     */
    public Dar() {
        // Filling in the Instruction Map with functions that return Strings
        instructionMap.put("list", parameter -> commandManager.listTasks());
        instructionMap.put("sort", parameter -> commandManager.sortDeadline());
        instructionMap.put("mark", commandManager::markTask);
        instructionMap.put("unmark", commandManager::unmarkTask);
        instructionMap.put("todo", commandManager::addTodo);
        instructionMap.put("deadline", commandManager::addDeadline);
        instructionMap.put("event", commandManager::addEvent);
        instructionMap.put("delete", commandManager::deleteTask);
        instructionMap.put("find", commandManager::findTasks);
    }

    /**
     * Generates a response for the user's chat message.
     * @param input User input string
     * @return Response from Dar
     */
    public String getResponse(String input) {
        if (input.trim().isEmpty()) {
            return ui.showInvalidInputMessage();
        }

        // Parse the user input into a command and description
        Parser parser = new Parser(input);
        String commandWord = parser.getCommandWord();
        String descriptionText = parser.getDescriptionText();

        // Handle "bye" separately
        if (commandWord.equals("bye")) {
            storage.saveTasks(commandManager.getTaskList());
            return ui.showExitMessage();
        }

        // If the command exists in instructionMap, execute and return response
        if (instructionMap.containsKey(commandWord)) {
            return instructionMap.get(commandWord).apply(descriptionText);
        }

        // If the command is unknown
        return ui.showUnknownInputMessage();
    }
}
