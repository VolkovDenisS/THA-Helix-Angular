package com.example.bundle.repository.impl;

import com.bmc.arsys.rx.services.common.DataPage;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.annotation.RxInstanceTransactional;
import com.bmc.arsys.rx.services.record.RecordService;
import com.example.bundle.domain.Publisher;
import com.example.bundle.mapper.Mapper;
import com.example.bundle.mapper.impl.PublisherToEntityMapperImpl;
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
    private final Mapper<Object, Publisher> mapper = new PublisherToEntityMapperImpl();
    private final RecordService recordService;

    public PublisherRepoImpl(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * Поиск издателей по списку ид
     *
     * @param ids список ид
     * @return список издателей
     */
    @Override
    @RxInstanceTransactional(readOnly = true, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public Map<String, Publisher> findByIds(Set<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }
        return Optional.ofNullable(recordService.getRecordInstancesByIdDataPage(getDataPageQueryParameters(ids)))
                .map(DataPage::getData).stream()
                .flatMap(Collection::stream)
                .map(mapper::toEntity)
                .collect(Collectors.toMap(Publisher::getId, publisher -> publisher));
    }

    private DataPageQueryParameters getDataPageQueryParameters(Set<String> ids) {
        Map<String, List<String>> dataPageParams = new HashMap<>();
        dataPageParams.put("dataPageType", new ArrayList<>(List.of(QUERY_TYPE_RECORD_DATA)));
        dataPageParams.put("recorddefinition", new ArrayList<>(List.of(PUBLISHERS_RECORD_DEFINITION_NAME)));
        dataPageParams.put("propertySelection", new ArrayList<>(getPropertySelections()));
        dataPageParams.put("startIndex", new ArrayList<>(List.of(Integer.toString(PAGE_START_INDEX_FIRST_PAGE))));
        dataPageParams.put("pageSize", new ArrayList<>(List.of(Integer.toString(Math.max(1, ids.size())))));
        dataPageParams.put("queryExpression", new ArrayList<>(List.of(getQueryExpression(ids))));
        return new DataPageQueryParameters(dataPageParams);
    }

    private static String getQueryExpression(Set<String> ids) {
        String idsFilter = ids.stream().collect(Collectors.joining("\",\"", "\"", "\""));
        return String.format("'%s' IN (%s)", PUBLISHERS_ID_FIELD_ID, idsFilter);
    }

    private static List<String> getPropertySelections() {
        List<String> propertySelections = new ArrayList<>();
        propertySelections.add(String.valueOf(PUBLISHERS_DISPLAY_ID_FIELD_ID));
        propertySelections.add(String.valueOf(PUBLISHERS_ID_FIELD_ID));
        propertySelections.add(String.valueOf(PUBLISHERS_NAME_FIELD_ID));
        return propertySelections;
    }
}
