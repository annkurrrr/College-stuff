import java.util.Scanner;

// Abstract class
abstract class Course {
    String courseName;

    Course(String courseName) {
        this.courseName = courseName;
    }

    // Abstract method
    abstract void getCourseType();
}

// OnlineCourse subclass
class OnlineCourse extends Course {

    String[] students = new String[5];

    OnlineCourse(String courseName, String[] students) {
        super(courseName);
        this.students = students;
    }

    @Override
    void getCourseType() {
        System.out.println("Course Type: Online Course");
    }

    // Display students in lowercase
    void listStudents() {
        System.out.println("\nEnrolled Students:");
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].toLowerCase());
        }
    }

    // Find student
    void findStudent(String name) {
        boolean found = false;

        for (int i = 0; i < students.length; i++) {
            if (students[i].equalsIgnoreCase(name)) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println(name + " found in the course.");
        } else {
            System.out.println(name + " not found.");
        }
    }

    // Check if course is full
    void checkCourseFull() {
        boolean full = true;

        for (int i = 0; i < students.length; i++) {
            if (students[i].trim().isEmpty()) {
                full = false;
                break;
            }
        }

        if (full) {
            System.out.println("Course Full");
        } else {
            System.out.println("Seats Available");
        }
    }
}

// Main class
public class SchoolCourseEnrollment {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Course Name: ");
        String courseName = sc.nextLine();

        String[] students = new String[5];

        System.out.println("Enter names of 5 students:");
        for (int i = 0; i < 5; i++) {
            System.out.print("Student " + (i + 1) + ": ");
            students[i] = sc.nextLine();
        }

        // Polymorphism
        Course course = new OnlineCourse(courseName, students);

        course.getCourseType();

        // Downcasting
        OnlineCourse oc = (OnlineCourse) course;

        oc.listStudents();

        System.out.print("\nEnter student name to search: ");
        String search = sc.nextLine();

        oc.findStudent(search);

        oc.checkCourseFull();

        sc.close();
    }
}