import java.util.*;
import java.io.*;

// Interface
interface Manageable {
    void addProduct(String product);
    void removeProduct(String product) throws ProductNotFoundException;
}

// Custom Exception
class ProductNotFoundException extends Exception {
    ProductNotFoundException(String message) {
        super(message);
    }
}

// Inventory class
class Inventory implements Manageable {

    Vector<String> products = new Vector<>();

    // Add product
    @Override
    public void addProduct(String product) {
        products.add(product);
        System.out.println(product + " added successfully.");
    }

    // Remove product
    @Override
    public void removeProduct(String product) throws ProductNotFoundException {

        if (products.contains(product)) {
            products.remove(product);
            System.out.println(product + " removed successfully.");
        } else {
            throw new ProductNotFoundException("Product Not Found!");
        }
    }

    // Display products
    void displayProducts() {
        System.out.println("\nInventory List:");
        for (String p : products) {
            System.out.println(p);
        }
    }

    // Write inventory to file
    void writeToFile() {

        try {
            FileWriter fw = new FileWriter("inventory.txt");

            for (String p : products) {
                fw.write(p + "\n");
            }

            fw.close();
            System.out.println("\nInventory written to inventory.txt");

        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }
}

// Main class
public class ProductInventory {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Inventory inventory = new Inventory();

        System.out.print("Enter number of products to add: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Add products
        for (int i = 0; i < n; i++) {
            System.out.print("Enter product name: ");
            String product = sc.nextLine();
            inventory.addProduct(product);
        }

        // Display inventory
        inventory.displayProducts();

        // Remove product
        System.out.print("\nEnter product name to remove: ");
        String remove = sc.nextLine();

        try {
            inventory.removeProduct(remove);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Display updated inventory
        inventory.displayProducts();

        // Write to file
        inventory.writeToFile();

        sc.close();
    }
}