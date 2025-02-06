package command;
import java.util.ArrayList;

import dar.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

/**
 * The CommandManager class manages the list of tasks and handles commands to manipulate them.
 * <p>
 * The task list is loaded from and saved to a `Storage` object, ensuring persistence of tasks across program sessions.
 * <p>
 * The class interprets user commands and interacts with the task list to perform the requested actions.
 */
public class CommandManager {
    private final ArrayList<Task> taskList;
    private final Storage storage;

    /**
     * Constructs a CommandManager instance that manages the list of tasks.
     *
     * @param storage The storage object used to load and save tasks.
     */
    public CommandManager(Storage storage) {
        this.taskList = new ArrayList<>(storage.loadTasks()); // Load tasks from storage
        this.storage = storage;
    }

    /**
     * Returns string of all tasks in the list in order of its task number.
     * <p>
     * If the task list is empty, return a String message indicating no tasks.
     * <p>
     * This method does not return anything.
     */
    public String listTasks() {
        if (taskList.isEmpty()) {
            return "Nice, your list is empty, you deserve a break! :)\n";
        } else {
            StringBuilder response = new StringBuilder("Here's your list, better get going!\n");
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                response.append(task.getTaskNumber()).append(". ").append(task).append("\n");
            }
            return response.toString();
        }
    }

    /**
     * Marks the specified task as done, then returns a String of the marked task.
     * <p>
     * If the given task number is invalid, an error message is shown.
     *
     * @param input The task number as a string
     */
    public String markTask(String input) {
        try {
            int taskNumber = Integer.parseInt(input);
            Task task = taskList.get(taskNumber - 1);
            task.setMark();
            storage.saveTasks(taskList);
            
            return "Good job, one less task to worry about:\n" + task + "\n";
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a valid task number to mark.";
        } catch (IndexOutOfBoundsException e) {
            return "Invalid task number. Please choose a valid task.";
        } catch (Exception e) {
            return "An error occurred while marking the task.";
        }
    }

    /**
     * Unmarks the specified task as not done, then displays the unmarked task.
     * <p>
     * If the given task number is invalid, an error message is displayed.
     *
     * @param input The task number as a string
     */
    public String unmarkTask(String input) {
        try {
            int taskNumber = Integer.parseInt(input);
            Task task = taskList.get(taskNumber - 1);
            task.setUnmark();
            storage.saveTasks(taskList);
            
            return "Oh okay, this task has been unmarked:\n" + task + "\n";
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a valid task number to unmark.";
        } catch (IndexOutOfBoundsException e) {
            return "Invalid task number. Please choose a valid task.";
        } catch (Exception e) {
            return "An error occurred while unmarking the task.";
        }
    }

    /**
     * Adds a new ToDo task to the task list and saves it to storage.
     * <p>
     * If the description is empty or contains only whitespace, an error message is displayed.
     *
     * @param description The description of the ToDo task.
     */
    public String addTodo(String description) {
        if (!description.trim().isEmpty()) {
            Task task = new ToDo(description);
            taskList.add(task);
            storage.saveTasks(taskList);
            
            return "Got it! I've added this todo:\n" + task + "\n";
        } else {
            return "The description of a todo task cannot be empty :<\n";
        }
    }

    /**
     * Adds a new Deadline task to the task list and saves it to storage.
     * <p>
     * The description should include both the task details and the deadline in a specific format
     * (after "by" in DD/MM/YYYY and HHMM).
     * If the description is empty or contains only whitespace, an error message is displayed.
     *
     * @param description The description of the Deadline task, including the deadline date/time.
     */
    public String addDeadline(String description) {
        if (!description.trim().isEmpty()) {
            Task task = new Deadline(description);
            taskList.add(task);
            storage.saveTasks(taskList);
            
            return "Got it! I've added this deadline:\n" + task + "\n";
        } else {
            return "The description of a deadline task cannot be empty :<\n";
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
    public String addEvent(String description) {
        if (!description.trim().isEmpty()) {
            Task task = new Event(description);
            taskList.add(task);
            storage.saveTasks(taskList);
            
            return "Got it! I've added this event:\n" + task + "\n";
        } else {
            return "The description of an event task cannot be empty :<\n";
        }
    }

    /**
     * Deletes a task from the task list by its task number and updates the remaining tasks.
     * <p>
     * If the input task number is invalid (non-numeric or out of range), an error message is displayed.
     *
     * @param input The task number as a string.
     */
    public String deleteTask(String input) {
        try {
            int taskNumber = Integer.parseInt(input);
            Task task = taskList.remove(taskNumber - 1);
            task.decrementTotalTasksCount();
    
            // Update remaining task numbers
            for (int i = 0; i < taskList.size(); i++) {
                taskList.get(i).updateTaskNumber(i + 1);
            }
    
            storage.saveTasks(taskList);
    
            return "Roger that, this task has been removed:\n" + task +
                   "\nNow you have " + Task.getTotalTasks() + " task(s) in your list.\n";
    
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a valid task number for deletion.";
        } catch (IndexOutOfBoundsException e) {
            return "Invalid task number. Please choose a valid task.";
        } catch (Exception e) {
            return "An error occurred while deleting the task.";
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
    public String findTasks(String matchWord) {
        StringBuilder response = new StringBuilder("You looking for these?\n");
        response.append("(Numbers represent that task's number, for deleting and marking etc.)\n\n");
    
        matchWord = matchWord.toLowerCase();
        boolean found = false;
    
        for (Task task : taskList) {
            if (task.getDescription().toLowerCase().contains(matchWord)) {
                response.append(task.getTaskNumber()).append(". ").append(task).append("\n");
                found = true;
            }
        }
    
        if (!found) {
            response.append("You have no matching tasks :(\n");
        }
    
        return response.toString();
    }

}
