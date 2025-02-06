package dar;

/**
 * The Ui class handles the display of messages to the user.
 * <p>
 */
public class Ui {

    /**
     * Constructs a new Ui instance.
     */
    public Ui() {
        // Empty constructor
    }

    /**
     * Displays greeting message
     * <p>
     * This method does not return anything.
     */
    public String showGreetingMessage() {
        return ("Hey buddy! The name's Dar, what can I do for you today?\n");
    }

    /**
     * Displays error message when chatbot input is empty
     * <p>
     * This method does not return anything.
     */
    public String showInvalidInputMessage() {
        return ("Oops! You entered nothing. Try again.\n");
    }

    /**
     * Displays error message when input (first word) is an unknown command
     * <p>
     * This method does not return anything.
     */
    public String showUnknownInputMessage() {
        return ("My apologies, I don't understand what you mean! Please let my dev know :D \n");
    }

    /**
     * Displays exit message, when input is "bye"
     * <p>
     * This method does not return anything.
     */
    public String showExitMessage() {
        return ("I'll see ya around, take it easy bud!\n");
    }

}
