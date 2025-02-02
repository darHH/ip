import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

public class Dar {

    private static final HashMap<String, Consumer<String>> instructionMap = new HashMap<>(); // Instruction set
    private static final ArrayList<Task> taskList = new ArrayList<>(); // List of tasks
    private static final Storage storage = new Storage("./data/dardata.txt"); // Storage instance


    public static void main(String[] args) {
        // Initialization and bot start up 
        taskList.addAll(storage.loadTasks());
        Ui ui = new Ui();

        // Greeting
        ui.showGreetingMessage();

        // Main 
        instructionMap.put("mark", Dar::mark);
        instructionMap.put("unmark", Dar::unmark);
        instructionMap.put("list", parameter -> list());
        instructionMap.put("todo", Dar::todo);
        instructionMap.put("deadline", Dar::deadline);
        instructionMap.put("event", Dar::event);
        instructionMap.put("delete", Dar::delete);


        Scanner scanner = new Scanner(System.in); // Create a Scanner object

        while (true) { 
            String inputText = scanner.nextLine().trim(); // Read a string input
            if (inputText.isEmpty()) {
                ui.showInvalidInputMessage();
                continue;
            }
            Parser parser = new Parser(inputText);
            String commandWord = parser.getCommandWord();
            String descriptionText = parser.getDescriptionText();
            if (commandWord.equals("bye")) {
                storage.saveTasks(taskList);
                break;
            } else if (instructionMap.containsKey(commandWord)) {
                    instructionMap.get(commandWord).accept(descriptionText); // Execute the instruction
            } else {
                ui.showUnknownInputMessage();
            }
        }

        // Exit 
        scanner.close();
        ui.showExitMessage();
    }

    private static void list() {
        // System.out.println("TEST Task list size: " + taskList.size());
        System.out.println("Here's your list, better get going!");
        for (int i = 0; i < taskList.size(); i++) {
            Task userTask = taskList.get(i);
            System.out.println(userTask.getTaskNumber() + "." + userTask.toString());
        }
        System.out.println("\n");

    }

    private static void mark(String restOfInput) {
        int taskNumber = Integer.parseInt(restOfInput); // Parse the parameter as an integer
        Task userTask = taskList.get(taskNumber - 1);
        userTask.setMark(); // Mark the task done
        storage.saveTasks(taskList); // Save to data
        System.out.println("Goodjob, one less task to worry about:");
        System.out.println(userTask.toString() +"\n");
    }

    private static void unmark(String restOfInput) {
        int taskNumber = Integer.parseInt(restOfInput); // Parse the parameter as an integer
        Task userTask = taskList.get(taskNumber - 1);
        userTask.setUnmark(); // Mark the task done
        storage.saveTasks(taskList); // Save to data
        System.out.println("Oh okay, this task has been unmarked:");
        System.out.println(userTask.toString() +"\n");
    }

    private static void todo(String restOfInput) {
        if (restOfInput.replace(" ","") != "") {
            Task userTask = new ToDo(restOfInput);
            taskList.add(userTask); // Add the Task instance to the list
            storage.saveTasks(taskList); // Save to data
            // System.out.println("TEST TODO detected");
        } else {
            System.out.println("Come on man, the description of a todo task cannot be empty :<\n");
        }
    }

    private static void deadline(String restOfInput) {
        if (restOfInput.replace(" ","") != "") {
            Task userTask = new Deadline(restOfInput);
            taskList.add(userTask); // Add the Task instance to the list
            storage.saveTasks(taskList); // Save to data
        } else {
            System.out.println("Come on man, the description of a deadline task cannot be empty :<\n");
        }
    }

    private static void event(String restOfInput) {
        if (restOfInput.replace(" ","") != "") {
            Task userTask = new Event(restOfInput);
            taskList.add(userTask); // Add the Task instance to the list
            storage.saveTasks(taskList); // Save to data
        } else {
            System.out.println("Come on man, the description of an event task cannot be empty :<\n");
        }
    }

    private static void delete(String restOfInput) {
        int taskNumber = Integer.parseInt(restOfInput); // Parse the parameter as an integer
        Task userTask = taskList.remove(taskNumber - 1);
        userTask.totalTasksMinusOne();
        for (int i = 0; i < taskList.size(); i++) {
            taskList.get(i).updateTaskNumber(i + 1); // Update remaining task numbers based on index
        }
        storage.saveTasks(taskList); // Save to data
        System.out.println("Roger that, this task has been removed:");
        System.out.println(userTask.toString());
        System.out.println("Now you have " + Task.getTotalTasks() + " task(s) in your list.\n");
    }
}
