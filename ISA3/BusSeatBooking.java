import java.util.*;
import java.io.*;

// Interface
interface Bookable {
    void bookSeat(String name) throws SeatFullException;
    void cancelSeat(String name);
}

// Custom Exception
class SeatFullException extends Exception {
    SeatFullException(String message) {
        super(message);
    }
}

// Bus class
class Bus implements Bookable {

    Vector<String> bookings = new Vector<>();
    final int CAPACITY = 40;

    // Book seat
    @Override
    public void bookSeat(String name) throws SeatFullException {

        if (bookings.size() >= CAPACITY) {
            throw new SeatFullException("Bus is Full!");
        }

        bookings.add(name);
        System.out.println(name + " seat booked successfully.");
    }

    // Cancel seat
    @Override
    public void cancelSeat(String name) {

        if (bookings.remove(name)) {
            System.out.println(name + " booking cancelled.");
        } else {
            System.out.println("Passenger not found.");
        }
    }

    // Display bookings
    void displayBookings() {

        System.out.println("\nBooked Passengers:");

        if (bookings.isEmpty()) {
            System.out.println("No bookings.");
        } else {
            for (String s : bookings) {
                System.out.println(s);
            }
        }
    }

    // Write bookings to file
    void writeToFile() {

        try {
            FileWriter fw = new FileWriter("bookings.txt");

            for (String s : bookings) {
                fw.write(s + "\n");
            }

            fw.close();
            System.out.println("\nBookings written to bookings.txt");

        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }
}

// Main class
public class BusSeatBooking {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bus bus = new Bus();

        System.out.print("Enter number of passengers to book: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Book seats
        for (int i = 0; i < n; i++) {

            System.out.print("Enter passenger name: ");
            String name = sc.nextLine();

            try {
                bus.bookSeat(name);
            } catch (SeatFullException e) {
                System.out.println(e.getMessage());
            }
        }

        bus.displayBookings();

        // Cancel booking
        System.out.print("\nEnter passenger name to cancel booking: ");
        String cancel = sc.nextLine();

        bus.cancelSeat(cancel);

        bus.displayBookings();

        // Write to file
        bus.writeToFile();

        sc.close();
    }
}