package com.example.bundle.repository.impl;

import com.bmc.arsys.rx.services.common.DataPage;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.QueryPredicate;
import com.bmc.arsys.rx.services.common.annotation.RxInstanceTransactional;
import com.bmc.arsys.rx.services.record.RecordService;
import com.example.bundle.domain.Author;
import com.example.bundle.mapper.EntityMapperFactory;
import com.example.bundle.repository.AuthorsRepo;
import org.springframework.transaction.annotation.Isolation;

import java.util.*;

import static com.example.bundle.constant.Constants.*;

/**
 * @author Created by ZotovES on 09.04.2026
 * Репозиторий авторов
 */
public class AuthorsRepoImpl implements AuthorsRepo {
    private final RecordService recordService;

    public AuthorsRepoImpl(RecordService recordService) {this.recordService = recordService;}

    /**
     * Поиск автора по ид отображения
     *
     * @param displayId ид отображения
     * @return автор
     */
    @Override
    @RxInstanceTransactional(readOnly = true, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public Optional<Author> findByDisplayId(String displayId) {
        final int pageSize = 1;
        final int startIndex = 0;
        Map<String, QueryPredicate> queryPredicates = new HashMap<>();
        queryPredicates.put(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
                new QueryPredicate(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
                        AUTHORS_RECORD_DEFINITION_NAME));
        String key = String.valueOf(AUTHORS_DISPLAY_ID_FIELD_ID);
        queryPredicates.put(key, new QueryPredicate(key, displayId));

        DataPageQueryParameters params = new DataPageQueryParameters(pageSize, startIndex, getPropertySelections(),
                null, queryPredicates);


        //noinspection unchecked
        return Optional.ofNullable(recordService.getRecordInstancesByIdDataPage(params))
                .map(DataPage::getData).stream()
                .flatMap(Collection::stream)
                .findFirst()
//                .filter(val-> val instanceof HashMap )
                .map(val -> (HashMap<String, Object>) val)
                .map(EntityMapperFactory::mappingToAuthor);
//        RecordInstance recordInstance = recordService.getRecordInstancesByIdDataPage(AUTHORS_RECORD_DEFINITION_NAME, displayId);
//        return Optional.of(mappingToAuthor(recordInstance));
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
