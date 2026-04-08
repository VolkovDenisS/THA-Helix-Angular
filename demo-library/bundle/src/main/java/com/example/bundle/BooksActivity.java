package com.example.bundle;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.bmc.arsys.rx.services.action.domain.Action;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.QueryPredicate;
import com.bmc.arsys.rx.services.common.Service;
import com.bmc.arsys.rx.services.common.domain.Scope;
import com.bmc.arsys.rx.services.record.RecordService;
import com.bmc.arsys.rx.services.record.domain.RecordInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by ZotovES on 07.04.2026
 * Активити получения топа книг
 */
public class BooksActivity implements Service {
    private static final String QUERY_TYPE_RECORD_DATA =
            "com.bmc.arsys.rx.application.record.datapage.RecordInstanceDataPageQuery";
    private static final String BOOKS_RECORD_DEFINITION_NAME = "com.example.demo-library:Books";
    private static final String BOOKS_AUTHOR_FIELD_ID = "536870917";
    private static final String BOOKS_AUTHOR_FIELD_NAME = "Display ID";
    protected static final String PROPERTY_SELECTION = "propertySelection";

    @Action(name = "GetTopBooksActivity", scope = Scope.PUBLIC)
    public Object action() {

        ServiceLocator.getLogger().info("Json Converter Log");
        Map<String, QueryPredicate> queryPredicates = new HashMap<>();
        queryPredicates.put(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
                new QueryPredicate(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME, BOOKS_RECORD_DEFINITION_NAME));
        queryPredicates.put(BOOKS_AUTHOR_FIELD_ID,
                new QueryPredicate(BOOKS_AUTHOR_FIELD_ID, "AGGADG1AAP0IEATCCT9RTCCT9RQGN7"));
        final int pageSize = 2;
        final int startIndex = 0;
        RecordService recordService = ServiceLocator.getRecordService();
        List<String> propertySelections = getPropertySelections();

        DataPageQueryParameters params =
                new DataPageQueryParameters(pageSize, startIndex, propertySelections, null, queryPredicates);


        return recordService.getRecordInstancesByIdDataPage(params);
    }

    private static List<String> getPropertySelections() {
        List<String> propertySelections = new ArrayList<>();
        propertySelections.add(Integer.toString(1));
        propertySelections.add(Integer.toString(7));
        propertySelections.add(Integer.toString(8));
        propertySelections.add(String.valueOf(RecordInstance.RECORD_ID_FIELD_ID));
        propertySelections.add(Integer.toString(536870914));
        propertySelections.add(Integer.toString(536870915));
        propertySelections.add(Integer.toString(536870916));
        propertySelections.add(Integer.toString(536870917));
        return propertySelections;
    }
}
