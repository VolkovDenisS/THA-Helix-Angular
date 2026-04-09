package com.example.bundle.repository.impl;

import com.bmc.arsys.rx.services.common.DataPage;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.QueryPredicate;
import com.bmc.arsys.rx.services.common.annotation.RxInstanceTransactional;
import com.bmc.arsys.rx.services.record.RecordService;
import com.example.bundle.domain.Author;
import com.example.bundle.mapper.EntityMapperFactory;
import com.example.bundle.repository.AuthorRepo;
import org.springframework.transaction.annotation.Isolation;

import java.util.*;

import static com.example.bundle.constant.Constants.*;

/**
 * @author Created by ZotovES on 09.04.2026
 * Репозиторий авторов
 */
public class AuthorRepoImpl implements AuthorRepo {
    private static final int PAGE_SIZE_FIND_ONE_RECORD = 1;
    private static final int PAGE_START_INDEX_FIND_ONE_RECORD = 0;
    private final RecordService recordService;

    public AuthorRepoImpl(RecordService recordService) {this.recordService = recordService;}

    /**
     * Поиск автора по ид отображения
     *
     * @param displayId ид отображения
     * @return автор
     */
    @Override
    @RxInstanceTransactional(readOnly = true, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public Optional<Author> findByDisplayId(String displayId) {
        //noinspection unchecked
        return Optional.ofNullable(recordService.getRecordInstancesByIdDataPage(getDataPageQueryParameters(displayId)))
                .map(DataPage::getData).stream()
                .flatMap(Collection::stream)
                .findFirst()
                .map(val -> (HashMap<String, Object>) val)
                .map(EntityMapperFactory::mappingToAuthor);
    }

    private static DataPageQueryParameters getDataPageQueryParameters(String displayId) {
        Map<String, QueryPredicate> queryPredicates = new HashMap<>();
        queryPredicates.put(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
                new QueryPredicate(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
                        AUTHORS_RECORD_DEFINITION_NAME));
        String key = String.valueOf(AUTHORS_DISPLAY_ID_FIELD_ID);
        queryPredicates.put(key, new QueryPredicate(key, displayId));

        return new DataPageQueryParameters(PAGE_SIZE_FIND_ONE_RECORD, PAGE_START_INDEX_FIND_ONE_RECORD,
                getPropertySelections(), null, queryPredicates);
    }

    private static List<String> getPropertySelections() {
        List<String> propertySelections = new ArrayList<>();
        propertySelections.add(String.valueOf(AUTHORS_ID_FIELD_ID));
        propertySelections.add(Integer.toString(AUTHORS_DISPLAY_ID_FIELD_ID));
        propertySelections.add(Integer.toString(AUTHORS_FULL_NAME_FIELD_ID));
        propertySelections.add(Integer.toString(AUTHORS_FROM_FIELD_ID));
        propertySelections.add(Integer.toString(AUTHORS_EMAIL_FIELD_ID));
        return propertySelections;
    }
}
