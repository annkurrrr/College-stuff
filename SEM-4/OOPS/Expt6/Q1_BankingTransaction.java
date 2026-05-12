import java.util.Scanner;

class Account {
    private String accountNumber;
    private String accountHolder;

    public Account() {
    }

    public Account(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public void displayDetails() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount() {
        super();
    }

    public SavingsAccount(String accountNumber, String accountHolder, double interestRate) {
        super(accountNumber, accountHolder);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Interest Rate: " + interestRate + "%");
    }
}

public class Q1_BankingTransaction {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Single Inheritance: Banking Transaction ---");

        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();

        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Interest Rate (%): ");
        double interest = scanner.nextDouble();

        SavingsAccount savingsUser = new SavingsAccount(accNum, name, interest);

        savingsUser.displayDetails();

        scanner.close();
    }
}
