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

    // Return final price after discount
    abstract double getFinalPrice();

    // Display item details
    void displayDetails() {
        System.out.println("Product Name : " + productName);
        System.out.println("Price        : " + price);
    }
}

// Grocery subclass
class Grocery extends Product {

    Grocery(String productName, double price) {
        super(productName, price);
    }

    @Override
    void getCategory() {
        System.out.println("Category : Grocery");
    }

    @Override
    double getFinalPrice() {
        return price - (price * 0.10); // 10% discount
    }
}

// Electronics subclass
class Electronics extends Product {

    Electronics(String productName, double price) {
        super(productName, price);
    }

    @Override
    void getCategory() {
        System.out.println("Category : Electronics");
    }

    @Override
    double getFinalPrice() {
        return price - (price * 0.05); // 5% discount
    }
}

// Main class
public class SupermarketBillingSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of products: ");
        int n = sc.nextInt();
        sc.nextLine();

        Product[] products = new Product[n];

        double totalBill = 0;

        // Input products
        for (int i = 0; i < n; i++) {

            System.out.println("\nProduct " + (i + 1));

            System.out.print("Enter Category (Grocery/Electronics): ");
            String category = sc.nextLine();

            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();

            // Trim and uppercase product name
            name = name.trim().toUpperCase();

            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            if (category.equalsIgnoreCase("Grocery")) {
                products[i] = new Grocery(name, price);
            } else {
                products[i] = new Electronics(name, price);
            }
        }

        System.out.println("\n----------- BILL SUMMARY -----------");

        // Polymorphism
        for (int i = 0; i < products.length; i++) {

            products[i].getCategory();
            products[i].displayDetails();

            double finalPrice = products[i].getFinalPrice();

            System.out.println("Final Price : " + finalPrice);
            System.out.println();

            totalBill += finalPrice;
        }

        System.out.println("------------------------------------");
        System.out.println("Total Bill = " + totalBill);

        sc.close();
    }
}