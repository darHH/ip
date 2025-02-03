package task;

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

    /**
     * Converts a String (e.g from Storage and dardata.txt) from data format into a Task object (either T, D, E).
     * <p>
     * @param dataLine A string representing a task in data format.
     * @return The corresponding Task object.
     * @throws IllegalArgumentException If the task type is unknown.
     */
    public static Task fromDataFormat(String dataLine) {
        String[] parts = dataLine.split(" \\| ");
        String typeTask = parts[0];
        boolean isDone = parts[1].equals("1");
        String dataDescription = parts[2];
        Task newTask;

        if (typeTask.equals("T")) {
            newTask = new ToDo(dataDescription);
        } else if (typeTask.equals("D")) {
            newTask = new Deadline(dataDescription);
        } else if (typeTask.equals("E")) {
            newTask = new Event(dataDescription);
        } else {
            throw new IllegalArgumentException("Unknown task type: " + typeTask);
        }

        if (isDone) {
            newTask.setMark();
        } else {
            newTask.setUnmark();
        }

        return newTask;
    }

}
    
