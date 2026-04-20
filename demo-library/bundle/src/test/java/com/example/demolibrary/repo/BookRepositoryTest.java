package com.example.demolibrary.repo;

import com.bmc.arsys.rx.services.record.domain.RecordInstance;
import com.example.demolibrary.domain.Book;
import com.example.demolibrary.support.InMemoryRecordService;
import com.example.demolibrary.support.TestRecordDefinitions;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookRepositoryTest {

    private static final String BOOKS = "com.example.demo-library:Books";
    private static final int AUTHOR = 536870913;
    private static final int BOOK_NAME = 536870914;
    private static final int BOOK_DESCRIPTION = 536870915;
    private static final int BOOK_PRICE = 536870916;

    private InMemoryRecordService recordService;
    private BookRepository repository;

    @BeforeMethod
    public void setUp() {
        recordService = new InMemoryRecordService()
                .registerDefinition(TestRecordDefinitions.of(BOOKS,
                        TestRecordDefinitions.fields(
                                RecordInstance.CORE_ENTRY_FIELD_ID, "Display ID",
                                RecordInstance.RECORD_ID_FIELD_ID, "ID",
                                AUTHOR, "Author",
                                BOOK_NAME, "Book Name",
                                BOOK_DESCRIPTION, "Book Description",
                                BOOK_PRICE, "Book Price")));
        recordService.addRow(BOOKS, row("000000000000001", "BG-1", "000000000000001", "War and Peace", "Epic", "100"));
        recordService.addRow(BOOKS, row("000000000000002", "BG-2", "000000000000001", "Anna Karenina", "Drama", "90"));
        recordService.addRow(BOOKS, row("000000000000003", "BG-3", "000000000000001", "Resurrection", "Novel", "80"));
        recordService.addRow(BOOKS, row("000000000000004", "BG-4", "000000000000002", "Crime and Punishment", "Thriller", "110"));

        repository = new BookRepository(recordService);
    }

    @Test
    public void findTopByAuthorMustReturnsOnlyMatchingAuthor() {
        List<Book> books = repository.findTopByAuthor("000000000000002", 10);

        assertEquals(books.size(), 1);
        assertEquals(books.get(0).getBookName(), "Crime and Punishment");
    }

    @Test
    public void findTopByAuthorMustRespectsLimit() {
        List<Book> books = repository.findTopByAuthor("000000000000001", 2);

        assertEquals(books.size(), 2);
        assertEquals(books.get(0).getBookName(), "War and Peace");
        assertEquals(books.get(1).getBookName(), "Anna Karenina");
    }

    @Test
    public void findTopByAuthorReturnsEmptyWhenNoMatch() {
        List<Book> books = repository.findTopByAuthor("missing", 10);
        assertTrue(books.isEmpty());
    }

    @Test
    public void findTopByAuthorShouldMapsAllFields() {
        List<Book> books = repository.findTopByAuthor("000000000000001", 1);

        Book first = books.get(0);
        assertEquals(first.getDisplayId(), "000000000000001");
        assertEquals(first.getId(), "BG-1");
        assertEquals(first.getBookName(), "War and Peace");
        assertEquals(first.getBookDescription(), "Epic");
        assertEquals(first.getBookPrice(), "100");
    }

    private static Map<Integer, Object> row(String displayId, String id, String authorDisplayId,
                                            String name, String description, String price) {
        Map<Integer, Object> row = new HashMap<>();
        row.put(RecordInstance.CORE_ENTRY_FIELD_ID, displayId);
        row.put(RecordInstance.RECORD_ID_FIELD_ID, id);
        row.put(AUTHOR, authorDisplayId);
        row.put(BOOK_NAME, name);
        row.put(BOOK_DESCRIPTION, description);
        row.put(BOOK_PRICE, price);
        return row;
    }
}
