import java.util.Scanner;

// Abstract class
abstract class BankAccount {
    String ownerName;
    double balance;

    BankAccount(String ownerName, double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

    // Abstract method
    abstract void getAccountType();

    // Method to check full name
    void checkOwnerName() {
        ownerName = ownerName.trim();

        if (ownerName.contains(" ")) {
            System.out.println("Owner has Full Name.");
        } else {
            System.out.println("Owner has Single Name.");
        }
    }
}

// SavingsAccount class
class SavingsAccount extends BankAccount {

    double[] transactions = new double[5];

    SavingsAccount(String ownerName, double balance, double[] transactions) {
        super(ownerName, balance);
        this.transactions = transactions;
    }

    @Override
    void getAccountType() {
        System.out.println("Account Type: Savings Account");
    }

    // Compute total transactions
    double computeTotalTransactions() {
        double sum = 0;

        for (int i = 0; i < transactions.length; i++) {
            sum += transactions[i];
        }

        return sum;
    }

    // Check balance
    void checkBalance() {
        if (balance >= 5000) {
            System.out.println("Sufficient Balance");
        } else {
            System.out.println("Low Balance");
        }
    }
}

// Main class
public class BankAccountManager {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Owner Name: ");
        String owner = sc.nextLine();

        System.out.print("Enter Account Balance: ");
        double balance = sc.nextDouble();

        double[] transactions = new double[5];

        System.out.println("Enter last 5 transaction amounts:");
        for (int i = 0; i < 5; i++) {
            System.out.print("Transaction " + (i + 1) + ": ");
            transactions[i] = sc.nextDouble();
        }

        // Polymorphism
        BankAccount account = new SavingsAccount(owner, balance, transactions);

        account.getAccountType();
        account.checkOwnerName();

        // Downcasting
        SavingsAccount s = (SavingsAccount) account;

        System.out.println("Total Transactions = " + s.computeTotalTransactions());

        s.checkBalance();

        sc.close();
    }
}