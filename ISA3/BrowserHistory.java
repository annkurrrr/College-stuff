import java.util.*;
import java.io.*;

// Interface
interface Navigable {
    void visit(String url);
    void goBack() throws NoHistoryException;
}

// Custom Exception
class NoHistoryException extends Exception {
    NoHistoryException(String message) {
        super(message);
    }
}

// Browser class
class Browser implements Navigable {

    Stack<String> history = new Stack<>();

    // Visit URL
    @Override
    public void visit(String url) {
        history.push(url);
        System.out.println(url + " visited.");
    }

    // Go back
    @Override
    public void goBack() throws NoHistoryException {

        if (history.isEmpty()) {
            throw new NoHistoryException("No History Available!");
        }

        System.out.println("Removed: " + history.pop());
    }

    // Display history
    void displayHistory() {
        System.out.println("\nCurrent Browser History:");
        if (history.isEmpty()) {
            System.out.println("History is Empty.");
        } else {
            for (String url : history) {
                System.out.println(url);
            }
        }
    }

    // Save history to file
    void saveHistory() {

        try {
            FileWriter fw = new FileWriter("history.txt");

            for (String url : history) {
                fw.write(url + "\n");
            }

            fw.close();
            System.out.println("\nHistory saved to history.txt");

        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }
}

// Main class
public class BrowserHistory {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Browser browser = new Browser();

        System.out.print("Enter number of websites to visit: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Visit websites
        for (int i = 0; i < n; i++) {
            System.out.print("Enter URL: ");
            String url = sc.nextLine();
            browser.visit(url);
        }

        browser.displayHistory();

        // Go back
        System.out.print("\nHow many times do you want to go back? ");
        int back = sc.nextInt();

        for (int i = 0; i < back; i++) {

            try {
                browser.goBack();
            } catch (NoHistoryException e) {
                System.out.println(e.getMessage());
            }
        }

        browser.displayHistory();

        // Save remaining history
        browser.saveHistory();

        sc.close();
    }
}