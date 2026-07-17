import java.util.Scanner;

// Abstract class
abstract class Vehicle {
    String modelName;

    Vehicle(String modelName) {
        this.modelName = modelName;
    }

    // Abstract method
    abstract double getRentalCost(int days);
}

// Car subclass
class Car extends Vehicle {

    double dailyRate;

    Car(String modelName, double dailyRate) {
        super(modelName);
        this.dailyRate = dailyRate;
    }

    @Override
    double getRentalCost(int days) {
        return dailyRate * days;
    }
}

// Bike subclass
class Bike extends Vehicle {

    double dailyRate;

    Bike(String modelName, double dailyRate) {
        super(modelName);
        this.dailyRate = dailyRate;
    }

    @Override
    double getRentalCost(int days) {
        return dailyRate * days;
    }
}

// Main class
public class VehicleRentalService {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vehicles: ");
        int n = sc.nextInt();
        sc.nextLine();

        Vehicle[] vehicles = new Vehicle[n];

        System.out.print("Enter number of rental days: ");
        int days = sc.nextInt();
        sc.nextLine();

        // Input vehicle details
        for (int i = 0; i < n; i++) {

            System.out.println("\nVehicle " + (i + 1));

            System.out.print("Enter Vehicle Type (Car/Bike): ");
            String type = sc.nextLine();

            System.out.print("Enter Model Name: ");
            String model = sc.nextLine();

            System.out.print("Enter Daily Rate: ");
            double rate = sc.nextDouble();
            sc.nextLine();

            if (type.equalsIgnoreCase("Car")) {
                vehicles[i] = new Car(model, rate);
            } else {
                vehicles[i] = new Bike(model, rate);
            }
        }

        System.out.println("\n===== Rental Details =====");

        double minCost = Double.MAX_VALUE;
        String cheapestVehicle = "";

        // Polymorphism
        for (int i = 0; i < vehicles.length; i++) {

            double cost = vehicles[i].getRentalCost(days);

            // Apply 10% discount if model name > 10 characters
            if (vehicles[i].modelName.length() > 10) {
                cost = cost - (cost * 0.10);
            }

            System.out.println("Model Name  : " + vehicles[i].modelName);
            System.out.println("Rental Cost : " + cost);
            System.out.println();

            if (cost < minCost) {
                minCost = cost;
                cheapestVehicle = vehicles[i].modelName;
            }
        }

        System.out.println("Cheapest Rental Option");
        System.out.println("Vehicle : " + cheapestVehicle);
        System.out.println("Cost    : " + minCost);

        sc.close();
    }
}