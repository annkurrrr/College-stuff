import java.util.*;

// Custom Checked Exception
class InsufficientFundsException extends Exception {
    private double balance;
    private double amount;

    // Default Constructor
    public InsufficientFundsException() {
        balance = 0;
        amount = 0;
    }

    // Parameterized Constructor
    public InsufficientFundsException(double balance, double amount) {
        this.balance = balance;
        this.amount = amount;
    }

    // toString method
    public String toString() {
        return "Cannot withdraw " + amount + ". Available balance: " + balance;
    }
}

// ATM Class
class ATM {
    private double balance;

    // Default Constructor
    public ATM() {
        balance = 0;
    }

    // Parameterized Constructor
    public ATM(double balance) {
        this.balance = balance;
    }

    // Withdraw Method
    public void withdraw(double amount) throws InsufficientFundsException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (amount > balance) {
            throw new InsufficientFundsException(balance, amount);
        }

        balance -= amount;
        System.out.println("Withdrawal successful! " + amount + " deducted.");
    }

    public double getBalance() {
        return balance;
    }

    // toString method
    public String toString() {
        return "Current Balance: " + balance;
    }
}

// Main Class
public class ATMTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter initial balance: ");
        double bal = sc.nextDouble();

        ATM atm = new ATM(bal);

        System.out.print("Enter amount to withdraw: ");
        double amt = sc.nextDouble();

        try {
            atm.withdraw(amt);
        }
        catch (InsufficientFundsException e) {
            System.out.println("Checked Exception Caught:");
            System.out.println(e);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Unchecked Exception Caught:");
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println("\n--- Transaction Complete ---");
            System.out.println(atm);
        }

        sc.close();
    }
}