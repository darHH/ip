package command;
import java.util.ArrayList;
import java.util.List;

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
        }

        return "Here's your list, better get going!\n"
               + taskList.stream()
                   .map(task -> task.getTaskNumber() + ". " + task)
                   .reduce("", (a, b) -> a + b + "\n");
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
        if (description.trim().isEmpty()) {
            return "The description of a todo task cannot be empty :<\n";
        }
        Task task = new ToDo(description);
        taskList.add(task);
        storage.saveTasks(taskList);
        if (isDuplicate(task)) {
            return "This task has duplicates, you can delete tasks using 'delete (task no.)'\n\n" + findDuplicates(task);
        } else {
            return "Got it! I've added this todo:\n" + Task.getTotalTasks() + ". " + task + "\n";
        }
    }

    /**
     * Adds a new Deadline task to the task list and saves it to storage.
     * If the description is empty or the date/time format is invalid, an error message is returned.
     *
     * @param description The description of the Deadline task, including the deadline date/time.
     * @return A success message or an error message if the input is invalid.
     */
    public String addDeadline(String description) {
        if (description.trim().isEmpty()) {
            return "The description of a deadline task cannot be empty :<\n";
        }

        try {
            Task task = new Deadline(description);
            taskList.add(task);
            storage.saveTasks(taskList);
            if (isDuplicate(task)) {
                return "This task has duplicates, you can delete tasks using 'delete (task no.)'\n\n" + findDuplicates(task);
            } else {
                return "Got it! I've added this deadline:\n" + Task.getTotalTasks() + ". " + task + "\n";
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
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
        if (description.trim().isEmpty()) {
            return "The description of an event task cannot be empty :<\n";
        }
        Task task = new Event(description);
        taskList.add(task);
        storage.saveTasks(taskList);
        if (isDuplicate(task)) {
            return "This task has duplicates, you can delete tasks using 'delete (task no.)'\n\n" + findDuplicates(task);
        } else {
            return "Got it! I've added this event:\n" + Task.getTotalTasks() + ". " + task + "\n";
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

            return "Roger that, this task has been removed:\n" + task
                + "\nNow you have " + Task.getTotalTasks() + " task(s) in your list.\n";

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
        String header = "You looking for these?\n"
                       + "(Numbers represent that task's number, for deleting and marking etc.)\n\n";

        String matches = taskList.stream()
            .filter(task -> task.getDescription().toLowerCase().contains(matchWord.toLowerCase()))
            .map(task -> task.getTaskNumber() + ". " + task)
            .reduce("", (a, b) -> a + b + "\n");

        return header + (matches.isEmpty() ? "You have no matching tasks :(\n" : matches);
    }

    /**
     * Finds and returns a list of duplicate tasks that have the same description as the given task.
     * <p>
     * @param newTask The task to check for duplicates.
     * @return A string listing all duplicate tasks, including their task numbers.
     */
    public String findDuplicates(Task newTask) {
        List<Task> duplicates = new ArrayList<>();
    
        for (Task task : taskList) { 
            if (task.getDescription().equals(newTask.getDescription())) {  
                duplicates.add(task);
            }
        }
    
        if (duplicates.isEmpty()) {
            return "No duplicate tasks found.";
        }
    
        StringBuilder result = new StringBuilder("Your duplicate tasks:\n");
        for (Task task : duplicates) {
            result.append(task.getTaskNumber()).append(". ").append(task.toString()).append("\n"); 
        }
        return result.toString();
    }


    /**
     * Checks if the given task is a duplicate in the task list.
     * <p>
     * @param newTask The task to check for duplication.
     * @return {@code true} if there are multiple tasks with the same description, {@code false} otherwise.
     */
    public Boolean isDuplicate(Task newTask) {
        int count = 0;
        for (Task task : taskList) { 
            if (task.getDescription().equals(newTask.getDescription())) {
                count++;
            }
        }
        if (count > 1) {
            return true;
        }
        return false; 
    }
}
