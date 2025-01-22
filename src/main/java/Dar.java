import java.util.Scanner;

public class Dar {
    public static void main(String[] args) {
        // Greeting
        String greetingMessage = "Hey buddy! The name's Dar, what can I do for you today?";
        String exitMessage = "I'll see ya around, take it easy bud!";
        System.out.println(greetingMessage + "\n");

        // Main 
        String inputState = "";
        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        while (true) { 
            inputState = scanner.nextLine(); // Read a string input
            if (!inputState.equalsIgnoreCase("BYE")) { 
                System.out.println("\n" + inputState + "\n");
            } else {
                break; 
            }
        }
        // Exit 
        scanner.close();
        System.out.println("\n" + exitMessage);

    }
}
