// Q2: Method Overriding - Transport System
// Demonstrates runtime polymorphism through method overriding

class Transport {
    protected String name;
    protected double baseFare;

    public Transport(String name, double baseFare) {
        this.name = name;
        this.baseFare = baseFare;
    }

    // Method to be overridden by subclasses
    public double fare() {
        return baseFare;
    }

    public String getName() {
        return name;
    }
}

class Bus extends Transport {
    private int distance;
    private double perKmRate;

    public Bus(String name, double baseFare, int distance, double perKmRate) {
        super(name, baseFare);
        this.distance = distance;
        this.perKmRate = perKmRate;
    }

    @Override
    public double fare() {
        return baseFare + (distance * perKmRate);
    }
}

class Train extends Transport {
    private String coachType;
    private double coachMultiplier;

    public Train(String name, double baseFare, String coachType, double coachMultiplier) {
        super(name, baseFare);
        this.coachType = coachType;
        this.coachMultiplier = coachMultiplier;
    }

    @Override
    public double fare() {
        return baseFare * coachMultiplier;
    }
}

class Taxi extends Transport {
    private double waitingTime;
    private double waitingRate;

    public Taxi(String name, double baseFare, double waitingTime, double waitingRate) {
        super(name, baseFare);
        this.waitingTime = waitingTime;
        this.waitingRate = waitingRate;
    }

    @Override
    public double fare() {
        return baseFare + (waitingTime * waitingRate);
    }
}

public class TransportDemo {
    public static void main(String[] args) {
        System.out.println("=== Transport System - Method Overriding Demo ===\n");

        // Creating objects
        Transport bus = new Bus("AC Express", 50.0, 100, 2.5);
        Transport train = new Train("Rajdhani", 200.0, "AC", 2.5);
        Transport taxi = new Taxi("City Cab", 30.0, 15, 5.0);

        // Displaying fare calculations
        System.out.println(bus.getName() + " Fare: Rs." + bus.fare());
        System.out.println(train.getName() + " Fare: Rs." + train.fare());
        System.out.println(taxi.getName() + " Fare: Rs." + taxi.fare());

    }
}
