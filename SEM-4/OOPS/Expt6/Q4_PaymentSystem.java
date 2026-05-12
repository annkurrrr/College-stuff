import java.util.Scanner;

abstract class Payment {
    private double amount;

    public Payment() {
    }

    public Payment(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public abstract void processPayment();
}

class CreditCardPayment extends Payment {
    private String cardNumber;

    public CreditCardPayment() {
        super();
    }

    public CreditCardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing Credit Card Payment...");
        System.out.println("Debiting $" + getAmount() + " from Card Number ending in: "
                + cardNumber.substring(Math.max(0, cardNumber.length() - 4)));
        System.out.println("Payment Successful!\n");
    }
}

class UPIPayment extends Payment {
    private String upiId;

    public UPIPayment() {
        super();
    }

    public UPIPayment(double amount, String upiId) {
        super(amount);
        this.upiId = upiId;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing UPI Payment...");
        System.out.println("Debiting $" + getAmount() + " via UPI ID: " + upiId);
        System.out.println("Payment Successful!\n");
    }
}

public class Q4_PaymentSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Abstract Class: Payment System ---");

        System.out.print("Enter amount for Credit Card Payment: ");
        double cardAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Credit Card Number: ");
        String cardNo = scanner.nextLine();

        System.out.print("\nEnter amount for UPI Payment: ");
        double upiAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter UPI ID: ");
        String upiIdStr = scanner.nextLine();

        Payment payment1 = new CreditCardPayment(cardAmount, cardNo);
        Payment payment2 = new UPIPayment(upiAmount, upiIdStr);

        System.out.println("\n--- Initiating Transactions ---");
        payment1.processPayment();

        payment2.processPayment();

        scanner.close();
    }
}
