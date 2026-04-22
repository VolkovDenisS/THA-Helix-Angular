package com.example.demolibrary.repo;

import com.bmc.arsys.rx.services.record.RecordService;
import com.example.demolibrary.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BookRepository extends AbstractRecordRepository<Book> {

    private static final String BOOKS_RECORD_DEFINITION = "com.example.demo-library:Books";
    private static final int BOOKS_AUTHOR_FIELD_ID = 536870913;

    public BookRepository(RecordService recordService) {
        super(recordService);
    }

    @Override
    protected String getRecordDefinitionName() {
        return BOOKS_RECORD_DEFINITION;
    }

    @Override
    protected Book mapToDto(Map<String, Object> row) {
        Book book = new Book();
        book.setId(asString(row.get("ID")));
        book.setDisplayId(asString(row.get("Display ID")));
        book.setCreatedBy(asString(row.get("Created By")));
        book.setCreatedDate(asString(row.get("Created Date")));
        book.setAssignee(asString(row.get("Assignee")));
        book.setModifiedBy(asString(row.get("Modified By")));
        book.setModifiedDate(asString(row.get("Modified Date")));
        book.setStatus(asString(row.get("Status")));
        book.setDescription(asString(row.get("Description")));
        book.setBookName(asString(row.get("Book Name")));
        book.setBookDescription(asString(row.get("Book Description")));
        book.setBookPrice(asString(row.get("Book Price")));
        return book;
    }

    public List<Book> findTopByAuthor(String authorDisplayId, int limit) {
        return fetchRecords(
                Collections.singletonMap(BOOKS_AUTHOR_FIELD_ID, authorDisplayId),
                limit);
    }
}
