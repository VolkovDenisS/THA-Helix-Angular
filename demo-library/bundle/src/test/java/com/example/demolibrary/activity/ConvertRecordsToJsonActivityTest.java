package com.example.demolibrary.activity;

import com.example.demolibrary.domain.Book;
import com.example.demolibrary.service.BookService;

import com.example.demolibrary.support.InMemoryRecordService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;



public class ConvertRecordsToJsonActivityTest {

    private static final String DEFAULT_AUTHOR_ID = "000000000000001";

    private CapturingBookService bookService;
    private ConvertRecordsToJsonActivity activity;

    @BeforeMethod
    public void setUp() {
        bookService = new CapturingBookService();
        activity = new ConvertRecordsToJsonActivity(new InMemoryRecordService());
    }

    @Test
    public void passesProvidedAuthorIdToService() {
        Book expected = new Book();
        bookService.responseToReturn = Collections.singletonList(expected);

        List<Book> result = activity.convertRecordDefinitionsToJson("some-author");

        assertEquals(bookService.capturedAuthorId, "some-author");
        assertEquals(result.size(), 1);
        assertSame(result.get(0), expected);
    }

    @Test
    public void defaultsWhenAuthorIdIsNull() {
        activity.convertRecordDefinitionsToJson(null);
        assertEquals(bookService.capturedAuthorId, DEFAULT_AUTHOR_ID);
    }

    @Test
    public void defaultsWhenAuthorIdIsEmpty() {
        activity.convertRecordDefinitionsToJson("");
        assertEquals(bookService.capturedAuthorId, DEFAULT_AUTHOR_ID);
    }

    @Test
    public void defaultsWhenAuthorIdIsBlank() {
        activity.convertRecordDefinitionsToJson("   ");
        assertEquals(bookService.capturedAuthorId, DEFAULT_AUTHOR_ID);
    }

    @Test
    public void returnsServiceResponseUnchanged() {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book());
        expected.add(new Book());
        bookService.responseToReturn = expected;

        List<Book> result = activity.convertRecordDefinitionsToJson("x");

        assertSame(result, expected);
    }

    private static final class CapturingBookService extends BookService {

        String capturedAuthorId;
        List<Book> responseToReturn = Collections.emptyList();

        CapturingBookService() {
            super(new InMemoryRecordService());
        }

        @Override
        public List<Book> getTopTwoByAuthor(String authorDisplayId) {
            this.capturedAuthorId = authorDisplayId;
            return responseToReturn;
        }
    }
}
