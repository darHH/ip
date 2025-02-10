package dar;

/**
 * The Parser class is responsible for parsing user input into two parts:
 * the first word (the command) and the remaining description text.
 */
public class Parser {
    private final String commandWord;
    private final String descriptionText;

    /**
     * Constructs a new Parser instance by parsing the provided input text.
     * <p>
     * The input text is split into two parts: the first word (command) and the remaining text (description).
     * If there is no description provided, an empty string is used as the description.
     *
     * @param inputText The input text to be parsed, typically containing a command followed by its description.
     */
    public Parser(String inputText) {
        assert inputText != null : "Input text cannot be null";
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
