import java.util.Scanner;

// Abstract class
abstract class Pet {
    String petName;

    Pet(String petName) {
        this.petName = petName;
    }

    // Abstract method
    abstract void describe();
}

// Dog subclass
class Dog extends Pet {

    Dog(String petName) {
        super(petName);
    }

    @Override
    void describe() {
        System.out.println("Type : Dog");
        System.out.println("Pet Name : " + petName);
    }
}

// Cat subclass
class Cat extends Pet {

    Cat(String petName) {
        super(petName);
    }

    @Override
    void describe() {
        System.out.println("Type : Cat");
        System.out.println("Pet Name : " + petName);
    }
}

// Main class
public class PetAdoptionCenter {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of pets: ");
        int n = sc.nextInt();
        sc.nextLine();

        Pet[] pets = new Pet[n];

        // Input pet details
        for (int i = 0; i < n; i++) {

            System.out.println("\nPet " + (i + 1));

            System.out.print("Enter Pet Type (Dog/Cat): ");
            String type = sc.nextLine();

            System.out.print("Enter Pet Name: ");
            String name = sc.nextLine();

            if (type.equalsIgnoreCase("Dog")) {
                pets[i] = new Dog(name);
            } else {
                pets[i] = new Cat(name);
            }
        }

        // Display pet details
        System.out.println("\n===== Pet Details =====");

        for (int i = 0; i < pets.length; i++) {

            pets[i].describe(); // Polymorphism

            // Check if name ends with 'y'
            if (pets[i].petName.toLowerCase().endsWith("y")) {
                System.out.println("Status : Popular");
            }

            System.out.println();
        }

        // Return pets of a specific type
        System.out.print("Enter pet type to display (Dog/Cat): ");
        String searchType = sc.nextLine();

        Pet[] result = new Pet[n];
        int count = 0;

        for (int i = 0; i < pets.length; i++) {

            if (searchType.equalsIgnoreCase("Dog") && pets[i] instanceof Dog) {
                result[count++] = pets[i];
            }
            else if (searchType.equalsIgnoreCase("Cat") && pets[i] instanceof Cat) {
                result[count++] = pets[i];
            }
        }

        System.out.println("\n" + searchType + " List:");

        if (count == 0) {
            System.out.println("No pets found.");
        } else {
            for (int i = 0; i < count; i++) {
                result[i].describe();
                System.out.println();
            }
        }

        sc.close();
    }
}