package com.example.bundle.repository.impl;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.bmc.arsys.rx.services.association.AssociationService;
import com.bmc.arsys.rx.services.common.DataPage;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.QueryPredicate;
import com.bmc.arsys.rx.services.common.annotation.RxInstanceTransactional;
import com.bmc.arsys.rx.services.record.RecordService;
import com.example.bundle.domain.Publisher;
import com.example.bundle.mapper.EntityMapperFactory;
import com.example.bundle.repository.PublisherRepo;
import org.springframework.transaction.annotation.Isolation;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.bundle.constant.Constants.*;

/**
 * @author Created by ZotovES on 09.04.2026
 * Репозиторий издателей
 */
public class PublisherRepoImpl implements PublisherRepo {
    private static final int PAGE_START_INDEX_FIRST_PAGE = 0;
    private static final String QUERY_TYPE_RECORD_DATA =
            "com.bmc.arsys.rx.application.record.datapage.RecordInstanceDataPageQuery";

    public PublisherRepoImpl(RecordService recordService) {
        this.recordService = recordService;
    }

    private final RecordService recordService;

    /**
     * Поиск издателей по списку ид
     *
     * @param ids список ид
     * @return список издателей
     */
    @Override
    @RxInstanceTransactional(readOnly = true, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public Map<String, Publisher> findByIds(Set<String> ids) {
        //noinspection unchecked
        return Optional.ofNullable(recordService.getRecordInstancesByIdDataPage(getDataPageQueryParameters(ids)))
                .map(DataPage::getData).stream()
                .flatMap(Collection::stream)
                .map(val -> (HashMap<String, Object>) val)
                .map(EntityMapperFactory::mappingToPublisher)
                .collect(Collectors.toMap(Publisher::getId, publisher -> publisher));
//        return Optional.ofNullable(getAssociatedRecords("com.example.demo-library:Publishers_Books", id, true, ids.size()))
//                .map(DataPage::getData).stream()
//                .flatMap(Collection::stream)
//                .map(val -> (HashMap<String, Object>) val)
//                .map(EntityMapperFactory::mappingToPublisher)
//                .collect(Collectors.toMap(Publisher::getId, publisher -> publisher));
    }

    private DataPageQueryParameters getDataPageQueryParameters(Set<String> ids) {
//        Map<String, QueryPredicate> queryPredicates = new HashMap<>();
//        queryPredicates.put(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
//                new QueryPredicate(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME, PUBLISHERS_RECORD_DEFINITION_NAME));
//        queryPredicates.put(String.valueOf(PUBLISHERS_ID_FIELD_ID),
//                new QueryPredicate(String.valueOf(BOOKS_AUTHOR_FIELD_ID), ids));
        String idsFilter = "(" + ids.stream().collect(Collectors.joining("\",\"", "\"", "\"")) + ")";
        Map<String, List<String>> dataPageParams = new HashMap<>();
        dataPageParams.put("dataPageType", new ArrayList<>(List.of(QUERY_TYPE_RECORD_DATA)));
        dataPageParams.put("recorddefinition", new ArrayList<>(List.of(PUBLISHERS_RECORD_DEFINITION_NAME)));
        dataPageParams.put("propertySelection", new ArrayList<>(getPropertySelections()));
        dataPageParams.put("startIndex", new ArrayList<>(List.of(Integer.toString(PAGE_START_INDEX_FIRST_PAGE))));
        dataPageParams.put("pageSize", new ArrayList<>(List.of(Integer.toString(ids.size()))));
        dataPageParams.put("queryExpression", new ArrayList<>(List.of("'379' IN " + idsFilter)));
        return new DataPageQueryParameters(dataPageParams);
//        return new DataPageQueryParameters(ids.size(), PAGE_START_INDEX_FIRST_PAGE,
//                getPropertySelections(), null, queryPredicates);
    }

    private static List<String> getPropertySelections() {
        List<String> propertySelections = new ArrayList<>();
        propertySelections.add(String.valueOf(PUBLISHERS_DISPLAY_ID_FIELD_ID));
        propertySelections.add(String.valueOf(PUBLISHERS_ID_FIELD_ID));
        propertySelections.add(String.valueOf(PUBLISHERS_NAME_FIELD_ID));
        return propertySelections;
    }

    private DataPage getAssociatedRecords(String associationName, String startingRecordId, boolean isFirstRecord,
            int maxRecords) {

        AssociationService associationService = ServiceLocator.getAssociationService();
//associationService.associate(associationName,startingRecordId,"AGGADG1AAP0IEATCCTL9TCCTL9QGSH");
        Map<String, QueryPredicate> queryPredicatesByName = new HashMap<String, QueryPredicate>();
        queryPredicatesByName.put("associationDefinition", new QueryPredicate("associationDefinition", associationName));
        queryPredicatesByName.put("nodeToQuery", new QueryPredicate("nodeToQuery", (isFirstRecord) ? "nodeA" : "nodeB"));
        queryPredicatesByName.put("associatedRecordInstanceId",
                new QueryPredicate("associatedRecordInstanceId", startingRecordId));

        DataPageQueryParameters params = new DataPageQueryParameters(maxRecords, 0, null, null, queryPredicatesByName);
        return associationService.getAssociationInstancesByIdDataPage(params);
    }
}
