import java.util.*;
import java.io.*;

// Interface
interface Saveable {
    void saveContact(Contact c) throws DuplicateContactException;
}

// Custom Exception
class DuplicateContactException extends Exception {
    DuplicateContactException(String message) {
        super(message);
    }
}

// Contact class
class Contact {
    String name;
    String phone;

    Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}

// ContactBook class
class ContactBook implements Saveable {

    Vector<Contact> contacts = new Vector<>();

    // Save contact
    @Override
    public void saveContact(Contact c) throws DuplicateContactException {

        for (Contact con : contacts) {
            if (con.name.equalsIgnoreCase(c.name)) {
                throw new DuplicateContactException("Duplicate Contact!");
            }
        }

        contacts.add(c);
        System.out.println("Contact Added Successfully.");
    }

    // Display contacts
    void displayContacts() {

        System.out.println("\nContact List:");

        if (contacts.isEmpty()) {
            System.out.println("No Contacts.");
        } else {
            for (Contact c : contacts) {
                System.out.println("Name : " + c.name);
                System.out.println("Phone: " + c.phone);
                System.out.println();
            }
        }
    }

    // Write contacts to file
    void writeToFile() {

        try {
            FileWriter fw = new FileWriter("contacts.txt");

            for (Contact c : contacts) {
                fw.write(c.name + " " + c.phone + "\n");
            }

            fw.close();
            System.out.println("Contacts written to contacts.txt");

        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }
}

// Main class
public class ContactBookDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ContactBook cb = new ContactBook();

        System.out.print("Enter number of contacts: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Input contacts
        for (int i = 0; i < n; i++) {

            System.out.println("\nContact " + (i + 1));

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Phone Number: ");
            String phone = sc.nextLine();

            Contact c = new Contact(name, phone);

            try {
                cb.saveContact(c);
            } catch (DuplicateContactException e) {
                System.out.println(e.getMessage());
            }
        }

        cb.displayContacts();

        cb.writeToFile();

        sc.close();
    }
}