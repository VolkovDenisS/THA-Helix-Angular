package com.example.demolibrary.repo;

import com.bmc.arsys.rx.services.record.RecordService;
import com.example.demolibrary.domain.Publisher;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PublisherRepository extends AbstractRecordRepository<Publisher> {

    private static final String PUBLISHERS_RECORD_DEFINITION = "com.example.demo-library:Publishers";
    private static final int DEFAULT_PAGE_SIZE = 1000;

    public PublisherRepository(RecordService recordService) {
        super(recordService);
    }

    @Override
    protected String getRecordDefinitionName() {
        return PUBLISHERS_RECORD_DEFINITION;
    }

    @Override
    protected Publisher mapToDto(Map<String, Object> row) {
        Publisher publisher = new Publisher();
        publisher.setId(asString(row.get("ID")));
        publisher.setDisplayId(asString(row.get("Display ID")));
        publisher.setCreatedBy(asString(row.get("Created By")));
        publisher.setCreatedDate(asString(row.get("Created Date")));
        publisher.setAssignee(asString(row.get("Assignee")));
        publisher.setModifiedBy(asString(row.get("Modified By")));
        publisher.setModifiedDate(asString(row.get("Modified Date")));
        publisher.setStatus(asString(row.get("Status")));
        publisher.setDescription(asString(row.get("Description")));
        publisher.setPublisherName(asString(row.get("Publisher Name")));
        publisher.setPublisherFrom(asString(row.get("Publisher From")));
        publisher.setPublisherEmail(asString(row.get("Publisher Email")));
        return publisher;
    }

    public List<Publisher> findAll() {
        return fetchRecords(Collections.emptyMap(), DEFAULT_PAGE_SIZE);
    }
}
