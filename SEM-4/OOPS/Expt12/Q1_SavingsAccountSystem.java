import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Q1_SavingsAccountSystem {
    
    static class SavingsAccount {
        private String accountNumber;
        private String name;
        private double balance;
        
        public SavingsAccount(String accountNumber, String name, double balance) {
            this.accountNumber = accountNumber;
            this.name = name;
            this.balance = balance;
        }
        
        public void applyInterest(double interestRate) {
            balance = balance + (balance * interestRate / 100);
        }
        
        public String getAccountNumber() {
            return accountNumber;
        }
        
        public String getName() {
            return name;
        }
        
        public double getBalance() {
            return balance;
        }
        
        @Override
        public String toString() {
            return accountNumber + "," + name + "," + String.format("%.2f", balance);
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<SavingsAccount> accounts = new ArrayList<>();
        
        System.out.println("=== Savings Account System ===");
        System.out.print("Enter the number of accounts: ");
        int n = sc.nextInt();
        sc.nextLine();
        
        System.out.println("\nEnter account details:");
        for (int i = 0; i < n; i++) {
            System.out.println("\nAccount " + (i + 1) + ":");
            System.out.print("  Account Number: ");
            String accountNumber = sc.nextLine();
            
            System.out.print("  Name: ");
            String name = sc.nextLine();
            
            System.out.print("  Balance: ");
            double balance = sc.nextDouble();
            sc.nextLine();
            
            accounts.add(new SavingsAccount(accountNumber, name, balance));
        }
        
        System.out.print("\nEnter interest rate (in %): ");
        double interestRate = sc.nextDouble();
        sc.nextLine();
        
        // Apply interest to all accounts
        System.out.println("\nApplying interest...");
        for (SavingsAccount account : accounts) {
            account.applyInterest(interestRate);
        }
        
        // Write to output file
        try {
            FileWriter fw = new FileWriter("SavingsAccountOutput.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write("=== Updated Savings Account Details ===\n");
            bw.write("AccountNumber,Name,UpdatedBalance\n");
            
            for (SavingsAccount account : accounts) {
                bw.write(account.toString() + "\n");
            }
            
            bw.close();
            System.out.println("Output written to SavingsAccountOutput.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        
        // Display on console
        System.out.println("\n=== Updated Account Details ===");
        System.out.println("AccountNumber | Name | UpdatedBalance");
        for (SavingsAccount account : accounts) {
            System.out.println(account.getAccountNumber() + " | " + account.getName() + " | " + 
                             String.format("%.2f", account.getBalance()));
        }
        
        sc.close();
    }
}
