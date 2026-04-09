package com.example.bundle.repository.impl;

import com.bmc.arsys.rx.services.common.DataPage;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.QueryPredicate;
import com.bmc.arsys.rx.services.common.annotation.RxInstanceTransactional;
import com.bmc.arsys.rx.services.record.RecordService;
import com.bmc.arsys.rx.services.record.domain.RecordInstance;
import com.example.bundle.domain.Book;
import com.example.bundle.mapper.EntityMapperFactory;
import com.example.bundle.repository.BookRepo;
import org.springframework.transaction.annotation.Isolation;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.bundle.constant.Constants.BOOKS_AUTHOR_FIELD_ID;
import static com.example.bundle.constant.Constants.BOOKS_RECORD_DEFINITION_NAME;

/**
 * @author Created by ZotovES on 09.04.2026
 * Репозиторий книг
 */
public class BookRepoImpl implements BookRepo {
    private static final int PAGE_SIZE_FIND_TOP = 2; //Кол-во элементов в списке топа книг по ТЗ
    private static final int PAGE_START_INDEX_FIRST_PAGE = 0;
    private final RecordService recordService;

    public BookRepoImpl(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * Поиск списка с топом книг по ид автора
     *
     * @param authorId ид автора
     * @return список книг
     */
    @Override
    @RxInstanceTransactional(readOnly = true, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public List<Book> findTopBooksByAuthorId(String authorId) {
        //noinspection unchecked
        return Optional.ofNullable(recordService.getRecordInstancesByIdDataPage(getDataPageQueryParameters(authorId)))
                .map(DataPage::getData).stream()
                .flatMap(Collection::stream)
                .map(val -> (HashMap<String, Object>) val)
                .map(EntityMapperFactory::mappingToBook)
                .collect(Collectors.toList());
    }

    private static DataPageQueryParameters getDataPageQueryParameters(String authorId) {
        Map<String, QueryPredicate> queryPredicates = new HashMap<>();
        queryPredicates.put(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
                new QueryPredicate(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME, BOOKS_RECORD_DEFINITION_NAME));
        queryPredicates.put(String.valueOf(BOOKS_AUTHOR_FIELD_ID),
                new QueryPredicate(String.valueOf(BOOKS_AUTHOR_FIELD_ID), authorId));

        return new DataPageQueryParameters(PAGE_SIZE_FIND_TOP, PAGE_START_INDEX_FIRST_PAGE,
                getPropertySelections(), null, queryPredicates);
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
