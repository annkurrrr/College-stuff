/**
 * Experiment 4: Smart Home System
 * Demonstrates interface and abstract class combination
 */

// Interface for automation functionality
interface Automation {
    void autoControl();
}

// Abstract base class for home devices
abstract class HomeDevice {
    private String deviceId;
    private String deviceName;
    private boolean isOn;
    private static int deviceCounter = 0;

    // Constructor
    public HomeDevice(String deviceName) {
        this.deviceId = "DEV-" + (++deviceCounter);
        this.deviceName = deviceName;
        this.isOn = false;
    }

    // Static method - belongs to class, not instance
    public static String deviceCategory() {
        return "Smart Home Devices";
    }

    // Abstract method - must be implemented by subclasses
    public abstract void operate();

    // Concrete methods
    public void turnOn() {
        isOn = true;
        System.out.println(deviceName + " is now ON");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(deviceName + " is now OFF");
    }

    // Getters and setters
    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public boolean isOn() {
        return isOn;
    }

    public static int getDeviceCounter() {
        return deviceCounter;
    }
}

// SmartLight class extending HomeDevice and implementing Automation
class SmartLight extends HomeDevice implements Automation {
    private int brightness;
    private String color;

    public SmartLight(String deviceName) {
        super(deviceName);
        this.brightness = 50;
        this.color = "White";
    }

    @Override
    public void operate() {
        if (isOn()) {
            System.out.println(getDeviceName() + " is operating at " + brightness + "% brightness");
            System.out.println("Color: " + color);
        } else {
            System.out.println(getDeviceName() + " needs to be turned ON first!");
        }
    }

    @Override
    public void autoControl() {
        System.out.println("=== Auto Control Mode ===");
        if (isOn()) {
            // Simulate automatic brightness adjustment
            brightness = 70;
            color = "Warm Yellow";
            System.out.println("Automatically adjusted brightness to " + brightness + "%");
            System.out.println("Changed color to " + color);
        } else {
            turnOn();
            brightness = 50;
            System.out.println("Auto-turned on at " + brightness + "% brightness");
        }
    }

    public void setBrightness(int brightness) {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
            System.out.println("Brightness set to: " + brightness + "%");
        }
    }

    public void setColor(String color) {
        this.color = color;
        System.out.println("Color changed to: " + color);
    }

    public int getBrightness() {
        return brightness;
    }

    public String getColor() {
        return color;
    }
}

// Additional class: SmartThermostat for demonstration
class SmartThermostat extends HomeDevice implements Automation {
    private int temperature;

    public SmartThermostat(String deviceName) {
        super(deviceName);
        this.temperature = 72;
    }

    @Override
    public void operate() {
        if (isOn()) {
            System.out.println(getDeviceName() + " maintaining " + temperature + "F");
        } else {
            System.out.println(getDeviceName() + " is currently OFF");
        }
    }

    @Override
    public void autoControl() {
        System.out.println("=== Auto Control Mode ===");
        // Simulate automatic temperature adjustment
        temperature = 68;
        System.out.println("Automatically adjusted temperature to " + temperature + "F (Energy Saving Mode)");
    }

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("Temperature set to: " + temperature + "F");
    }
}

// Main class
public class SmartHomeSystem {
    public static void main(String[] args) {
        System.out.println("=== SMART HOME SYSTEM ===\n");

        // Display device category using static method
        System.out.println("Category: " + HomeDevice.deviceCategory());
        System.out.println();

        // Creating SmartLight object
        SmartLight livingRoomLight = new SmartLight("Living Room Light");

        System.out.println("--- Smart Light Control ---");
        System.out.println("Device ID: " + livingRoomLight.getDeviceId());
        System.out.println("Device Name: " + livingRoomLight.getDeviceName());

        // Manual control
        livingRoomLight.turnOn();
        livingRoomLight.operate();

        System.out.println();

        // Auto control via interface
        livingRoomLight.autoControl();

        System.out.println();

        // Additional settings
        livingRoomLight.setBrightness(80);
        livingRoomLight.setColor("Blue");
        livingRoomLight.operate();

        System.out.println();
        System.out.println("--- Smart Thermostat Control ---");

        // Creating SmartThermostat object
        SmartThermostat thermostat = new SmartThermostat("Main Thermostat");
        thermostat.turnOn();
        thermostat.operate();
        thermostat.autoControl();

        System.out.println();
        System.out.println("Total devices created: " + HomeDevice.getDeviceCounter());
    }
}
