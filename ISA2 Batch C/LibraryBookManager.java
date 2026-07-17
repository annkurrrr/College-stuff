import java.util.Scanner;

// Abstract class
abstract class LibraryItem {
    String title;

    LibraryItem(String title) {
        this.title = title;
    }

    // Abstract method
    abstract void getDetails();
}

// Book subclass
class Book extends LibraryItem {

    String author;

    Book(String title, String author) {
        super(title);
        this.author = author;
    }

    @Override
    void getDetails() {
        System.out.println("Type   : Book");
        System.out.println("Title  : " + title);
        System.out.println("Author : " + author);
    }
}

// Magazine subclass
class Magazine extends LibraryItem {

    int issueNo;

    Magazine(String title, int issueNo) {
        super(title);
        this.issueNo = issueNo;
    }

    @Override
    void getDetails() {
        System.out.println("Type     : Magazine");
        System.out.println("Title    : " + title);
        System.out.println("Issue No : " + issueNo);
    }
}

// Main class
public class LibraryBookManager {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of library items: ");
        int n = sc.nextInt();
        sc.nextLine();

        LibraryItem[] items = new LibraryItem[n];

        int bookCount = 0;
        int magazineCount = 0;

        // Input
        for (int i = 0; i < n; i++) {

            System.out.println("\nItem " + (i + 1));

            System.out.print("Enter Item Type (Book/Magazine): ");
            String type = sc.nextLine();

            System.out.print("Enter Title: ");
            String title = sc.nextLine();

            if (type.equalsIgnoreCase("Book")) {

                System.out.print("Enter Author: ");
                String author = sc.nextLine();

                items[i] = new Book(title, author);
                bookCount++;

            } else {

                System.out.print("Enter Issue Number: ");
                int issue = sc.nextInt();
                sc.nextLine();

                items[i] = new Magazine(title, issue);
                magazineCount++;
            }
        }

        // Display details
        System.out.println("\n===== Library Items =====");

        for (int i = 0; i < items.length; i++) {

            items[i].getDetails();     // Polymorphism

            if (items[i].title.toLowerCase().contains("java")) {
                System.out.println("*** Java Book/Magazine Found! ***");
            }

            System.out.println();
        }

        // Count items by type
        System.out.println("Total Books      : " + bookCount);
        System.out.println("Total Magazines  : " + magazineCount);

        sc.close();
    }
}