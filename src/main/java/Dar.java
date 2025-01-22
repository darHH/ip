import java.util.ArrayList;
import java.util.Scanner;

public class Dar {
    public static void main(String[] args) {
        // Greeting
        String greetingMessage = "Hey buddy! The name's Dar, what can I do for you today?";
        String exitMessage = "I'll see ya around, take it easy bud!";
        System.out.println(greetingMessage + "\n");

        // Main 
        ArrayList<String> userList = new ArrayList<>(); // Create a dynamic array
        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        while (true) { 
            String inputState = scanner.nextLine(); // Read a string input
            if (inputState.equalsIgnoreCase("LIST")) {
                System.out.println("Here's the current list:");
                for (int i = 0; i < userList.size(); i++) {
                    System.out.println((i + 1) + ". " + userList.get(i)); 
                } 
            } else if (!inputState.equalsIgnoreCase("BYE")) { 
            userList.add(inputState);
            System.out.println("Gotcha! Added: " + inputState + "\n"); 
            } else {
                break; 
            }
        }
        // Exit 
        scanner.close();
        System.out.println("\n" + exitMessage);

    }
}
