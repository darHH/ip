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
     * Constructs an Event task with a description and time range.
     * <p>
     * The description should contain the keyword "from" to specify the start time
     * and "to" to specify the end time. If these are missing, default value "-" is assigned.
     *
     * @param description The full event description, including start ("from") and end ("to") times.
     */
    public Event(String description) {
        super(description);
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
