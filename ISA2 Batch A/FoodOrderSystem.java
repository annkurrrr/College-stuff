import java.util.Scanner;

// Abstract class
abstract class FoodItem {
    String itemName;

    FoodItem(String itemName) {
        this.itemName = itemName;
    }

    // Abstract method
    abstract double getPrice();
}

// MainCourse class
class MainCourse extends FoodItem {
    double price;

    MainCourse(String itemName, double price) {
        super(itemName);
        this.price = price;
    }

    @Override
    double getPrice() {
        return price;
    }
}

// Dessert class
class Dessert extends FoodItem {
    double price;

    Dessert(String itemName, double price) {
        super(itemName);
        this.price = price;
    }

    @Override
    double getPrice() {
        return price;
    }
}

// Main class
public class FoodOrderSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of food items: ");
        int n = sc.nextInt();
        sc.nextLine();

        FoodItem[] order = new FoodItem[n];

        double totalBill = 0;

        // Input food items
        for (int i = 0; i < n; i++) {

            System.out.println("\nItem " + (i + 1));

            System.out.print("Enter item type (MainCourse/Dessert): ");
            String type = sc.nextLine();

            System.out.print("Enter item name: ");
            String name = sc.nextLine();

            System.out.print("Enter item price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            if (type.equalsIgnoreCase("MainCourse")) {
                order[i] = new MainCourse(name, price);
            } else {
                order[i] = new Dessert(name, price);
            }

            double itemPrice = order[i].getPrice();

            // Apply 10% surcharge if name contains "Special"
            if (name.toLowerCase().contains("special")) {
                itemPrice = itemPrice + (itemPrice * 0.10);
            }

            totalBill += itemPrice;
        }

        // Display total bill
        System.out.println("\nTotal Bill = Rs. " + totalBill);

        // Print only dessert items
        System.out.println("\nDessert Items:");

        for (int i = 0; i < order.length; i++) {
            if (order[i] instanceof Dessert) {
                System.out.println(order[i].itemName + " - Rs. " + order[i].getPrice());
            }
        }

        sc.close();
    }
}