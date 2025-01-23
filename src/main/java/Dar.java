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
        instructionMap.put("event", Dar::event);


        Scanner scanner = new Scanner(System.in); // Create a Scanner object

        while (true) { 
            String inputState = scanner.nextLine(); // Read a string input
            String[] inputParts = inputState.split(" ", 2); // Split input into two parts
            String firstWord = inputParts[0].toLowerCase(); // Extract the first word
            String restOfInput = "";
            if (inputParts.length > 1) { 
                restOfInput = inputParts[1]; // Extract the rest of the input
            }

            if (firstWord.equals("bye")) {
                break;
            } else if (instructionMap.containsKey(firstWord)) {
                    instructionMap.get(firstWord).accept(restOfInput); // Execute the instruction
            } else {
                String unknownInputMessage = "My apologies, I don't understand what you mean!\n";
                System.out.println(unknownInputMessage);
            }
        }
        // Exit 
        scanner.close();
        String exitMessage = "I'll see ya around, take it easy bud!";
        System.out.println(exitMessage);

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
        // System.out.println("TEST TODO detected");
    }

    private static void deadline(String restOfInput) {
        Task userTask = new Deadline(restOfInput);
        taskList.add(userTask); // Add the Task instance to the list
    }

    private static void event(String restOfInput) {
        Task userTask = new Event(restOfInput);
        taskList.add(userTask); // Add the Task instance to the list
    }
}
