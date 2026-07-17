import java.util.Scanner;

// Abstract class
abstract class Employee {
    String name;

    Employee(String name) {
        this.name = name;
    }

    // Abstract method
    abstract double calculateSalary();
}

// FullTimeEmployee subclass
class FullTimeEmployee extends Employee {

    double monthlySalary;

    FullTimeEmployee(String name, double monthlySalary) {
        super(name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    double calculateSalary() {
        return monthlySalary;
    }
}

// PartTimeEmployee subclass
class PartTimeEmployee extends Employee {

    int hoursWorked;
    double hourlyRate;

    PartTimeEmployee(String name, int hoursWorked, double hourlyRate) {
        super(name);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

// Main class
public class EmployeePayrollSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of employees: ");
        int n = sc.nextInt();
        sc.nextLine();

        Employee[] employees = new Employee[n];

        // Input employee details
        for (int i = 0; i < n; i++) {

            System.out.println("\nEmployee " + (i + 1));

            System.out.print("Enter Employee Type (FullTime/PartTime): ");
            String type = sc.nextLine();

            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine();

            if (type.equalsIgnoreCase("FullTime")) {

                System.out.print("Enter Monthly Salary: ");
                double salary = sc.nextDouble();
                sc.nextLine();

                employees[i] = new FullTimeEmployee(name, salary);

            } else {

                System.out.print("Enter Hours Worked: ");
                int hours = sc.nextInt();

                System.out.print("Enter Hourly Rate: ");
                double rate = sc.nextDouble();
                sc.nextLine();

                employees[i] = new PartTimeEmployee(name, hours, rate);
            }
        }

        System.out.println("\n===== Payroll Details =====");

        double highestSalary = 0;
        String highestPaid = "";

        // Polymorphism
        for (int i = 0; i < employees.length; i++) {

            double salary = employees[i].calculateSalary();

            // 5% bonus if name starts with 'A'
            if (employees[i].name.toUpperCase().startsWith("A")) {
                salary += salary * 0.05;
            }

            System.out.println("Employee Name : " + employees[i].name);
            System.out.println("Salary        : " + salary);
            System.out.println();

            if (salary > highestSalary) {
                highestSalary = salary;
                highestPaid = employees[i].name;
            }
        }

        System.out.println("Highest Paid Employee : " + highestPaid);
        System.out.println("Highest Salary        : " + highestSalary);

        sc.close();
    }
}