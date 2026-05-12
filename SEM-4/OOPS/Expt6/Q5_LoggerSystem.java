import java.util.Scanner;

final class Logger {

    public Logger() {
    }

    public void logMessage(String message) {
        System.out.println("[LOG] " + java.time.LocalDateTime.now() + " : " + message);
    }
}

/*
 * Attempting to do the following would cause a compilation error:
 * 
 * class MaliciousLogger extends Logger {
 * 
 * @Override
 * public void logMessage(String message) {
 * // Suppressing logs or doing something malicious
 * }
 * }
 */

public class Q5_LoggerSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Logger systemLogger = new Logger();

        System.out.println("--- Final Class: Logger System ---");
        System.out.print("Enter a system event to log (e.g., User logged in): ");
        String event1 = scanner.nextLine();

        System.out.print("Enter another system event (e.g., Database connected): ");
        String event2 = scanner.nextLine();

        System.out.println("\n--- Generating Logs ---");

        systemLogger.logMessage(event1);
        systemLogger.logMessage(event2);

        scanner.close();
    }
}
