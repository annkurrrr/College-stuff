import java.util.Scanner;

// Abstract class
abstract class Product {
    String productName;
    double price;

    Product(String productName, double price) {
        this.productName = productName;
        this.price = price;
    }

    // Abstract method
    abstract void getCategory();
}

// GroceryProduct subclass
class GroceryProduct extends Product {

    double[] prices = new double[5];

    GroceryProduct(String productName, double price, double[] prices) {
        super(productName, price);
        this.prices = prices;
    }

    @Override
    void getCategory() {
        System.out.println("Category: Grocery Product");
    }

    // Calculate total cost
    double getTotalCost() {
        double sum = 0;

        for (int i = 0; i < prices.length; i++) {
            sum += prices[i];
        }

        return sum;
    }

    // Capitalize first letter
    void formatName() {
        String formatted = productName.substring(0, 1).toUpperCase()
                + productName.substring(1).toLowerCase();

        System.out.println("Formatted Product Name: " + formatted);
    }

    // Check budget
    void checkBudget() {
        double total = getTotalCost();

        if (total <= 500) {
            System.out.println("In Budget");
        } else {
            System.out.println("Over Budget");
        }
    }
}

// Main class
public class ProductInventoryTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Product Price: ");
        double price = sc.nextDouble();

        double[] prices = new double[5];

        System.out.println("Enter prices of 5 grocery products:");
        for (int i = 0; i < 5; i++) {
            System.out.print("Price " + (i + 1) + ": ");
            prices[i] = sc.nextDouble();
        }

        // Polymorphism
        Product p = new GroceryProduct(name, price, prices);

        p.getCategory();

        // Downcasting
        GroceryProduct gp = (GroceryProduct) p;

        gp.formatName();

        double total = gp.getTotalCost();
        System.out.println("Total Cost = " + total);

        gp.checkBudget();

        sc.close();
    }
}