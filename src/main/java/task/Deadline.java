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
     * Constructs a Deadline task with a description and deadline.
     * <p>
     * The deadline is specified after the "by" keyword. The date and time are
     * saved as LocalDate and LocalTime objects respectively.
     * If the date or time is invalid or missing,
     * default values are assigned, and an error message is shown.
     *
     * @param description The full description of the deadline task,
     *      including the "by" keyword followed by the date and time.
     */
    public Deadline(String description) throws IllegalArgumentException {
        super(description);
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

        boolean isValidDateOrTime = false;

        // Check extracted sequences for valid date or time formats
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
        return this.deadlineTime.format(DateTimeFormatter.ofPattern("ha"));
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
