import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Course {
    private String courseId;
    private String courseName;
    private double fee;

    // Default constructor
    public Course() {
        this.courseId = "Unknown";
        this.courseName = "Unknown";
        this.fee = 0.0;
    }

    // Parameterized constructor
    public Course(String courseId, String courseName, double fee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.fee = fee;
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return String.format("Course ID: %s | Name: %s | Fee: %.2f", courseId, courseName, fee);
    }
}

public class CourseEnrollment {
    public static void main(String[] args) {
        String inputFileName = "courses.txt";
        String outputFileName = "total_fees.txt";

        File inputFile = new File(inputFileName);

        // Checklist: File exists before reading
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.out.println("Error: Input file '" + inputFileName + "' does not exist or cannot be read.");
            return;
        }

        // Checklist: Store courses in an array
        Course[] courses = new Course[100];
        int courseCount = 0;

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            // Checklist: Buffered I/O used for better performance
            reader = new BufferedReader(new FileReader(inputFile));
            String line;
            
            System.out.println("--- Reading Course Details ---");
            while ((line = reader.readLine()) != null && courseCount < courses.length) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String cId = parts[0].trim();
                    String cName = parts[1].trim();
                    double fee = Double.parseDouble(parts[2].trim());
                    
                    courses[courseCount] = new Course(cId, cName, fee);
                    System.out.println("Read: " + courses[courseCount]);
                    courseCount++;
                }
            }

            if (courseCount == 0) {
                System.out.println("No course data found.");
                return;
            }

            // Calculate total fees
            double totalFees = 0.0;
            for (int i = 0; i < courseCount; i++) {
                totalFees += courses[i].getFee();
            }

            System.out.println("\nTotal Fees Calculated: " + totalFees);

            // Write total fees to output file
            File outputFile = new File(outputFileName);
            
            // Checklist: Append vs overwrite mode set correctly
            writer = new BufferedWriter(new FileWriter(outputFile, false));
            writer.write("Course Enrollment System - Total Fees Calculation");
            writer.newLine();
            writer.write("Total number of courses: " + courseCount);
            writer.newLine();
            writer.write(String.format("Total Fees of all courses: %.2f", totalFees));
            writer.newLine();
            
            System.out.println("Successfully wrote total fees to " + outputFileName);

        } catch (IOException e) {
            // Checklist: IOException is caught and handled
            System.err.println("An I/O error occurred: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Data format error in input file: " + e.getMessage());
        } finally {
            // Checklist: Streams are always closed after use
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException ex) {
                System.err.println("Error closing streams: " + ex.getMessage());
            }
        }
    }
}
