import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Dar {

    private static final HashMap<String, Runnable> instructionMap = new HashMap<>(); // Instruction set
    private static final ArrayList<Task> taskList = new ArrayList<>(); // List of tasks


    public static void main(String[] args) {
        // Greeting
        String greetingMessage = "Hey buddy! The name's Dar, what can I do for you today?";
        System.out.println(greetingMessage + "\n");

        // Main 
        instructionMap.put("mark", Dar::mark);
        instructionMap.put("unmark", Dar::unmark);
        instructionMap.put("list", Dar::list);

        Scanner scanner = new Scanner(System.in); // Create a Scanner object

        while (true) { 
            String inputState = scanner.nextLine(); // Read a string input
            String[] inputParts = inputState.split(" "); // Split input into parts
            String firstWord = inputParts[0].toLowerCase(); // Extract the first word

            if (instructionMap.containsKey(firstWord)) {
                    instructionMap.get(firstWord).run(); // Execute the instruction
            } else if (!firstWord.equals("bye")) {
            Task userTask = new Task(inputState);
            taskList.add(userTask); // Add the Task instance to the list
            } else {
                break; 
            }
        }
        // Exit 
        scanner.close();
        String exitMessage = "I'll see ya around, take it easy bud!";
        System.out.println("\n" + exitMessage);

    }
    private static void list() {
        System.out.println("Here's your list, better get going!");
        for (int i = 0; i < taskList.size(); i++) {
            Task userTask = taskList.get(i);
            System.out.println(userTask.getTaskNumber() + ".[" + userTask.getStatusIcon() + "] " + userTask.getDescription());
        }
    }

    private static void mark() {
        System.out.println("marking...");
    }

    private static void unmark() {
        System.out.println("unmarking...");

    }
}
