package dar;

public class Parser {
    private final String commandWord;
    private final String descriptionText;

    public Parser(String inputText) {

        // Split the input text after first word, into two sections.
        String[] inputParts = inputText.trim().split(" ", 2); 
        // Extract the first word
        this.commandWord = inputParts[0].toLowerCase(); 
        // Extract the rest
        this.descriptionText = (inputParts.length > 1) ? inputParts[1] : ""; 
    }

    public String getCommandWord() {
        return this.commandWord;
    }

    public String getDescriptionText() {
        return this.descriptionText;
    }
}
