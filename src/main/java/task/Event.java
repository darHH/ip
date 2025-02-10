package task;

/**
 * Represents an Event task, which includes a description, start time, and end time.
 * <p>
 * This class extends the Task class and adds functionality for handling time-bound events.
 */
public class Event extends Task {

    private String afterFrom;
    private String afterTo;
    private String description;

    /**
     * Constructs an Event task and extracts its time range.
     * <p>
     * Calls {@code parseDescription} to extract the event details.
     *
     * @param description The event description, including the "from" and "to" time range.
     */
    public Event(String description) {
        super(description);
        parseDescription(description);
    }

    /**
     * Parses the event description to extract the time range.
     * <p>
     * Splits the input based on the "from" keyword to extract the start time 
     * and the "to" keyword to extract the end time. If either is missing, 
     * a default value ("-") is assigned.
     *
     * @param description The input string containing the event details and time range.
     */
    private void parseDescription(String description) {
        String[] descriptionPartOne = description.split("from");
        this.description = descriptionPartOne[0];
        if (descriptionPartOne.length > 1) {
            String[] descriptionPartTwo = descriptionPartOne[1].split("to");
            this.afterFrom = descriptionPartTwo[0].trim();
            if (descriptionPartTwo.length > 1) {
                this.afterTo = descriptionPartTwo[1].trim();
            } else {
                this.afterTo = "-";
            }
        } else {
            this.afterFrom = "-";
            this.afterTo = "-";

        }
        System.out.println("Event task has been added:\n  " + this.toString());
        System.out.println("Now you have " + Task.getTotalTasks() + " task(s) in your list.\n");
    }

    @Override
    public String toString() {
        return "[E][" + this.getStatusIcon() + "] " + this.description
                + "(From: " + this.afterFrom + " To: " + this.afterTo + ")";
    }

    @Override
    public String toDataFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + this.description + "from" + this.afterFrom + "to " + afterTo;
    }
}
