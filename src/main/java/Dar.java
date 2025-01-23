import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;


public class Dar {

    private static final HashMap<String, Consumer<String>> instructionMap = new HashMap<>(); // Instruction set
    private static final ArrayList<Task> taskList = new ArrayList<>(); // List of tasks


    public static void main(String[] args) {
        // Greeting
        String greetingMessage = "Hey buddy! The name's Dar, what can I do for you today?";
        System.out.println(greetingMessage + "\n");

        // Main 
        instructionMap.put("mark", Dar::mark);
        instructionMap.put("unmark", Dar::unmark);
        instructionMap.put("list", parameter -> list());
        instructionMap.put("todo", Dar::todo);
        instructionMap.put("deadline", Dar::deadline);

        Scanner scanner = new Scanner(System.in); // Create a Scanner object

        while (true) { 
            String inputState = scanner.nextLine(); // Read a string input
            String[] inputParts = inputState.split(" "); // Split input into parts
            String firstWord = inputParts[0].toLowerCase(); // Extract the first word
            String restOfInput = "";
            for (int i = 1; i < inputParts.length; i++) {
                restOfInput += inputParts[i];
            } // Rest of the input

            if (instructionMap.containsKey(firstWord)) {
                    instructionMap.get(firstWord).accept(restOfInput); // Execute the instruction
            } else if (!firstWord.equals("bye")) {
                String unknownInputMessage = "My apologies, I don't understand what you mean!\n";
                System.out.println(unknownInputMessage);
            } else {
                break; 
            }
        }
        // Exit 
        scanner.close();
        String exitMessage = "I'll see ya around, take it easy bud!";
        System.out.println(exitMessage);

    }
    private static void list() {
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
        System.out.println("Goodjob, one less task to worry about:");
        // System.out.println("[" + userTask.getStatusIcon() + "] " + userTask.getDescription() + "\n");
        System.out.println(userTask.toString());
    }

    private static void unmark(String restOfInput) {
        int taskNumber = Integer.parseInt(restOfInput); // Parse the parameter as an integer
        Task userTask = taskList.get(taskNumber - 1);
        userTask.setUnmark(); // Mark the task done
        System.out.println("Oh okay, this task has been unmarked:");
        System.out.println(userTask.toString());
    }

    private static void todo(String restOfInput) {
        Task userTask = new ToDo(restOfInput);
        taskList.add(userTask); // Add the Task instance to the list
        // System.out.println("TODO detected");
    }

    private static void deadline(String restOfInput) {
        Task userTask = new Deadline(restOfInput);
        taskList.add(userTask); // Add the Task instance to the list
    }
}
