import java.util.*;

// Interface 1
interface SolarPowered {
    default double calculateCostPerUnit() {
        return 2.5;
    }
}

// Interface 2
interface GridPowered {
    default double calculateCostPerUnit() {
        return 7.0;
    }
}

// HybridHome class implementing both interfaces
class HybridHome implements SolarPowered, GridPowered {

    private double solarPercentage;
    private double gridPercentage;

    // Default Constructor
    public HybridHome() {
        solarPercentage = 50;
        gridPercentage = 50;
    }

    // Parameterized Constructor
    public HybridHome(double solarPercentage, double gridPercentage) {
        this.solarPercentage = solarPercentage;
        this.gridPercentage = gridPercentage;
    }

    // Overriding default method to resolve ambiguity
    public double calculateCostPerUnit() {
        double solarCost = SolarPowered.super.calculateCostPerUnit();
        double gridCost = GridPowered.super.calculateCostPerUnit();

        return (solarPercentage/100 * solarCost) +
               (gridPercentage/100 * gridCost);
    }

    // Monthly bill calculation
    public double monthlyBill(double units) {
        return units * calculateCostPerUnit();
    }

    // Carbon footprint calculation
    public double carbonFootprint(double units) {
        double gridUnits = (gridPercentage/100) * units;
        return gridUnits * 0.82; // kg CO2 per unit
    }

    // toString method
    public String toString() {
        return "Solar Usage: " + solarPercentage + "%\n" +
               "Grid Usage: " + gridPercentage + "%\n" +
               "Cost per Unit: " + calculateCostPerUnit();
    }
}

// Main class
public class EnergyMonitor {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total units consumed: ");
        double units = sc.nextDouble();

        System.out.print("Enter solar usage percentage: ");
        double solar = sc.nextDouble();

        double grid = 100 - solar;

        // Object creation through user input
        HybridHome h = new HybridHome(solar, grid);

        System.out.println("\n--- Energy Consumption Details ---");
        System.out.println(h);

        System.out.println("Monthly Bill: " + h.monthlyBill(units));
        System.out.println("Carbon Footprint: " + h.carbonFootprint(units) + " kg CO2");

        sc.close();
    }
}