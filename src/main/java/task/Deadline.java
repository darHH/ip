package task;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Represents a Deadline task, which includes a description and a deadline with both date and time.
 */
public class Deadline extends Task {

    private String afterBy;
    private String description;
    private LocalDate deadlineDate;
    private LocalTime deadlineTime;

    /**
     * Constructs a Deadline task and extracts its deadline details.
     * <p>
     * Calls {@code parseDescription} to extract the task description and deadline
     *
     * @param description The task description, including the "by" keyword followed by the deadline.
     */
    public Deadline(String description) throws IllegalArgumentException {
        super(description);
        parseDescription(description);
    }

    /**
     * Parses the task description to extract the deadline details.
     * <p>
     * This method splits the input based on the "by" keyword to separate the task
     * description from the deadline. The extracted deadline is validated and stored.
     *
     * @param description The input string containing the task details and deadline.
     * @throws IllegalArgumentException If the deadline format is invalid.
     */
    private void parseDescription(String description) {
        String[] descriptionParts = description.split("by");
        this.description = descriptionParts[0].trim();
        this.afterBy = (descriptionParts.length > 1) ? descriptionParts[1].trim() : "-";
        if (!saveDateTime(afterBy)) {
            throw new IllegalArgumentException("Invalid date or time format. "
                    + "Declare date and time after 'by' in DD/MM/YYYY and/or HHMM format.");
        }
    }


    /**
     * Extracts and saves a valid date and/or time from the given description.
     * If an invalid format is encountered, returns false.
     *
     * @param description The input string containing date and/or time.
     * @return true if a valid date or time is found, false otherwise.
     */
    public boolean saveDateTime(String description) {
        ArrayList<String> numberSequence = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        // Extract digits and slashes that might represent dates or times
        extractNumericSequences(description, numberSequence, currentNumber);

        // Check extracted sequences for valid date or time formats
        return parseDateTime(numberSequence);
    }

    /**
     * Extracts numeric sequences from a given string, capturing potential date or time values.
     * <p>
     * This method scans through the input string and collects sequences of digits and slashes
     * Any non-numeric character (except '/') acts as a delimiter, triggering the end of a number sequence.
     *
     * @param description The input string that may contain date and/or time.
     * @param numberSequence The list to store extracted numeric sequences.
     * @param currentNumber A StringBuilder used to accumulate individual numeric sequences.
     */
    private void extractNumericSequences(String description, ArrayList<String>
        numberSequence, StringBuilder currentNumber) {
        for (char ch : description.toCharArray()) {
            if (Character.isDigit(ch) || ch == '/') {
                currentNumber.append(ch); // Add digit to the current sequence
            } else {
                if (currentNumber.length() > 0) {
                    numberSequence.add(currentNumber.toString());
                    currentNumber.setLength(0); // Reset for the next sequence
                }
            }
        }
        if (currentNumber.length() > 0) {
            numberSequence.add(currentNumber.toString());
        }
    }

    /**
     * Parses the extracted numeric sequences to determine if they represent a valid date or time.
     * <p>
     * This method checks if each extracted sequence matches either a date format (DD/MM/YYYY)
     * or a time format (HHMM). If a valid date or time is found, it updates the corresponding
     * instance variables and returns true.
     *
     * @param numberSequence A list of extracted numeric sequences to be checked.
     * @return true if a valid date or time is found, false otherwise.
     */
    private boolean parseDateTime(ArrayList<String> numberSequence) {
        boolean isValidDateOrTime = false;

        for (String element : numberSequence) {
            try {
                if (element.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    deadlineDate = LocalDate.parse(element, dateFormatter);
                    isValidDateOrTime = true;
                } else if (element.matches("\\d{4}")) {
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
                    deadlineTime = LocalTime.parse(element, timeFormatter);
                    isValidDateOrTime = true;
                }
            } catch (DateTimeParseException e) {
                return false; // Invalid date/time format
            }
        }

        return isValidDateOrTime;
    }

    public String getDeadlineDate() {
        return deadlineDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public String getDeadlineTime() {
        return (deadlineTime != null)
            ? deadlineTime.format(DateTimeFormatter.ofPattern("ha"))
            : "No time set";
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        String formattedDate = (deadlineDate != null)
                ? deadlineDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                : "-";
        String formattedTime = (deadlineTime != null)
                ? deadlineTime.format(DateTimeFormatter.ofPattern("ha"))
                : "-";

        return "[D][" + this.getStatusIcon() + "] " + this.description
                + " (By: " + formattedDate + " " + formattedTime + ")";
    }

    @Override
    public String toDataFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + this.description + "by " + this.afterBy;
    }
}
