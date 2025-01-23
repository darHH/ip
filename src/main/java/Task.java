public class Task {
    protected String description;
    protected boolean isDone;
    static int totalTasks = 0;
    private int taskNumber;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        totalTasks++;
        this.taskNumber = totalTasks;
        // System.out.println("Gotcha! Added: " + description + "\n"); 
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void setMark() {
        this.isDone = true;
    }

    public void setUnmark() {
        this.isDone = false;
    }

    public int getTaskNumber() {
        return this.taskNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public static int getTotalTasks() {
        return totalTasks;
    }

    public void updateTaskNumber(int newTaskNumber) {
        this.taskNumber = newTaskNumber;
    }

    public void totalTasksMinusOne() {
        totalTasks--;
    }
}
    
