import java.util.*;
import java.io.*;

// Interface
interface Taskable {
    void addTask(String task);
    void completeTask() throws NoTasksException;
}

// Custom Exception
class NoTasksException extends Exception {
    NoTasksException(String message) {
        super(message);
    }
}

// TaskManager class
class TaskManager implements Taskable {

    LinkedList<String> tasks = new LinkedList<>();

    // Add task
    @Override
    public void addTask(String task) {
        tasks.add(task);
        System.out.println(task + " added successfully.");
    }

    // Complete task (remove from front)
    @Override
    public void completeTask() throws NoTasksException {

        if (tasks.isEmpty()) {
            throw new NoTasksException("No Tasks Available!");
        }

        System.out.println("Completed Task: " + tasks.removeFirst());
    }

    // Display pending tasks
    void displayTasks() {

        System.out.println("\nPending Tasks:");

        if (tasks.isEmpty()) {
            System.out.println("No Pending Tasks.");
        } else {
            for (String task : tasks) {
                System.out.println(task);
            }
        }
    }

    // Write pending tasks to file
    void writeToFile() {

        try {
            FileWriter fw = new FileWriter("tasks.txt");

            for (String task : tasks) {
                fw.write(task + "\n");
            }

            fw.close();
            System.out.println("\nPending tasks written to tasks.txt");

        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }
}

// Main class
public class SimpleTaskManager {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TaskManager tm = new TaskManager();

        System.out.print("Enter number of tasks: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Add tasks
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Task: ");
            String task = sc.nextLine();
            tm.addTask(task);
        }

        tm.displayTasks();

        // Complete tasks
        System.out.print("\nEnter number of tasks to complete: ");
        int complete = sc.nextInt();

        for (int i = 0; i < complete; i++) {
            try {
                tm.completeTask();
            } catch (NoTasksException e) {
                System.out.println(e.getMessage());
            }
        }

        tm.displayTasks();

        // Write pending tasks to file
        tm.writeToFile();

        sc.close();
    }
}