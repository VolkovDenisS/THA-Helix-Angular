package com.example.demolibrary.repo;

import com.bmc.arsys.rx.services.record.domain.RecordInstance;
import com.example.demolibrary.domain.Author;
import com.example.demolibrary.support.InMemoryRecordService;
import com.example.demolibrary.support.TestRecordDefinitions;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class AuthorRepositoryTest {

    private static final String AUTHORS = "com.example.demo-library:Authors";
    private static final int FULL_NAME = 536870913;
    private static final int FROM = 536870914;
    private static final int EMAIL = 536870915;

    private InMemoryRecordService recordService;
    private AuthorRepository repository;

    @BeforeMethod
    public void setUp() {
        recordService = new InMemoryRecordService()
                .registerDefinition(TestRecordDefinitions.of(AUTHORS,
                        TestRecordDefinitions.fields(
                                RecordInstance.CORE_ENTRY_FIELD_ID, "Display ID",
                                RecordInstance.RECORD_ID_FIELD_ID, "ID",
                                FULL_NAME, "Full Name",
                                FROM, "From",
                                EMAIL, "Email")));
        recordService.addRow(AUTHORS, row("000000000000001", "AG-1", "Leo Tolstoy", "Russia", "leo@tolstoy.ru"));
        recordService.addRow(AUTHORS, row("000000000000002", "AG-2", "Fyodor Dostoevsky", "Russia", "fyodor@dostoevsky.ru"));

        repository = new AuthorRepository(recordService);
    }

    @Test
    public void findByDisplayIdReturnsMatchingAuthor() {
        Optional<Author> author = repository.findByDisplayId("000000000000001");

        assertTrue(author.isPresent());
        Author found = author.get();
        assertEquals(found.getDisplayId(), "000000000000001");
        assertEquals(found.getId(), "AG-1");
        assertEquals(found.getFullName(), "Leo Tolstoy");
        assertEquals(found.getFrom(), "Russia");
        assertEquals(found.getEmail(), "leo@tolstoy.ru");
    }

    @Test
    public void findByDisplayIdReturnsEmptyWhenNotFound() {
        Optional<Author> author = repository.findByDisplayId("missing");
        assertFalse(author.isPresent());
    }

    @Test
    public void findByDisplayIdShouldIgnoresRowsWithOtherDisplayId() {
        Optional<Author> author = repository.findByDisplayId("000000000000002");
        assertTrue(author.isPresent());
        assertNotNull(author.get().getFullName());
        assertEquals(author.get().getFullName(), "Fyodor Dostoevsky");
    }

    private static Map<Integer, Object> row(String displayId, String id, String fullName,
                                            String from, String email) {
        Map<Integer, Object> row = new HashMap<>();
        row.put(RecordInstance.CORE_ENTRY_FIELD_ID, displayId);
        row.put(RecordInstance.RECORD_ID_FIELD_ID, id);
        row.put(FULL_NAME, fullName);
        row.put(FROM, from);
        row.put(EMAIL, email);
        return row;
    }
}
