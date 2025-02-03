package dar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;
import command.CommandManager;

public class Dar {

    /** This HashMap links command strings (e.g., "todo", "list", "delete") to their corresponding Consumer functions that execute the commands. */
    private static final HashMap<String, Consumer<String>> instructionMap = new HashMap<>();
    private static final Storage storage = new Storage("./data/dardata.txt");
    private static final CommandManager commandManager = new CommandManager(storage);

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
}
