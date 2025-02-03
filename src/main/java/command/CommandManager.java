package command;
import java.util.ArrayList;

import dar.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

public class CommandManager {
    private final ArrayList<Task> taskList;
    private final Storage storage;

    public CommandManager(Storage storage) {
        this.taskList = new ArrayList<>(storage.loadTasks()); // Load tasks from storage
        this.storage = storage;
    }

    /**
     * Displays all tasks in the list in order of its task number.
     * <p>
     * If the task list is empty, displays a message indicating no tasks.
     * <p>
     * This method does not return anything.
     */
    public void listTasks() {
        if (taskList.isEmpty()) {
            System.out.println("Nice, your list is empty, you deserve a break! :)\n");
        } else {
            System.out.println("Here's your list, better get going!");
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                System.out.println(task.getTaskNumber() + "." + task);
            }
            System.out.println();
        }
    }

    /**
     * Marks the specified task as done, then displays the marked task.
     * <p>
     * If the given task number is invalid, an error message is shown.
     *
     * @param input The task number as a string
     */    
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

    /**
     * Unmarks the specified task as not done, then displays the unmarked task.
     * <p>
     * If the given task number is invalid, an error message is displayed.
     *
     * @param input The task number as a string
     */    
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

    /**
     * Adds a new ToDo task to the task list and saves it to storage.
     * <p>
     * If the description is empty or contains only whitespace, an error message is displayed.
     *
     * @param description The description of the ToDo task.
     */
    public void addTodo(String description) {
        if (!description.trim().isEmpty()) {
            Task task = new ToDo(description);
            taskList.add(task);
            storage.saveTasks(taskList);
        } else {
            System.out.println("The description of a todo task cannot be empty :<\n");
        }
    }

    /**
     * Adds a new Deadline task to the task list and saves it to storage.
     * <p>
     * The description should include both the task details and the deadline in a specific format (after "by" in DD/MM/YYYY and HHMM).
     * If the description is empty or contains only whitespace, an error message is displayed.
     *
     * @param description The description of the Deadline task, including the deadline date/time.
     */
    public void addDeadline(String description) {
        if (!description.trim().isEmpty()) {
            Task task = new Deadline(description);
            taskList.add(task);
            storage.saveTasks(taskList);
        } else {
            System.out.println("The description of a deadline task cannot be empty :<\n");
        }
    }

    /**
     * Adds a new Event task to the task list and saves it to storage.
     * <p>
     * The description should include the event details along with its start and end time (format with "from" and "to").
     * If the description is empty or contains only whitespace, an error message is displayed.
     *
     * @param description The description of the Event task, including start and end time.
     */
    public void addEvent(String description) {
        if (!description.trim().isEmpty()) {
            Task task = new Event(description);
            taskList.add(task);
            storage.saveTasks(taskList);
        } else {
            System.out.println("The description of an event task cannot be empty :<\n");
        }
    }

    /**
     * Deletes a task from the task list by its task number and updates the remaining tasks.
     * <p>
     * If the input task number is invalid (non-numeric or out of range), an error message is displayed.
     *
     * @param input The task number as a string.
     */
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

    /**
     * Finds and displays tasks that contain the specified keyword.
     * <p>
     * @param matchWord The keyword to search for in task descriptions.
     */
    public void findTasks(String matchWord) {
        System.out.println("You looking for these?");
        System.out.println("(Numbers represent that task's number, for deleting and marking etc.)");
        matchWord = matchWord.toLowerCase(); 
        boolean found = false;
        for (Task task : taskList) {
            if (task.getDescription().toLowerCase().contains(matchWord)) {
                System.out.println(task.getTaskNumber() + "." + task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("You have no matching tasks :(");
        }
        System.out.println();
    }

}
