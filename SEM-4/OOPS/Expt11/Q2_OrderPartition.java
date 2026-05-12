import java.util.LinkedList;
import java.util.Scanner;

public class Q2_OrderPartition {
    static class Order {
        int orderId;
        String customerName;
        boolean isDelivered;
        public Order(int orderId, String customerName, boolean isDelivered) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.isDelivered = isDelivered;
        }
        @Override
        public String toString() {
            return "Order{" +
                    "orderId=" + orderId +
                    ", customerName='" + customerName + '\'' +
                    ", isDelivered=" + isDelivered +
                    '}';
        }
    }
    
    public static LinkedList<Order> partitionOrders(LinkedList<Order> orders) {
        LinkedList<Order> result = new LinkedList<>();
        
        // First pass: add all undelivered orders
        for (Order order : orders) {
            if (!order.isDelivered) {
                result.add(order);
            }
        }
        
        // Second pass: add all delivered orders
        for (Order order : orders) {
            if (order.isDelivered) {
                result.add(order);
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Order> orders = new LinkedList<>();
        System.out.println("=== Order Partition by Delivery Status ===");
        System.out.print("Enter the number of orders: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("\nOrder " + (i + 1) + ":");
            System.out.print("  Order ID: ");
            int orderId = sc.nextInt();
            sc.nextLine();
            System.out.print("  Customer Name: ");
            String customerName = sc.nextLine();
            System.out.print("  Is Delivered (true/false): ");
            boolean isDelivered = sc.nextBoolean();
            sc.nextLine();
            orders.add(new Order(orderId, customerName, isDelivered));
        }
        System.out.println("\n--- Original List ---");
        for (Order order : orders) {
            System.out.println(order);
        }
        LinkedList<Order> partitionedOrders = partitionOrders(orders);
        System.out.println("\n--- Partitioned List (Undelivered before Delivered) ---");
        for (Order order : partitionedOrders) {
            System.out.println(order);
        }
        sc.close();
    }
}
