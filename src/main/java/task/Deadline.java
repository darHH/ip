package task;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Deadline extends Task {

    String afterBy;
    String description;
    LocalDate deadlineDate;
    LocalTime deadlineTime;

    public Deadline(String description) {
        super(description);
        String[] descriptionParts = description.split("by");
        this.description = descriptionParts[0];
        // TEST System.out.println(this.description);
        this.afterBy = (descriptionParts.length > 1) ? descriptionParts[1].trim() : "-";

        saveDateTime(afterBy);

        System.out.println("Deadline task has been added:\n  " + this.toString());
        System.out.println("Now you have " + Task.getTotalTasks() + " task(s) in your list.\n");
    }
    
    /**
     * Extracts and saves a valid date and/or time from the given description.
     * <p>
     * This method scans the input string for numerical sequences that match 
     * either the date format (DD/MM/YYYY) or the time format (HHMM).
     * Only if a valid date or time is found, it is saved as a `LocalDate` or `LocalTime`, respectively.
     * Otherwise, a message is displayed indicating an invalid format
     *
     * @param description The input string containing date and/or time.
     */
    public void saveDateTime(String description) {
        ArrayList<String> numberSequence = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        // Extract the digits and slashes that might represent dates or times
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
        boolean validDateOrTime = false;

        // Check extracted sequences for valid date or time formats
        for (String element : numberSequence) {
            if (element.matches("\\d{2}/\\d{2}/\\d{4}")) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                deadlineDate = LocalDate.parse(element, dateFormatter);
                validDateOrTime = true;
            } else if (element.matches("\\d{4}")) {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
                deadlineTime = LocalTime.parse(element, timeFormatter);
                validDateOrTime = true;
            } 
        }

        // Handle invalid input cases
        if (!validDateOrTime) {
            afterBy = "-";
            System.out.println("(Invalid Date/Time - Date should be in DD/MM/YYYY and Time in HHMM format)");
        } else {
            System.out.println("Deadline saved with:");
            if (deadlineDate != null) {
                System.out.println("  Date: " + deadlineDate);
            }
            if (deadlineTime != null) {
                System.out.println("  Time: " + deadlineTime);
            }
        }
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
    
        return "[D][" + this.getStatusIcon() + "] " + this.description + " (By: " + formattedDate + " " + formattedTime + ")";
    }

    @Override
    public String toDataFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + this.description + "by " + this.afterBy;
    }
}