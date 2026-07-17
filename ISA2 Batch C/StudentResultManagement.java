import java.util.Scanner;

// Abstract class
abstract class Person {
    String name;

    Person(String name) {
        this.name = name;
    }

    // Abstract method
    abstract void getRole();
}

// Student subclass
class Student extends Person {

    int[] marks = new int[3];

    Student(String name, int[] marks) {
        super(name);
        this.marks = marks;
    }

    @Override
    void getRole() {
        System.out.println("Role : Student");
    }

    // Calculate average
    double calculateAverage() {
        int sum = 0;
        for (int i = 0; i < marks.length; i++) {
            sum += marks[i];
        }
        return sum / 3.0;
    }

    // Assign letter grade
    char getGrade() {
        double avg = calculateAverage();

        if (avg >= 90)
            return 'A';
        else if (avg >= 75)
            return 'B';
        else if (avg >= 50)
            return 'C';
        else
            return 'F';
    }

    // Count vowels
    void countVowels() {
        int count = 0;
        String s = name.toLowerCase();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == 'a' || ch == 'e' || ch == 'i' ||
                ch == 'o' || ch == 'u') {
                count++;
            }
        }

        System.out.println("Vowels in Name : " + count);
    }

    // Reverse name
    void reverseName() {
        String rev = "";

        for (int i = name.length() - 1; i >= 0; i--) {
            rev += name.charAt(i);
        }

        System.out.println("Reversed Name : " + rev);
    }

    // Report card
    void printReportCard() {
        System.out.println("\n----- Report Card -----");
        System.out.println("Name : " + name);
        System.out.println("Average : " + calculateAverage());
        System.out.println("Grade : " + getGrade());
        countVowels();
        reverseName();
    }
}

// Teacher subclass
class Teacher extends Person {

    Teacher(String name) {
        super(name);
    }

    @Override
    void getRole() {
        System.out.println("Role : Teacher");
    }
}

// Main class
public class StudentResultManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        Student[] students = new Student[n];

        // Input students
        for (int i = 0; i < n; i++) {

            System.out.println("\nStudent " + (i + 1));

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            int[] marks = new int[3];

            System.out.println("Enter marks of 3 subjects:");

            for (int j = 0; j < 3; j++) {
                System.out.print("Subject " + (j + 1) + ": ");
                marks[j] = sc.nextInt();
            }
            sc.nextLine();

            students[i] = new Student(name, marks);
        }

        // Polymorphism
        System.out.println("\n====== REPORT CARDS ======");

        for (int i = 0; i < students.length; i++) {

            Person p = students[i];

            p.getRole();

            Student s = (Student) p;

            s.printReportCard();
        }

        // Search student
        System.out.print("\nEnter student name to search: ");
        String search = sc.nextLine();

        boolean found = false;

        for (int i = 0; i < students.length; i++) {

            if (students[i].name.equalsIgnoreCase(search)) {
                System.out.println(search + " found.");
                students[i].printReportCard();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println(search + " not found.");
        }

        sc.close();
    }
}