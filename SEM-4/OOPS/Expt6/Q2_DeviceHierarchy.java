import java.util.Scanner;

class Device {
    private String deviceName;

    public Device() {
        System.out.println("1. Device (Base Class) default initialized.");
    }

    public Device(String deviceName) {
        System.out.println("1. Device (Base Class) initialized.");
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}

class Computer extends Device {
    private String processor;

    public Computer() {
        super();
        System.out.println("2. Computer (Intermediate/Derived Class) default initialized.");
    }

    public Computer(String deviceName, String processor) {
        super(deviceName);
        System.out.println("2. Computer (Intermediate/Derived Class) initialized.");
        this.processor = processor;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }
}

class Laptop extends Computer {
    private int batteryLife;

    public Laptop() {
        super();
        System.out.println("3. Laptop (Derived Class) default initialized.");
    }

    public Laptop(String deviceName, String processor, int batteryLife) {
        super(deviceName, processor);
        System.out.println("3. Laptop (Derived Class) initialized.");
        this.batteryLife = batteryLife;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public void displaySpecs() {
        System.out.println("\n--- Laptop Specifications ---");
        System.out.println("Device Name: " + getDeviceName());
        System.out.println("Processor: " + getProcessor());
        System.out.println("Battery Life: " + batteryLife + " hours");
    }
}

public class Q2_DeviceHierarchy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Multilevel Inheritance: Device Hierarchy ---");
        System.out.print("Enter Device Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Processor Details: ");
        String processor = scanner.nextLine();

        System.out.print("Enter Battery Life (hours): ");
        int battery = scanner.nextInt();

        System.out.println("\nCreating Laptop Object:");
        Laptop myLaptop = new Laptop(name, processor, battery);

        myLaptop.displaySpecs();

        scanner.close();
    }
}
