public abstract class Task {
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
    
    public abstract String toDataFormat();

    public static Task fromDataFormat(String dataLine) {
        String[] parts = dataLine.split(" | ");
        boolean isDone = parts[1].equals("1");
        String dataDescription = parts[2];
        Task newTask = new ToDo(dataDescription);
        if (isDone) {
            newTask.setMark();
        } else {
            newTask.setUnmark();
        }        
        return newTask;
    }

}
    
