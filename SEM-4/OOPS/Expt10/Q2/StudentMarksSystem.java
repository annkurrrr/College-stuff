import java.util.*;

// Custom Checked Exception
class InvalidMarksException extends Exception {
    private int marks;

    // Default Constructor
    public InvalidMarksException() {
        marks = 0;
    }

    // Parameterized Constructor
    public InvalidMarksException(int marks) {
        this.marks = marks;
    }

    // toString method
    public String toString() {
        return "Invalid Marks: " + marks + ". Marks should be between 0 and 100.";
    }
}

// Student Class
class Student {
    private String name;
    private int marks;

    // Default Constructor
    public Student() {
        name = "Unknown";
        marks = 0;
    }

    // Parameterized Constructor
    public Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    // Grade Calculation Method
    public String calculateGrade() {
        if (marks >= 90) return "A";
        else if (marks >= 75) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 40) return "D";
        else return "F";
    }

    // toString method
    public String toString() {
        return "Student Name: " + name +
               "\nMarks: " + marks +
               "\nGrade: " + calculateGrade();
    }
}

// Main Class
public class StudentMarksSystem {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Input Name
            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Student name cannot be empty");
            }

            // Input Marks
            System.out.print("Enter marks (0-100): ");
            int marks = sc.nextInt();

            if (marks < 0 || marks > 100) {
                throw new InvalidMarksException(marks);
            }

            // Create Object
            Student s = new Student(name, marks);

            System.out.println("\n--- Student Details ---");
            System.out.println(s);
        }

        // Catch Blocks
        catch (InvalidMarksException e) {
            System.out.println("Checked Exception Caught:");
            System.out.println(e);
        }
        catch (InputMismatchException e) {
            System.out.println("Input Error: Please enter integer value for marks.");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Unchecked Exception Caught:");
            System.out.println(e.getMessage());
        }

        // Finally Block
        finally {
            System.out.println("\n--- Processing Completed ---");
            sc.close();
        }
    }
}