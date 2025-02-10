package task;

/**
 * Represents a ToDo task, which contains only a description and a status
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task with a description.
     * <p>
     * @param description The task description.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T][" + this.getStatusIcon() + "] " + description;
    }

    @Override
    public String toDataFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + this.description;
    }
}
