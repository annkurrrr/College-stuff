import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SavingsAccount {
    private String accountNumber;
    private String name;
    private double balance;

    // Default constructor
    public SavingsAccount() {
        this.accountNumber = "Unknown";
        this.name = "Unknown";
        this.balance = 0.0;
    }

    // Parameterized constructor
    public SavingsAccount(String accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Method to apply interest
    public void applyInterest(double ratePercentage) {
        this.balance += this.balance * (ratePercentage / 100.0);
    }

    @Override
    public String toString() {
        return String.format("Account No: %s | Name: %s | Balance: %.2f", accountNumber, name, balance);
    }
}

public class SavingsAccountSystem {
    public static void main(String[] args) {
        String inputFileName = "accnts.xt";
        String outputFileName = "updated_accounts.txt";
        
        File inputFile = new File(inputFileName);

        // Checklist: File exists before reading
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.out.println("Error: Input file '" + inputFileName + "' does not exist or cannot be read.");
            return;
        }

        List<SavingsAccount> accounts = new ArrayList<>();
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            // Checklist: Buffered I/O used for better performance
            reader = new BufferedReader(new FileReader(inputFile));
            String line;
            
            System.out.println("--- Reading Accounts from File ---");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String accNo = parts[0].trim();
                    String name = parts[1].trim();
                    double balance = Double.parseDouble(parts[2].trim());
                    
                    SavingsAccount account = new SavingsAccount(accNo, name, balance);
                    accounts.add(account);
                    System.out.println("Read: " + account);
                }
            }

            // Apply 5% interest to all accounts
            double interestRate = 5.0;
            System.out.println("\n--- Applying " + interestRate + "% Interest ---");
            for (SavingsAccount acc : accounts) {
                acc.applyInterest(interestRate);
            }

            // Write updated accounts to output file
            // Checklist: Append vs overwrite mode set correctly (false = overwrite)
            writer = new BufferedWriter(new FileWriter(outputFileName, false));
            System.out.println("--- Writing Updated Accounts to Output File ---");
            
            for (SavingsAccount acc : accounts) {
                writer.write(acc.getAccountNumber() + "," + acc.getName() + "," + String.format("%.2f", acc.getBalance()));
                writer.newLine();
                System.out.println("Wrote: " + acc);
            }
            
            System.out.println("\nSuccessfully written to " + outputFileName);

        } catch (IOException e) {
            // Checklist: IOException is caught and handled
            System.err.println("An I/O error occurred: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Data format error in input file: " + e.getMessage());
        } finally {
            // Checklist: Streams are always closed after use
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException ex) {
                System.err.println("Error closing streams: " + ex.getMessage());
            }
        }
    }
}
