import java.io.*;
import java.util.Scanner;

public class Q3_CourseEnrollmentSystem {
    
    static class Course {
        private String courseID;
        private String courseName;
        private double fee;
        
        public Course(String courseID, String courseName, double fee) {
            this.courseID = courseID;
            this.courseName = courseName;
            this.fee = fee;
        }
        
        public String getCourseID() {
            return courseID;
        }
        
        public String getCourseName() {
            return courseName;
        }
        
        public double getFee() {
            return fee;
        }
        
        @Override
        public String toString() {
            return courseID + "," + courseName + "," + String.format("%.2f", fee);
        }
    }
    
    public static double calculateTotalFees(Course[] courses) {
        double totalFees = 0;
        for (Course course : courses) {
            totalFees += course.getFee();
        }
        return totalFees;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Course Enrollment System ===");
        System.out.print("Enter the number of courses: ");
        int n = sc.nextInt();
        sc.nextLine();
        
        Course[] courses = new Course[n];
        
        System.out.println("\nEnter course details:");
        for (int i = 0; i < n; i++) {
            System.out.println("\nCourse " + (i + 1) + ":");
            System.out.print("  Course ID: ");
            String courseID = sc.nextLine();
            
            System.out.print("  Course Name: ");
            String courseName = sc.nextLine();
            
            System.out.print("  Fee: ");
            double fee = sc.nextDouble();
            sc.nextLine();
            
            courses[i] = new Course(courseID, courseName, fee);
        }
        
        // Display all courses
        System.out.println("\n=== All Courses ===");
        System.out.println("Course ID | Course Name | Fee");
        for (Course course : courses) {
            System.out.println(course.getCourseID() + " | " + course.getCourseName() + " | " + 
                             String.format("%.2f", course.getFee()));
        }
        
        // Calculate total fees
        double totalFees = calculateTotalFees(courses);
        
        // Write to output file
        try {
            FileWriter fw = new FileWriter("CourseTotalFeesOutput.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write("=== Course Enrollment Details ===\n");
            bw.write("CourseID,CourseName,Fee\n");
            
            for (Course course : courses) {
                bw.write(course.toString() + "\n");
            }
            
            bw.write("\n=== Total Fees Summary ===\n");
            bw.write("Total Fees of All Courses: " + String.format("%.2f", totalFees) + "\n");
            
            bw.close();
            System.out.println("\nOutput written to CourseTotalFeesOutput.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        
        // Display total fees
        System.out.println("\n=== Total Fees Summary ===");
        System.out.println("Total Fees of All Courses: " + String.format("%.2f", totalFees));
        
        sc.close();
    }
}
