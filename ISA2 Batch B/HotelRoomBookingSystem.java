import java.util.Scanner;

// Abstract class
abstract class Accommodation {
    String hotelName;

    Accommodation(String hotelName) {
        this.hotelName = hotelName;
    }

    // Abstract method
    abstract void getRoomType();
}

// HotelRoom subclass
class HotelRoom extends Accommodation {

    double[] rates = new double[5];

    HotelRoom(String hotelName, double[] rates) {
        super(hotelName);
        this.rates = rates;
    }

    @Override
    void getRoomType() {
        System.out.println("Room Type: Hotel Room");
    }

    // Calculate total cost
    double getTotalCost() {
        double sum = 0;

        for (int i = 0; i < rates.length; i++) {
            sum += rates[i];
        }

        return sum;
    }

    // Format hotel name
    void formatHotelName() {

        if (hotelName.length() > 10) {
            System.out.println("Shortened Hotel Name: " + hotelName.substring(0, 10));
        } else {
            System.out.println("Hotel Name: " + hotelName);
        }
    }

    // Check stay type
    void checkStay() {
        double total = getTotalCost();

        if (total > 5000) {
            System.out.println("Premium Stay");
        } else {
            System.out.println("Affordable Stay");
        }
    }
}

// Main class
public class HotelRoomBookingSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Hotel Name: ");
        String hotelName = sc.nextLine();

        double[] rates = new double[5];

        System.out.println("Enter 5 nightly room rates:");

        for (int i = 0; i < 5; i++) {
            System.out.print("Rate " + (i + 1) + ": ");
            rates[i] = sc.nextDouble();
        }

        // Polymorphism
        Accommodation a = new HotelRoom(hotelName, rates);

        a.getRoomType();

        // Downcasting
        HotelRoom h = (HotelRoom) a;

        h.formatHotelName();

        System.out.println("Total Cost = " + h.getTotalCost());

        h.checkStay();

        sc.close();
    }
}