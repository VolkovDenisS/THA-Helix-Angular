package com.example.demolibrary.service;

import com.bmc.arsys.rx.services.record.domain.RecordInstance;
import com.example.demolibrary.domain.Author;
import com.example.demolibrary.domain.Book;
import com.example.demolibrary.domain.Publisher;
import com.example.demolibrary.repo.AuthorRepository;
import com.example.demolibrary.repo.BookRepository;
import com.example.demolibrary.repo.PublisherRepository;
import com.example.demolibrary.support.InMemoryRecordService;
import com.example.demolibrary.support.TestRecordDefinitions;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class BookServiceTest {

    private static final String AUTHORS = "com.example.demo-library:Authors";
    private static final String PUBLISHERS = "com.example.demo-library:Publishers";
    private static final String BOOKS = "com.example.demo-library:Books";

    private static final int AUTHOR_FULL_NAME = 536870913;
    private static final int AUTHOR_FROM = 536870914;
    private static final int AUTHOR_EMAIL = 536870915;

    private static final int PUBLISHER_NAME = 536870913;
    private static final int PUBLISHER_FROM = 536870914;
    private static final int PUBLISHER_EMAIL = 536870915;

    private static final int BOOK_AUTHOR = 536870913;
    private static final int BOOK_NAME = 536870914;
    private static final int BOOK_DESCRIPTION = 536870915;
    private static final int BOOK_PRICE = 536870916;

    private InMemoryRecordService recordService;
    private BookService service;

    @BeforeMethod
    public void setUp() {
        recordService = new InMemoryRecordService()
                .registerDefinition(TestRecordDefinitions.of(AUTHORS,
                        TestRecordDefinitions.fields(
                                RecordInstance.CORE_ENTRY_FIELD_ID, "Display ID",
                                RecordInstance.RECORD_ID_FIELD_ID, "ID",
                                AUTHOR_FULL_NAME, "Full Name",
                                AUTHOR_FROM, "From",
                                AUTHOR_EMAIL, "Email")))
                .registerDefinition(TestRecordDefinitions.of(PUBLISHERS,
                        TestRecordDefinitions.fields(
                                RecordInstance.CORE_ENTRY_FIELD_ID, "Display ID",
                                RecordInstance.RECORD_ID_FIELD_ID, "ID",
                                PUBLISHER_NAME, "Publisher Name",
                                PUBLISHER_FROM, "Publisher From",
                                PUBLISHER_EMAIL, "Publisher Email")))
                .registerDefinition(TestRecordDefinitions.of(BOOKS,
                        TestRecordDefinitions.fields(
                                RecordInstance.CORE_ENTRY_FIELD_ID, "Display ID",
                                RecordInstance.RECORD_ID_FIELD_ID, "ID",
                                BOOK_AUTHOR, "Author",
                                BOOK_NAME, "Book Name",
                                BOOK_DESCRIPTION, "Book Description",
                                BOOK_PRICE, "Book Price")));

        service = new BookService(recordService);
    }

    @Test
    public void getTopTwoByAuthorEnrichesBooksWithAuthorAndPublishers() {
        seedAuthor("000000000000001", "Leo Tolstoy", "Russia", "leo@tolstoy.ru");
        seedPublisher("000000000000001", "O'Reilly", "USA", "info@oreilly.com");
        seedPublisher("000000000000002", "Penguin", "UK", "info@penguin.co.uk");
        seedBook("000000000000001", "000000000000001", "War and Peace");
        seedBook("000000000000002", "000000000000001", "Anna Karenina");
        seedBook("000000000000003", "000000000000001", "Resurrection");

        List<Book> books = service.findTopBooksWithNested("000000000000001");

        assertEquals(books.size(), 2);
        for (Book book : books) {
            Author author = book.getAuthor();
            assertNotNull(author);
            assertEquals(author.getFullName(), "Leo Tolstoy");
            assertEquals(author.getDisplayId(), "000000000000001");

            List<Publisher> publishers = book.getPublishers();
            assertNotNull(publishers);
            assertEquals(publishers.size(), 2);
            assertEquals(publishers.get(0).getPublisherName(), "O'Reilly");
            assertEquals(publishers.get(1).getPublisherName(), "Penguin");
        }
    }

    @Test
    public void getTopTwoByAuthorReturnsEmptyWhenNoBooks() {
        seedAuthor("000000000000001", "Leo Tolstoy", "Russia", "leo@tolstoy.ru");

        List<Book> books = service.findTopBooksWithNested("000000000000001");
        assertTrue(books.isEmpty());
    }

    @Test
    public void getTopTwoByAuthorSetsAuthorToNullWhenMissing() {
        seedPublisher("000000000000001", "O'Reilly", "USA", "info@oreilly.com");
        seedBook("000000000000001", "000000000000001", "War and Peace");

        List<Book> books = service.findTopBooksWithNested("000000000000001");

        assertEquals(books.size(), 1);
        assertNull(books.get(0).getAuthor());
        assertEquals(books.get(0).getPublishers().size(), 1);
    }

    @Test
    public void getTopTwoByAuthorReturnsAllBooksWhenFewerThanLimit() {
        seedAuthor("000000000000001", "Leo Tolstoy", "Russia", "leo@tolstoy.ru");
        seedBook("000000000000001", "000000000000001", "War and Peace");

        List<Book> books = service.findTopBooksWithNested("000000000000001");

        assertEquals(books.size(), 1);
        assertEquals(books.get(0).getBookName(), "War and Peace");
        assertNotNull(books.get(0).getAuthor());
    }

    @Test
    public void getTopTwoByAuthorReturnsEmptyPublishersListWhenNone() {
        seedAuthor("000000000000001", "Leo Tolstoy", "Russia", "leo@tolstoy.ru");
        seedBook("000000000000001", "000000000000001", "War and Peace");

        List<Book> books = service.findTopBooksWithNested("000000000000001");

        assertEquals(books.size(), 1);
        assertNotNull(books.get(0).getPublishers());
        assertTrue(books.get(0).getPublishers().isEmpty());
    }

    @Test
    public void getTopTwoByAuthorSharesSameAuthorInstanceAcrossBooks() {
        seedAuthor("000000000000001", "Leo Tolstoy", "Russia", "leo@tolstoy.ru");
        seedBook("000000000000001", "000000000000001", "War and Peace");
        seedBook("000000000000002", "000000000000001", "Anna Karenina");

        List<Book> books = service.findTopBooksWithNested("000000000000001");

        assertEquals(books.size(), 2);
        assertSame(books.get(0).getAuthor(), books.get(1).getAuthor());
        assertSame(books.get(0).getPublishers(), books.get(1).getPublishers());
    }

    @Test
    public void getTopTwoByAuthorShouldIgnoresBooksOfOtherAuthors() {
        seedAuthor("000000000000001", "Leo Tolstoy", "Russia", "leo@tolstoy.ru");
        seedAuthor("000000000000002", "Fyodor Dostoevsky", "Russia", "fyodor@dostoevsky.ru");
        seedBook("000000000000001", "000000000000002", "Crime and Punishment");
        seedBook("000000000000002", "000000000000001", "War and Peace");

        List<Book> books = service.findTopBooksWithNested("000000000000001");

        assertEquals(books.size(), 1);
        assertEquals(books.get(0).getBookName(), "War and Peace");
    }

    private void seedAuthor(String displayId, String fullName, String from, String email) {
        Map<Integer, Object> row = new HashMap<>();
        row.put(RecordInstance.CORE_ENTRY_FIELD_ID, displayId);
        row.put(RecordInstance.RECORD_ID_FIELD_ID, "AG-" + displayId);
        row.put(AUTHOR_FULL_NAME, fullName);
        row.put(AUTHOR_FROM, from);
        row.put(AUTHOR_EMAIL, email);
        recordService.addRow(AUTHORS, row);
    }

    private void seedPublisher(String displayId, String name, String from, String email) {
        Map<Integer, Object> row = new HashMap<>();
        row.put(RecordInstance.CORE_ENTRY_FIELD_ID, displayId);
        row.put(RecordInstance.RECORD_ID_FIELD_ID, "PG-" + displayId);
        row.put(PUBLISHER_NAME, name);
        row.put(PUBLISHER_FROM, from);
        row.put(PUBLISHER_EMAIL, email);
        recordService.addRow(PUBLISHERS, row);
    }

    private void seedBook(String displayId, String authorDisplayId, String name) {
        Map<Integer, Object> row = new HashMap<>();
        row.put(RecordInstance.CORE_ENTRY_FIELD_ID, displayId);
        row.put(RecordInstance.RECORD_ID_FIELD_ID, "BG-" + displayId);
        row.put(BOOK_AUTHOR, authorDisplayId);
        row.put(BOOK_NAME, name);
        row.put(BOOK_DESCRIPTION, "desc-" + displayId);
        row.put(BOOK_PRICE, "100");
        recordService.addRow(BOOKS, row);
    }
}
