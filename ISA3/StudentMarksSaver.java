import java.util.*;
import java.io.*;

// Interface
interface Printable {
    void display();
}

// Custom Exception
class InvalidMarksException extends Exception {
    InvalidMarksException(String message) {
        super(message);
    }
}

// Student class
class Student implements Printable {

    String name;
    int marks;

    Student(String name, int marks) throws InvalidMarksException {

        if (marks < 0 || marks > 100) {
            throw new InvalidMarksException("Invalid Marks! Marks should be between 0 and 100.");
        }

        this.name = name;
        this.marks = marks;
    }

    // Display method
    @Override
    public void display() {
        System.out.println("Name  : " + name);
        System.out.println("Marks : " + marks);
    }
}

// Main class
public class StudentMarksSaver {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Input student details
        for (int i = 0; i < n; i++) {

            System.out.println("\nStudent " + (i + 1));

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Marks: ");
            int marks = sc.nextInt();
            sc.nextLine();

            try {
                Student s = new Student(name, marks);
                students.add(s);
            } catch (InvalidMarksException e) {
                System.out.println(e.getMessage());
            }
        }

        // Display student details
        System.out.println("\nStudent Details:");

        for (Student s : students) {
            s.display();
        }

        // Write to file
        try {
            FileWriter fw = new FileWriter("students.txt");

            for (Student s : students) {
                fw.write(s.name + " " + s.marks + "\n");
            }

            fw.close();
            System.out.println("\nStudent details written to students.txt");

        } catch (IOException e) {
            System.out.println("File Error!");
        }

        sc.close();
    }
}