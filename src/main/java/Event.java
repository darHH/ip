public class Event extends Task {

    String afterFrom;
    String afterTo;
    String description;

    public Event(String description) {
        super(description);
        String[] descriptionPartOne = description.split("from");
        this.description = descriptionPartOne[0];
        if (descriptionPartOne.length > 1) {
            String[] descriptionPartTwo= descriptionPartOne[1].split("to");
            this.afterFrom = descriptionPartTwo[0];
            if (descriptionPartTwo.length > 1) {
                this.afterTo = descriptionPartTwo[1].trim();
            } else {
                this.afterTo = ""; 
            }
        } else {
            this.afterFrom = ""; 
            this.afterFrom = "";
        }
        System.out.println("Gotcha! This task has been added:\n[E][ ] " + this.description + "(From: " + this.afterFrom + " To: " + this.afterTo + ")\nNow you have " + Task.getTotalTasks() + " task(s) in your list.\n"); 
    }
    
    @Override
    public String toString() {
        return "[E][" + this.getStatusIcon() + "] " + this.description + "(From:" + this.afterFrom + "To: " + this.afterTo + ")";
    }
}