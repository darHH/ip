public class ToDo extends Task {

    protected String by;

    public ToDo(String description) {
        super(description);
        System.out.println("Gotcha! This task has been added:\n[T][ ] " + description + "\nNow you have " + Task.getTotalTasks() + " task(s) in your list.\n"); 
    }

    @Override
    public String toString() {
        return "[T][" + this.getStatusIcon() + "] " + description;
    }
}
