/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import java.time.LocalDate;
import java.util.ArrayList;
import library.system.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


/**
 *
 * @author homar
 */

class LibraryTest {

    @Test
    void displayCatalog() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 5);
        Book book2 = new Book("Title2", "Author2", 3);
        library.getCatalog().add(book1);
        library.getCatalog().add(book2);

        // Redirect System.out to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.displayCatalog();

        // Restore System.out
        System.setOut(System.out);

        String expectedOutput = "Catalog:\nTitle1 by Author1 (Available: 5)\nTitle2 by Author2 (Available: 3)\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void checkoutBooks_ValidSelection() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 5);
        Book book2 = new Book("Title2", "Author2", 3);
        library.getCatalog().add(book1);
        library.getCatalog().add(book2);

        ArrayList<Book> selectedBooks = new ArrayList<>();
        selectedBooks.add(book1);
        selectedBooks.add(book2);

        int result = library.checkoutBooks(selectedBooks);

        assertEquals(0, result);
        assertFalse(book1.isAvailable());
        assertEquals(4, book1.getQuantity());
        assertFalse(book2.isAvailable());
        assertEquals(2, book2.getQuantity());
    }

    @Test
    void checkoutBooks_InvalidQuantity() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 5);
        library.getCatalog().add(book1);

        ArrayList<Book> selectedBooks = new ArrayList<>();
        selectedBooks.add(book1);

        // Redirect System.in to provide input
        ByteArrayInputStream inContent = new ByteArrayInputStream("0\n1\n".getBytes());
        System.setIn(inContent);

        int result = library.checkoutBooks(selectedBooks);

        // Restore System.in
        System.setIn(System.in);

        assertEquals(0, result);
        assertTrue(book1.isAvailable()); // Availability should not change
        assertEquals(5, book1.getQuantity()); // Quantity should not change
    }

    @Test
    void calculateDueDate() {
        Library library = new Library();
        LocalDate currentDate = LocalDate.now();
        Book book = new Book("Title", "Author", 5);

        LocalDate dueDate = library.calculateDueDate(book, currentDate);

        assertEquals(currentDate.plusDays(14), dueDate);
    }

    @Test
    void calculateLateFees() {
        Library library = new Library();
        double lateFee = library.calculateLateFees(5);

        assertEquals(5.0, lateFee);
    }

    @Test
    void returnBooks_SuccessfulReturn() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 5);
        Book book2 = new Book("Title2", "Author2", 3);
        library.getCatalog().add(book1);
        library.getCatalog().add(book2);

        ArrayList<Book> returnedBooks = new ArrayList<>();
        returnedBooks.add(book1);
        returnedBooks.add(book2);

        library.returnBooks(returnedBooks);

        assertTrue(book1.isAvailable());
        assertEquals(6, book1.getQuantity());
        assertTrue(book2.isAvailable());
        assertEquals(4, book2.getQuantity());
    }

    @Test
    void userConfirmation_ConfirmCheckout() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 5);
        Book book2 = new Book("Title2", "Author2", 3);
        library.getCatalog().add(book1);
        library.getCatalog().add(book2);

        ArrayList<Book> selectedBooks = new ArrayList<>();
        selectedBooks.add(book1);
        selectedBooks.add(book2);

        // Redirect System.in to provide input
        ByteArrayInputStream inContent = new ByteArrayInputStream("yes\n".getBytes());
        System.setIn(inContent);

        boolean confirmed = LibraryApp.userConfirmation(selectedBooks,library);

        // Restore System.in
        System.setIn(System.in);

        assertTrue(confirmed);
    }

    @Test
    void userConfirmation_CancelCheckout() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 5);
        Book book2 = new Book("Title2", "Author2", 3);
        library.getCatalog().add(book1);
        library.getCatalog().add(book2);

        ArrayList<Book> selectedBooks = new ArrayList<>();
        selectedBooks.add(book1);
        selectedBooks.add(book2);

        // Redirect System.in to provide input
        ByteArrayInputStream inContent = new ByteArrayInputStream("no\n".getBytes());
        System.setIn(inContent);

        boolean confirmed = LibraryApp.userConfirmation(selectedBooks,library);

        // Restore System.in
        System.setIn(System.in);

        assertFalse(confirmed);
    }

}
