public class Deadline extends Task {

    String afterBy;
    String description;

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
        System.out.println("Gotcha! This task has been added:\n[D][ ] " + this.description + "(By: " + afterBy + ")\n" + "Now you have " + Task.getTotalTasks() + " task(s) in your list.\n"); 
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