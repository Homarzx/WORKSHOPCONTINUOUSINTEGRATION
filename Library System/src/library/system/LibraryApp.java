/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package library.system;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author homar
 */
public class LibraryApp {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            displayMenu();
            choice = getValidInput("Enter your choice:", scanner);

            switch (choice) {
                case 1:
                    // Book checkout process
                    handleCheckout(library, scanner);
                    break;
                case 2:
                    // Calculate late fees
                    handleLateFeeCalculation(library, scanner);
                    break;
                case 3:
                    // Book return process
                    handleBookReturn(library, scanner);
                    break;
                case 4:
                    System.out.println("Exiting the library application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        } while (choice != 4);

        // Close the scanner
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("Library Menu:");
        System.out.println("1. Borrow Books");
        System.out.println("2. Calculate Late Fees");
        System.out.println("3. Return Books");
        System.out.println("4. Exit");
    }

    private static void handleCheckout(Library library, Scanner scanner) {
        library.displayCatalog();

        // Book checkout process
        ArrayList<Book> selectedBooks = new ArrayList<>();
        int numBooks = getValidInput("Enter the number of books to borrow:", scanner);

        // Consume the newline character left by the previous input
        scanner.nextLine();

        for (int i = 0; i < numBooks; i++) {
            String title = getStringInput("Enter book title for book " + (i + 1) + ":", scanner);

            // Find the book in the catalog
            Book selectedBook = findBookByTitle(title, library.getCatalog());

            if (selectedBook != null) {
                selectedBooks.add(selectedBook);
            } else {
                System.out.println("Book not found in the catalog. Please try again.");
                i--; // Decrement i to re-enter the current book title
            }
        }

        userConfirmation(selectedBooks,library);
    }
    
    public static boolean userConfirmation(ArrayList<Book> selectedBooks,Library library){
        displayConfirmation(selectedBooks);
        Scanner scanner = new Scanner(System.in);
        if (confirmAction("Do you want to proceed with the checkout? (yes/no):", scanner)) {
            int checkoutResult = library.checkoutBooks(selectedBooks);
            if (checkoutResult == 0) {
                // Successful checkout, proceed with other actions if needed
                System.out.println("Checkout successful!");
                return true;
            } else {
                // Handle checkout failure
                System.out.println("Checkout failed.");
                return false;
            }
        } else {
            System.out.println("Checkout canceled.");
            return false;
        }
    }
    
    private static void displayConfirmation(ArrayList<Book> selectedBooks) {
        System.out.println("Selected Books for Checkout:");

        for (Book selectedBook : selectedBooks) {
            System.out.println("Title: " + selectedBook.getTitle() +
                    ", Quantity: 1, Due Date: [calculate due date here]");
        }
    }

    public static boolean confirmAction(String prompt, Scanner scanner) {
        while (true) {
            System.out.println(prompt);
            String response = scanner.nextLine().toLowerCase();

            if (response.equals("yes")) {
                return true;
            } else if (response.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid response. Please enter 'yes' or 'no'.");
            }
        }
    }

    private static void handleLateFeeCalculation(Library library, Scanner scanner) {
        // Implement late fee calculation logic here
        // (This is just a placeholder, you may need to adjust it based on your actual requirements)
        int daysOverdue = getValidInput("Enter the number of days overdue:", scanner);
        double lateFee = library.calculateLateFees(daysOverdue);
        System.out.println("Late Fee: $" + lateFee);
    }

    private static void handleBookReturn(Library library, Scanner scanner) {
        // Book return process
        ArrayList<Book> returnedBooks = new ArrayList<>();
        int numReturnedBooks = getValidInput("Enter the number of books to return:", scanner);

        // Consume the newline character left by the previous input
        scanner.nextLine();

        for (int i = 0; i < numReturnedBooks; i++) {
            String title = getStringInput("Enter book title for book " + (i + 1) + ":", scanner);

            // Find the book in the catalog
            Book returnedBook = findBookByTitle(title, library.getCatalog());

            if (returnedBook != null) {
                returnedBooks.add(returnedBook);
            } else {
                System.out.println("Book not found in the catalog. Please try again.");
                i--; // Decrement i to re-enter the current book title
            }
        }

        library.returnBooks(returnedBooks);
    }

    private static int getValidInput(String prompt, Scanner scanner) {
        while (true) {
            try {
                System.out.println(prompt);
                int r = scanner.nextInt();
                if(r < 10){
                    return r;
                }
                System.out.println("Invalid input. Only 10 books per order.");
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Clear the buffer
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static String getStringInput(String prompt, Scanner scanner) {
        System.out.println(prompt);
        return scanner.nextLine(); // Use nextLine() to capture the entire line, including spaces
    }

    private static Book findBookByTitle(String title, ArrayList<Book> catalog) {
        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}
