package dar;

public class Ui {
    public Ui(){
        // Empty constructor
    }

    /**
     * Displays greeting message
     * <p>
     * This method does not return anything.
     */
    public void showGreetingMessage(){
        System.out.println("Hey buddy! The name's Dar, what can I do for you today?\n");
    }

    /**
     * Displays error message when chatbot input is empty
     * <p>
     * This method does not return anything.
     */
    public void showInvalidInputMessage() {
        System.out.println("Oops! You entered nothing. Try again.\n");
    }

    /**
     * Displays error message when input (first word) is an unknown command 
     * <p>
     * This method does not return anything.
     */
    public void showUnknownInputMessage() {
        System.out.println("My apologies, I don't understand what you mean! Let my dev know and I may get it next time :D \n");
    }    

    /**
     * Displays exit message, when input is "bye"
     * <p>
     * This method does not return anything.
     */
    public void showExitMessage() {
        System.out.println("I'll see ya around, take it easy bud!\n");
    }    
 
}
