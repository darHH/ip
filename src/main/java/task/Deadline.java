package task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Deadline task, which includes a description and a deadline with both date and time.
 */
public class Deadline extends Task {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter DISPLAY_TIME_FORMATTER = DateTimeFormatter.ofPattern("ha");

    private String afterBy;
    private String description;
    private LocalDate deadlineDate;
    private LocalTime deadlineTime;

    /**
     * Constructs a Deadline task and extracts its deadline details.
     *
     * @param description The task description, including the "by" keyword followed by the deadline.
     * @throws IllegalArgumentException If the deadline format is invalid.
     */
    public Deadline(String description) throws IllegalArgumentException {
        super(description);
        parseDescription(description);
    }

    /**
     * Parses the task description to extract the deadline details.
     *
     * @param description The input string containing the task details and deadline.
     * @throws IllegalArgumentException If the deadline format is invalid.
     */
    private void parseDescription(String description) {
        String[] descriptionParts = description.split("by");
        this.description = descriptionParts[0].trim();
        this.afterBy = (descriptionParts.length > 1) ? descriptionParts[1].trim() : "-";
        if (!extractAndSaveDateTime(afterBy)) {
            decrementTotalTasksCount();
            throw new IllegalArgumentException("Invalid date or time format. "
                    + "Declare date and time after 'by' in DD/MM/YYYY and/or HHMM format.");
        }
    }

    /**
     * Extracts and saves a valid date and/or time from the given description.
     *
     * @param description The input string containing date and/or time.
     * @return true if a valid date or time is found, false otherwise.
     */
    private boolean extractAndSaveDateTime(String description) {
        List<String> numberSequences = extractNumericSequences(description);
        return parseDateTime(numberSequences);
    }

    /**
     * Extracts numeric sequences from a given string, capturing potential date or time values.
     *
     * @param description The input string that may contain date and/or time.
     * @return A list of extracted numeric sequences.
     */
    private List<String> extractNumericSequences(String description) {
        List<String> numberSequences = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        for (char ch : description.toCharArray()) {
            if (Character.isDigit(ch) || ch == '/') {
                currentNumber.append(ch);
            } else {
                if (currentNumber.length() > 0) {
                    numberSequences.add(currentNumber.toString());
                    currentNumber.setLength(0);
                }
            }
        }

        if (currentNumber.length() > 0) {
            numberSequences.add(currentNumber.toString());
        }

        return numberSequences;
    }

    /**
     * Parses the extracted numeric sequences to determine if they represent a valid date or time.
     *
     * @param numberSequences A list of extracted numeric sequences to be checked.
     * @return true if a valid date or time is found, false otherwise.
     */
    private boolean parseDateTime(List<String> numberSequences) {
        boolean isValidDateOrTime = false;

        for (String element : numberSequences) {
            try {
                if (element.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    deadlineDate = LocalDate.parse(element, DATE_FORMATTER);
                    isValidDateOrTime = true;
                } else if (element.matches("\\d{4}")) {
                    deadlineTime = LocalTime.parse(element, TIME_FORMATTER);
                    isValidDateOrTime = true;
                }
            } catch (DateTimeParseException e) {
                return false; // Invalid date/time format
            }
        }

        return isValidDateOrTime;
    }

    public String getDeadlineDate() {
        return (deadlineDate != null) ? deadlineDate.format(DISPLAY_DATE_FORMATTER) : "No date set";
    }

    public String getDeadlineTime() {
        return (deadlineTime != null) ? deadlineTime.format(DISPLAY_TIME_FORMATTER) : "No time set";
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
