package com.example.demolibrary.repo;

import com.bmc.arsys.rx.services.record.RecordService;
import com.bmc.arsys.rx.services.record.domain.RecordInstance;
import com.example.demolibrary.domain.Author;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class AuthorRepository extends AbstractRecordRepository<Author> {

    private static final String AUTHORS_RECORD_DEFINITION = "com.example.demo-library:Authors";

    public AuthorRepository(RecordService recordService) {
        super(recordService);
    }

    @Override
    protected String getRecordDefinitionName() {
        return AUTHORS_RECORD_DEFINITION;
    }

    @Override
    protected Author mapToDto(Map<String, Object> row) {
        Author author = new Author();
        author.setId(asString(row.get("ID")));
        author.setDisplayId(asString(row.get("Display ID")));
        author.setCreatedBy(asString(row.get("Created By")));
        author.setCreatedDate(asString(row.get("Created Date")));
        author.setAssignee(asString(row.get("Assignee")));
        author.setModifiedBy(asString(row.get("Modified By")));
        author.setModifiedDate(asString(row.get("Modified Date")));
        author.setStatus(asString(row.get("Status")));
        author.setDescription(asString(row.get("Description")));
        author.setFullName(asString(row.get("Full Name")));
        author.setFrom(asString(row.get("From")));
        author.setEmail(asString(row.get("Email")));
        return author;
    }

    public Optional<Author> findByDisplayId(String displayId) {
        return fetchFirst(Collections.singletonMap(RecordInstance.CORE_ENTRY_FIELD_ID, displayId));
    }
}
