public class Parser {
    private final String commandWord;
    private final String descriptionText;

    public Parser(String inputText) {

        String[] inputParts = inputText.trim().split(" ", 2); 

        this.commandWord = inputParts[0].toLowerCase(); // Extract the first word

        this.descriptionText = (inputParts.length > 1) ? inputParts[1] : ""; // Extract the rest
    }

    public String getCommandWord() {
        return this.commandWord;
    }

    public String getDescriptionText() {
        return this.descriptionText;
    }
}
