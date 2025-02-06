package dar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

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
     * to their corresponding Consumer functions that execute the commands.
     */
    private static final HashMap<String, Consumer<String>> instructionMap = new HashMap<>();

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
     * Entry point for Dar.
     * Initializes the system, processes user commands, and handles program termination.
     * The program runs in a loop until the user enters "bye".
     * All tasks are saved to storage when the program exits.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialization and bot start up
        Ui ui = new Ui();

        // Greeting
        ui.showGreetingMessage();

        // Filling in the Instruction Map
        instructionMap.put("list", parameter -> commandManager.listTasks());
        instructionMap.put("mark", commandManager::markTask);
        instructionMap.put("unmark", commandManager::unmarkTask);
        instructionMap.put("todo", commandManager::addTodo);
        instructionMap.put("deadline", commandManager::addDeadline);
        instructionMap.put("event", commandManager::addEvent);
        instructionMap.put("delete", commandManager::deleteTask);
        instructionMap.put("find", commandManager::findTasks);

        Scanner scanner = new Scanner(System.in);

        // Continuously reads user input until "bye" is entered.
        while (true) {
            String inputText = scanner.nextLine().trim();
            if (inputText.isEmpty()) {
                ui.showInvalidInputMessage();
                continue;
            }

            // Parse the user input into a command and description.
            Parser parser = new Parser(inputText);
            String commandWord = parser.getCommandWord();
            String descriptionText = parser.getDescriptionText();

            // If the user types "bye", save all tasks and exit the loop.
            if (commandWord.equals("bye")) {
                storage.saveTasks(commandManager.getTaskList()); // Save tasks on exit
                break;

            // If the command is recognized, execute the corresponding action.
            } else if (instructionMap.containsKey(commandWord)) {
                instructionMap.get(commandWord).accept(descriptionText);

            // If the command is unknown, show an error message.
            } else {
                ui.showUnknownInputMessage();
            }
        }
        // Exit
        scanner.close();
        ui.showExitMessage();
    }
    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }
}
