// Q2: Shared Printer simulated by 3 threads (3 users)
// synchronized method ensures each print job completes fully before next one starts

class SharedPrinterQ2 {
    // synchronized ensures only one thread can call this method at a time
    public synchronized void printJob(String userName, String line1, String line2) {
        System.out.println("\n[PRINTER] Starting job for: " + userName);
        System.out.println("          Line 1: " + line1);
        try {
            Thread.sleep(500); // simulate printing time for line 1
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("          Line 2: " + line2);
        try {
            Thread.sleep(500); // simulate printing time for line 2
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[PRINTER] Job for " + userName + " completed.\n");
    }
}

class PrintUser extends Thread {
    private SharedPrinterQ2 printer;
    private String userName;
    private String line1;
    private String line2;

    public PrintUser(SharedPrinterQ2 printer, String userName, String line1, String line2) {
        super(userName);
        this.printer  = printer;
        this.userName = userName;
        this.line1    = line1;
        this.line2    = line2;
    }

    @Override
    public void run() {
        System.out.println(userName + " is waiting to print...");
        printer.printJob(userName, line1, line2);
    }
}

public class SynchronizedPrinterDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Synchronized Shared Printer Demo ===");
        System.out.println("3 users trying to use the same printer simultaneously.");
        System.out.println("Each print job (2 lines) must complete before the next starts.\n");

        SharedPrinterQ2 printer = new SharedPrinterQ2();

        Thread u1 = new PrintUser(printer, "Alice",
                "Dear Manager,",
                "Please approve my leave application.");

        Thread u2 = new PrintUser(printer, "Bob",
                "To whom it may concern,",
                "I am submitting my project report.");

        Thread u3 = new PrintUser(printer, "Charlie",
                "Hello Team,",
                "Meeting is scheduled at 3 PM today.");

        // All three start at nearly the same time
        u1.start();
        u2.start();
        u3.start();

        u1.join();
        u2.join();
        u3.join();

        System.out.println("All print jobs completed.");
    }
}
