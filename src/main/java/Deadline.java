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
        // System.out.println(this.description);
        if (descriptionParts.length > 1) {
            this.afterBy = descriptionParts[1].trim();
        } else {
            this.afterBy = ""; 
        }
        saveDateTime(afterBy);
        System.out.println("Gotcha! This task has been added:\n  " + this.toString());
        System.out.println("Now you have " + Task.getTotalTasks() + " task(s) in your list.\n"); 
    }
    
    // Function to save date and time, date has to be in DD/MM/YYYY, time has to be in 24 hour DDMM format
    // Extract consecutive digits and check if it matches above two formats, if it does save as Local Date or Time, else send a message. 
    public void saveDateTime(String description) {
        ArrayList<String> numberSequence = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();
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
        for (String element : numberSequence) {
            if (element.matches("\\d{2}/\\d{2}/\\d{4}")) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                deadlineDate = LocalDate.parse(element, dateFormatter);
            } else if (element.matches("\\d{4}")) {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
                deadlineTime = LocalTime.parse(element, timeFormatter);
            } else {
                System.out.println("Invalid format: " + element);
                System.out.println("Dates should be in DD/MM/YYYY, Time should be in 24hr HHMM format, exactly!");
            }
        }
        if (deadlineDate != null || deadlineTime != null) {
            System.out.println("Deadline saved with:");
            if (deadlineDate != null) {
                System.out.println("  Date: " + deadlineDate);
            }
            if (deadlineTime != null) {
                System.out.println("  Time: " + deadlineTime);
            }
        }
    }



    @Override
    public String toString() {
        return "[D][" + this.getStatusIcon() + "] " + this.description + "(By: " + afterBy + ")";
    }

    @Override
    public String toDataFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + this.description + "by " + this.afterBy;
    }
}