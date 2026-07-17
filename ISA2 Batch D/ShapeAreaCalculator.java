import java.util.Scanner;

// Abstract class
abstract class Shape {
    String shapeName;

    Shape(String shapeName) {
        this.shapeName = shapeName;
    }

    // Abstract method
    abstract double calculateArea();
}

// Rectangle subclass
class Rectangle extends Shape {

    double length, breadth;

    Rectangle(String shapeName, double length, double breadth) {
        super(shapeName);
        this.length = length;
        this.breadth = breadth;
    }

    @Override
    double calculateArea() {
        return length * breadth;
    }
}

// Circle subclass
class Circle extends Shape {

    double radius;

    Circle(String shapeName, double radius) {
        super(shapeName);
        this.radius = radius;
    }

    @Override
    double calculateArea() {
        return 3.14 * radius * radius;
    }

    double calculateCircumference() {
        return 2 * 3.14 * radius;
    }
}

// Main class
public class ShapeAreaCalculator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of shapes: ");
        int n = sc.nextInt();
        sc.nextLine();

        Shape[] shapes = new Shape[n];

        // Input
        for (int i = 0; i < n; i++) {

            System.out.println("\nShape " + (i + 1));

            System.out.print("Enter Shape Type (Rectangle/Circle): ");
            String type = sc.nextLine();

            if (type.equalsIgnoreCase("Rectangle")) {

                System.out.print("Enter Shape Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Length: ");
                double length = sc.nextDouble();

                System.out.print("Enter Breadth: ");
                double breadth = sc.nextDouble();
                sc.nextLine();

                shapes[i] = new Rectangle(name, length, breadth);

            } else {

                System.out.print("Enter Shape Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Radius: ");
                double radius = sc.nextDouble();
                sc.nextLine();

                shapes[i] = new Circle(name, radius);
            }
        }

        System.out.println("\n===== Shape Details =====");

        double maxArea = 0;
        String largestShape = "";

        // Polymorphism
        for (int i = 0; i < shapes.length; i++) {

            double area = shapes[i].calculateArea();

            System.out.println("Shape Name : " + shapes[i].shapeName);
            System.out.println("Area       : " + area);

            // Print circumference if shape name is "Circle"
            if (shapes[i].shapeName.equalsIgnoreCase("Circle")) {
                Circle c = (Circle) shapes[i];
                System.out.println("Circumference : " + c.calculateCircumference());
            }

            System.out.println();

            if (area > maxArea) {
                maxArea = area;
                largestShape = shapes[i].shapeName;
            }
        }

        System.out.println("Largest Shape : " + largestShape);
        System.out.println("Largest Area  : " + maxArea);

        sc.close();
    }
}