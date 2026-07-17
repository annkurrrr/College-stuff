import java.util.*;
import java.io.*;

// Interface
interface Trackable {
    void markAttendance(String name) throws StudentNotFoundException;
}

// Custom Exception
class StudentNotFoundException extends Exception {
    StudentNotFoundException(String message) {
        super(message);
    }
}

// Student class
class Student implements Trackable {

    LinkedList<String> classList = new LinkedList<>();
    LinkedList<String> presentStudents = new LinkedList<>();

    // Constructor
    Student() {
        classList.add("Ankur");
        classList.add("Rahul");
        classList.add("Karan");
        classList.add("Priya");
        classList.add("Neha");
    }

    // Mark attendance
    @Override
    public void markAttendance(String name) throws StudentNotFoundException {

        if (classList.contains(name)) {
            presentStudents.add(name);
            System.out.println(name + " marked Present.");
        } else {
            throw new StudentNotFoundException("Student Not Found!");
        }
    }

    // Display attendance
    void displayAttendance() {
        System.out.println("\nPresent Students:");
        for (String s : presentStudents) {
            System.out.println(s);
        }
    }

    // Write attendance to file
    void writeToFile() {
        try {
            FileWriter fw = new FileWriter("attendance.txt");

            for (String s : presentStudents) {
                fw.write(s + "\n");
            }

            fw.close();
            System.out.println("\nAttendance written to attendance.txt");

        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }
}

// Main class
public class AttendanceTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Student s = new Student();

        System.out.print("Enter number of students present: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {

            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            try {
                s.markAttendance(name);
            } catch (StudentNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        s.displayAttendance();

        s.writeToFile();

        sc.close();
    }
}