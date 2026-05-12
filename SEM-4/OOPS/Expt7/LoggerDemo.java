// Q1: Method Overloading - Logger Utility
// Demonstrates compile-time polymorphism through method overloading

class Logger {
    private String logSource;

    public Logger(String logSource) {
        this.logSource = logSource;
    }

    // Overloaded method 1: log with just message
    public void log(String message) {
        System.out.println("[INFO] " + message);
    }

    // Overloaded method 2: log with message and level
    public void log(String message, int level) {
        String levelStr = switch (level) {
            case 1 -> "[ERROR]";
            case 2 -> "[WARNING]";
            case 3 -> "[INFO]";
            case 4 -> "[DEBUG]";
            default -> "[TRACE]";
        };
        System.out.println(levelStr + " " + message);
    }

    // Overloaded method 3: log with message and timestamp
    public void log(String message, String timestamp) {
        System.out.println("[" + timestamp + "] " + message);
    }

    // Additional overload: all parameters combined
    public void log(String message, int level, String timestamp) {
        System.out.println("[" + timestamp + "] [Level " + level + "] " + message);
    }
}

public class LoggerDemo {
    public static void main(String[] args) {
        Logger logger = new Logger("AppLogger");

        System.out.println("=== Logger Utility - Method Overloading Demo ===\n");

        // Calling different overloaded versions
        logger.log("System started");
        logger.log("Database connection failed", 1);
        logger.log("User logged in", "2026-04-21 10:30:00");
        logger.log("Processing request", 4, "2026-04-21 10:30:05");

    }
}
