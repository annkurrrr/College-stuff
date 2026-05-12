// Q4: Polymorphism in Interfaces
// Demonstrates interface-based polymorphism

interface Shape {
    void draw();
    double area();
    double perimeter();
}

class Circle implements Shape {
    private double radius;
    private String color;

    public Circle(double radius, String color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw() {
        System.out.println("Drawing " + color + " Circle with radius " + radius);
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    public double getRadius() {
        return radius;
    }
}

class Square implements Shape {
    private double side;
    private String color;

    public Square(double side, String color) {
        this.side = side;
        this.color = color;
    }

    @Override
    public void draw() {
        System.out.println("Drawing " + color + " Square with side " + side);
    }

    @Override
    public double area() {
        return side * side;
    }

    @Override
    public double perimeter() {
        return 4 * side;
    }

    public double getSide() {
        return side;
    }
}

public class ShapeDemo {
    public static void main(String[] args) {
        System.out.println("=== Interface Polymorphism - Shape Demo ===\n");

        // Interface reference holding implementation objects
        Shape[] shapes = new Shape[2];
        shapes[0] = new Circle(5.0, "Red");
        shapes[1] = new Square(4.0, "Blue");

        // Polymorphic behavior through interface
        for (Shape shape : shapes) {
            shape.draw();
            System.out.printf("  Area: %.2f sq units%n", shape.area());
            System.out.printf("  Perimeter: %.2f units%n", shape.perimeter());
            System.out.println();
        }

        // Individual access with type casting
        Circle circle = (Circle) shapes[0];
        Square square = (Square) shapes[1];

        System.out.println("=== Shape Properties ===");
        System.out.println("Circle radius: " + circle.getRadius());
        System.out.println("Square side: " + square.getSide());

    }
}
