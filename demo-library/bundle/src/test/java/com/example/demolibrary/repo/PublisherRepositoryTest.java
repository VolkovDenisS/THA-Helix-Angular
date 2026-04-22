package com.example.demolibrary.repo;

import com.bmc.arsys.rx.services.record.domain.RecordInstance;
import com.example.demolibrary.domain.Publisher;
import com.example.demolibrary.support.InMemoryRecordService;
import com.example.demolibrary.support.TestRecordDefinitions;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PublisherRepositoryTest {

    private static final String PUBLISHERS = "com.example.demo-library:Publishers";
    private static final int PUBLISHER_NAME = 536870913;
    private static final int PUBLISHER_FROM = 536870914;
    private static final int PUBLISHER_EMAIL = 536870915;

    private InMemoryRecordService recordService;

    @BeforeMethod
    public void setUp() {
        recordService = new InMemoryRecordService()
                .registerDefinition(TestRecordDefinitions.of(PUBLISHERS,
                        TestRecordDefinitions.fields(
                                RecordInstance.CORE_ENTRY_FIELD_ID, "Display ID",
                                RecordInstance.RECORD_ID_FIELD_ID, "ID",
                                PUBLISHER_NAME, "Publisher Name",
                                PUBLISHER_FROM, "Publisher From",
                                PUBLISHER_EMAIL, "Publisher Email")));
    }

    @Test
    public void findAllShouldReturnsAllPublishers() {
        recordService.addRow(PUBLISHERS, row("000000000000001", "PG-1", "O'Reilly", "USA", "info@oreilly.com"));
        recordService.addRow(PUBLISHERS, row("000000000000002", "PG-2", "Penguin", "UK", "info@penguin.co.uk"));

        List<Publisher> all = new PublisherRepository(recordService).findAll();

        assertEquals(all.size(), 2);
        assertEquals(all.get(0).getPublisherName(), "O'Reilly");
        assertEquals(all.get(0).getPublisherFrom(), "USA");
        assertEquals(all.get(0).getPublisherEmail(), "info@oreilly.com");
        assertEquals(all.get(1).getDisplayId(), "000000000000002");
        assertEquals(all.get(1).getPublisherName(), "Penguin");
    }

    @Test
    public void findAllShouldReturnsEmptyWhenNoData() {
        List<Publisher> all = new PublisherRepository(recordService).findAll();
        assertTrue(all.isEmpty());
    }

    private static Map<Integer, Object> row(String displayId, String id, String name,
                                            String from, String email) {
        Map<Integer, Object> row = new HashMap<>();
        row.put(RecordInstance.CORE_ENTRY_FIELD_ID, displayId);
        row.put(RecordInstance.RECORD_ID_FIELD_ID, id);
        row.put(PUBLISHER_NAME, name);
        row.put(PUBLISHER_FROM, from);
        row.put(PUBLISHER_EMAIL, email);
        return row;
    }
}
