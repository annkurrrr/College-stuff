import java.util.Scanner;

// Abstract class
abstract class MedicalStaff {
    String staffName;

    MedicalStaff(String staffName) {
        this.staffName = staffName;
    }

    // Abstract method
    abstract void getDepartment();
}

// Doctor subclass
class Doctor extends MedicalStaff {

    Doctor(String staffName) {
        super(staffName);
    }

    @Override
    void getDepartment() {
        System.out.println("Department : Doctor");
    }
}

// Nurse subclass
class Nurse extends MedicalStaff {

    Nurse(String staffName) {
        super(staffName);
    }

    @Override
    void getDepartment() {
        System.out.println("Department : Nurse");
    }
}

// Main class
public class HospitalPatientTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] patients = new String[5];

        // Select staff
        System.out.print("Enter Staff Role (Doctor/Nurse): ");
        String role = sc.nextLine();

        System.out.print("Enter Staff Name: ");
        String staffName = sc.nextLine();

        // Polymorphism
        MedicalStaff staff;

        if (role.equalsIgnoreCase("Doctor")) {
            staff = new Doctor(staffName);
        } else {
            staff = new Nurse(staffName);
        }

        staff.getDepartment();

        // Admit patients
        System.out.println("\nEnter 5 Patient Names:");

        for (int i = 0; i < 5; i++) {
            System.out.print("Patient " + (i + 1) + ": ");
            patients[i] = sc.nextLine().trim();   // Format name using trim()
        }

        // Search for duplicate names
        System.out.println("\nDuplicate Patients:");

        boolean duplicate = false;

        for (int i = 0; i < patients.length; i++) {
            for (int j = i + 1; j < patients.length; j++) {

                if (patients[i].equalsIgnoreCase(patients[j])) {
                    System.out.println(patients[i]);
                    duplicate = true;
                }
            }
        }

        if (!duplicate) {
            System.out.println("No duplicate patients.");
        }

        // Display all patients
        System.out.println("\nCurrent Patients:");

        for (int i = 0; i < patients.length; i++) {
            System.out.println((i + 1) + ". " + patients[i].toUpperCase());
        }

        // Discharge patient
        System.out.print("\nEnter patient name to discharge: ");
        String discharge = sc.nextLine();

        boolean found = false;

        for (int i = 0; i < patients.length; i++) {

            if (patients[i].equalsIgnoreCase(discharge)) {
                patients[i] = "Discharged";
                found = true;
                break;
            }
        }

        if (found)
            System.out.println("Patient Discharged Successfully.");
        else
            System.out.println("Patient Not Found.");

        // Display updated patient list
        System.out.println("\nUpdated Patient List:");

        for (int i = 0; i < patients.length; i++) {
            System.out.println((i + 1) + ". " + patients[i]);
        }

        sc.close();
    }
}