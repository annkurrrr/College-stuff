import java.util.Scanner;

// Abstract class
abstract class Student {
    String name;

    Student(String name) {
        this.name = name;
    }

    // Abstract method
    abstract int computeGrade();
}

// UndergraduateStudent class
class UndergraduateStudent extends Student {
    int marks;

    UndergraduateStudent(String name, int marks) {
        super(name);
        this.marks = marks;
    }

    @Override
    int computeGrade() {
        return marks;
    }
}

// GraduateStudent class
class GraduateStudent extends Student {
    int marks;

    GraduateStudent(String name, int marks) {
        super(name);
        this.marks = marks;
    }

    @Override
    int computeGrade() {
        return marks;
    }
}

// Main class
public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        Student[] students = new Student[n];

        int passCount = 0;

        // Input
        for (int i = 0; i < n; i++) {

            System.out.println("\nStudent " + (i + 1));

            System.out.print("Enter student type (Undergraduate/Graduate): ");
            String type = sc.nextLine();

            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter marks: ");
            int marks = sc.nextInt();
            sc.nextLine();

            if (type.equalsIgnoreCase("Undergraduate")) {
                students[i] = new UndergraduateStudent(name, marks);
            } else {
                students[i] = new GraduateStudent(name, marks);
            }
        }

        System.out.println("\nStudent Grades:");

        // Compute grades polymorphically
        for (int i = 0; i < students.length; i++) {

            int grade = students[i].computeGrade();

            System.out.println(students[i].name + " : " + grade);

            // Greeting if name contains 'K'
            if (students[i].name.toUpperCase().contains("K")) {
                System.out.println("Hello " + students[i].name + "!");
            }

            // Count passed students
            if (grade >= 50) {
                passCount++;
            }
        }

        System.out.println("\nNumber of Students Passed = " + passCount);

        sc.close();
    }
}