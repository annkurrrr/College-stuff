/**
 * Experiment 2: Order Processing System
 * Demonstrates abstract classes with final methods
 */

// Abstract base class for orders
abstract class Order {
    private String orderId;
    private double amount;
    private String customerName;
    private static int orderCounter = 1000;

    // Constructor
    public Order(String customerName, double amount) {
        this.orderId = "ORD-" + (++orderCounter);
        this.customerName = customerName;
        this.amount = amount;
    }

    // Abstract method - must be implemented by subclasses
    public abstract void processOrder();

    // Final method - cannot be overridden by subclasses
    public final void generateInvoice() {
        System.out.println("=== INVOICE ===");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customerName);
        System.out.println("Amount: $" + String.format("%.2f", amount));
        System.out.println("Invoice generated successfully!");
        System.out.println("================");
    }

    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

// OnlineOrder subclass
class OnlineOrder extends Order {
    private String deliveryAddress;
    private String paymentMethod;

    public OnlineOrder(String customerName, double amount, String address, String payment) {
        super(customerName, amount);
        this.deliveryAddress = address;
        this.paymentMethod = payment;
    }

    @Override
    public void processOrder() {
        System.out.println("Processing ONLINE order...");
        System.out.println("Delivery Address: " + deliveryAddress);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Order will be shipped within 2-3 business days.");
        generateInvoice(); // Calling final method
    }
}

// StoreOrder subclass
class StoreOrder extends Order {
    private String storeLocation;
    private String pickupTime;

    public StoreOrder(String customerName, double amount, String location, String time) {
        super(customerName, amount);
        this.storeLocation = location;
        this.pickupTime = time;
    }

    @Override
    public void processOrder() {
        System.out.println("Processing STORE pickup order...");
        System.out.println("Store Location: " + storeLocation);
        System.out.println("Pickup Time: " + pickupTime);
        System.out.println("Order ready for pickup!");
        generateInvoice(); // Calling final method
    }
}

// Main class
public class OrderProcessingSystem {
    public static void main(String[] args) {
        System.out.println("=== ORDER PROCESSING SYSTEM ===\n");

        // Creating and processing Online Order
        OnlineOrder onlineOrder = new OnlineOrder(
            "John Doe",
            299.99,
            "123 Main St, City, State 12345",
            "Credit Card"
        );
        onlineOrder.processOrder();

        System.out.println();

        // Creating and processing Store Order
        StoreOrder storeOrder = new StoreOrder(
            "Jane Smith",
            149.50,
            "Downtown Store",
            "2:00 PM"
        );
        storeOrder.processOrder();
    }
}
