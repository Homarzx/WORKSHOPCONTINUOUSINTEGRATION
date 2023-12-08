/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.system;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author homar
 */
public class Library {
    private ArrayList<Book> catalog;

    public Library() {
        catalog = new ArrayList<>();
        // Initialize the library catalog with some books and quantities
        catalog.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 3));
        catalog.add(new Book("To Kill a Mockingbird", "Harper Lee", 5));
        catalog.add(new Book("1984", "George Orwell", 2));
        catalog.add(new Book("Pride and Prejudice", "Jane Austen", 4));
        catalog.add(new Book("The Hobbit", "J.R.R. Tolkien", 6));
        // Add more books as needed
    }

    public void displayCatalog() {
        System.out.println("Catalog:");
        for (Book book : catalog) {
            System.out.println(book.getTitle() + " by " + book.getAuthor() +
                    " - Available: " + book.getQuantity());
        }
    }

    public int checkoutBooks(ArrayList<Book> selectedBooks) {
        // Check if selected books are available and update quantities
        for (Book selectedBook : selectedBooks) {
            if (!selectedBook.isAvailable() || selectedBook.getQuantity() == 0) {
                System.out.println("Error: Book " + selectedBook.getTitle() + " is not available.");
                return -1; // Return -1 to indicate checkout failure
            }
        }

        // Calculate due date and update book availability
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 14); // 14 days loan period

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dueDate = dateFormat.format(calendar.getTime());

        System.out.println("Due Date: " + dueDate);

        for (Book selectedBook : selectedBooks) {
            selectedBook.setQuantity(selectedBook.getQuantity() - 1);
        }

        return 0; // Return 0 to indicate successful checkout
    }

    public double calculateLateFees(int daysOverdue) {
        // Implement late fee calculation logic here
        return daysOverdue * 1.0; // $1 per day
    }

    public void returnBooks(ArrayList<Book> returnedBooks) {
        // Implement the return process here
        for (Book returnedBook : returnedBooks) {
            returnedBook.setAvailable(true);
            returnedBook.setQuantity(returnedBook.getQuantity() + 1);
        }
    }

    public ArrayList<Book> getCatalog() {
        return catalog;
    }
    
    public LocalDate calculateDueDate(Book book, LocalDate currentDate) {
        // Verificar si el libro tiene una fecha de vencimiento previa
        if (book.getDueDate() != null && book.getDueDate().isAfter(currentDate)) {
            return book.getDueDate(); // Si ya hay una fecha de vencimiento, no la cambiamos
        } else {
            // Calcular la nueva fecha de vencimiento sumando 14 días a la fecha actual
            return currentDate.plusDays(14);
        }
    }
}