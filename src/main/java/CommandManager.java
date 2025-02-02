import java.util.ArrayList;

public class CommandManager {
    private final ArrayList<Task> taskList;
    private final Storage storage;

    public CommandManager(Storage storage) {
        this.taskList = new ArrayList<>(storage.loadTasks()); // Load tasks from storage
        this.storage = storage;
    }

    public void listTasks() {
        System.out.println("Here's your list, better get going!");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.println(task.getTaskNumber() + "." + task);
        }
        System.out.println();
    }

    public void markTask(String input) {
        try {
            int taskNumber = Integer.parseInt(input);
            Task task = taskList.get(taskNumber - 1);
            task.setMark();
            storage.saveTasks(taskList);
            System.out.println("Good job, one less task to worry about:");
            System.out.println(task + "\n");
        } catch (Exception e) {
            System.out.println("Invalid task number for marking.");
        }
    }

    public void unmarkTask(String input) {
        try {
            int taskNumber = Integer.parseInt(input);
            Task task = taskList.get(taskNumber - 1);
            task.setUnmark();
            storage.saveTasks(taskList);
            System.out.println("Oh okay, this task has been unmarked:");
            System.out.println(task + "\n");
        } catch (Exception e) {
            System.out.println("Invalid task number for unmarking.");
        }
    }

    public void addTodo(String description) {
        if (!description.trim().isEmpty()) {
            Task task = new ToDo(description);
            taskList.add(task);
            storage.saveTasks(taskList);
        } else {
            System.out.println("The description of a todo task cannot be empty :<\n");
        }
    }

    public void addDeadline(String description) {
        if (!description.trim().isEmpty()) {
            Task task = new Deadline(description);
            taskList.add(task);
            storage.saveTasks(taskList);
        } else {
            System.out.println("The description of a deadline task cannot be empty :<\n");
        }
    }

    public void addEvent(String description) {
        if (!description.trim().isEmpty()) {
            Task task = new Event(description);
            taskList.add(task);
            storage.saveTasks(taskList);
        } else {
            System.out.println("The description of an event task cannot be empty :<\n");
        }
    }

    public void deleteTask(String input) {
        try {
            int taskNumber = Integer.parseInt(input);
            Task task = taskList.remove(taskNumber - 1);
            task.totalTasksMinusOne();
            for (int i = 0; i < taskList.size(); i++) {
                taskList.get(i).updateTaskNumber(i + 1); // Update remaining task numbers
            }
            storage.saveTasks(taskList);
            System.out.println("Roger that, this task has been removed:");
            System.out.println(task);
            System.out.println("Now you have " + Task.getTotalTasks() + " task(s) in your list.\n");
        } catch (Exception e) {
            System.out.println("Invalid task number for deletion.");
        }
    }
    
    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}