import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

public class Dar {

    private static final HashMap<String, Consumer<String>> instructionMap = new HashMap<>(); // Instruction set
    private static final Storage storage = new Storage("./data/dardata.txt"); // Storage instance
    private static final CommandManager commandManager = new CommandManager(storage);
    


    public static void main(String[] args) {
        // Initialization and bot start up 
        Ui ui = new Ui();

        // Greeting
        ui.showGreetingMessage();

        // Main 
        instructionMap.put("list", parameter -> commandManager.listTasks());
        instructionMap.put("mark", commandManager::markTask);
        instructionMap.put("unmark", commandManager::unmarkTask);
        instructionMap.put("todo", commandManager::addTodo);
        instructionMap.put("deadline", commandManager::addDeadline);
        instructionMap.put("event", commandManager::addEvent);
        instructionMap.put("delete", commandManager::deleteTask);


        Scanner scanner = new Scanner(System.in); // Create a Scanner object

        while (true) {
            String inputText = scanner.nextLine().trim();
            if (inputText.isEmpty()) {
                ui.showInvalidInputMessage();
                continue;
            }

            Parser parser = new Parser(inputText);
            String commandWord = parser.getCommandWord();
            String descriptionText = parser.getDescriptionText();

            if (commandWord.equals("bye")) {
                storage.saveTasks(commandManager.getTaskList()); // Save tasks on exit
                break;
            } else if (instructionMap.containsKey(commandWord)) {
                instructionMap.get(commandWord).accept(descriptionText);
            } else {
                ui.showUnknownInputMessage();
            }
        }

        // Exit 
        scanner.close();
        ui.showExitMessage();
    }
}
