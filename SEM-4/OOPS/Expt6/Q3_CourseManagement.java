import java.util.Scanner;

class Course {
    private String courseName;
    private double baseFee;

    public Course() {
    }

    public Course(String courseName, double baseFee) {
        this.courseName = courseName;
        this.baseFee = baseFee;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(double baseFee) {
        this.baseFee = baseFee;
    }

    public double calculateCourseFee() {
        return baseFee;
    }

    public void displayCourseInfo() {
        System.out.println("Course Name: " + courseName);
        System.out.println("Calculated Total Fee: $" + calculateCourseFee());
    }
}

class OnlineCourse extends Course {
    private double platformFee;

    public OnlineCourse() {
        super();
    }

    public OnlineCourse(String courseName, double baseFee, double platformFee) {
        super(courseName, baseFee);
        this.platformFee = platformFee;
    }

    public double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(double platformFee) {
        this.platformFee = platformFee;
    }

    @Override
    public double calculateCourseFee() {
        return getBaseFee() + platformFee;
    }
}

class OfflineCourse extends Course {
    private double facilityFee;

    public OfflineCourse() {
        super();
    }

    public OfflineCourse(String courseName, double baseFee, double facilityFee) {
        super(courseName, baseFee);
        this.facilityFee = facilityFee;
    }

    public double getFacilityFee() {
        return facilityFee;
    }

    public void setFacilityFee(double facilityFee) {
        this.facilityFee = facilityFee;
    }

    @Override
    public double calculateCourseFee() {
        return getBaseFee() + facilityFee;
    }
}

public class Q3_CourseManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Hierarchical Inheritance: Course Management ---");

        System.out.println("Enter Online Course Name: ");
        String onlineName = scanner.nextLine();
        System.out.println("Enter Base Fee and Platform Fee (separated by space): ");
        double onlineBase = scanner.nextDouble();
        double onlinePlatform = scanner.nextDouble();

        scanner.nextLine();

        System.out.println("\nEnter Offline Course Name: ");
        String offlineName = scanner.nextLine();
        System.out.println("Enter Base Fee and Facility Fee (separated by space): ");
        double offlineBase = scanner.nextDouble();
        double offlineFacility = scanner.nextDouble();

        Course course1 = new OnlineCourse(onlineName, onlineBase, onlinePlatform);
        Course course2 = new OfflineCourse(offlineName, offlineBase, offlineFacility);

        System.out.println("\n--- Online Course Details Output ---");
        course1.displayCourseInfo();

        System.out.println("\n--- Offline Course Details Output ---");
        course2.displayCourseInfo();

        scanner.close();
    }
}
