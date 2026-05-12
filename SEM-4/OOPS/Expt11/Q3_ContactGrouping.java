import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class Q3_ContactGrouping {
    static class Contact {
        String name;
        String phoneNumber;
        public Contact(String name, String phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
        }
        @Override
        public String toString() {
            return "Contact{" +
                    "name='" + name + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }
    }

    public static void groupContactsByFirstLetter(ArrayList<Contact> contacts) {
        // Get all unique first letters
        Set<Character> letters = new HashSet<>();
        for (Contact contact : contacts) {
            if (contact.name.length() > 0) {
                letters.add(Character.toUpperCase(contact.name.charAt(0)));
            }
        }
        
        // Sort letters for better display
        ArrayList<Character> sortedLetters = new ArrayList<>(letters);
        sortedLetters.sort(Character::compareTo);
        
        // Print contacts grouped by first letter
        for (Character letter : sortedLetters) {
            System.out.println("\n--- " + letter + " ---");
            boolean found = false;
            for (Contact contact : contacts) {
                if (contact.name.length() > 0 && 
                    Character.toUpperCase(contact.name.charAt(0)) == letter) {
                    System.out.println(contact);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No contacts");
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Contact> contacts = new ArrayList<>();
        System.out.println("=== Contact Grouping by First Letter ===");
        System.out.print("Enter the number of contacts: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("\nContact " + (i + 1) + ":");
            System.out.print("  Name: ");
            String name = sc.nextLine();
            System.out.print("  Phone Number: ");
            String phoneNumber = sc.nextLine();
            contacts.add(new Contact(name, phoneNumber));
        }
        System.out.println("\n=== Contacts Grouped by First Letter ===");
        groupContactsByFirstLetter(contacts);
        sc.close();
    }
}
