package com.example.bundle.repository.impl;

import com.bmc.arsys.rx.services.common.DataPage;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.QueryPredicate;
import com.bmc.arsys.rx.services.common.SortByValue;
import com.bmc.arsys.rx.services.common.annotation.RxInstanceTransactional;
import com.bmc.arsys.rx.services.record.RecordService;
import com.example.bundle.domain.Book;
import com.example.bundle.mapper.Mapper;
import com.example.bundle.mapper.impl.BookToEntityMapperImpl;
import com.example.bundle.repository.BookRepo;
import org.springframework.transaction.annotation.Isolation;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.bundle.constant.Constants.*;

/**
 * @author Created by ZotovES on 09.04.2026
 * Репозиторий книг
 */
public class BookRepoImpl implements BookRepo {
    private static final int PAGE_SIZE_FIND_TOP = 2; //Кол-во элементов в списке топа книг по ТЗ
    private static final int PAGE_START_INDEX_FIRST_PAGE = 0;
    private final Mapper<Object, Book> mapper = new BookToEntityMapperImpl();
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
        return Optional.ofNullable(recordService.getRecordInstancesByIdDataPage(getDataPageQueryParameters(authorId)))
                .map(DataPage::getData).stream()
                .flatMap(Collection::stream)
                .map(mapper::toEntity)
                .collect(Collectors.toList());
    }

    private static DataPageQueryParameters getDataPageQueryParameters(String authorId) {
        Map<String, QueryPredicate> queryPredicates = new HashMap<>();
        queryPredicates.put(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
                new QueryPredicate(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME, BOOKS_RECORD_DEFINITION_NAME));
        queryPredicates.put(String.valueOf(BOOKS_AUTHOR_FIELD_ID),
                new QueryPredicate(String.valueOf(BOOKS_AUTHOR_FIELD_ID), authorId));
        SortByValue sortByValue = new SortByValue(String.valueOf(BOOKS_PRICE_FIELD_ID), false);
        return new DataPageQueryParameters(PAGE_SIZE_FIND_TOP, PAGE_START_INDEX_FIRST_PAGE,
                getPropertySelections(), List.of(sortByValue), queryPredicates);
    }

    private static List<String> getPropertySelections() {
        List<String> propertySelections = new ArrayList<>();
        propertySelections.add(String.valueOf(BOOKS_DISPLAY_ID_FIELD_ID));
        propertySelections.add(String.valueOf(BOOKS_ID_FIELD_ID));
        propertySelections.add(String.valueOf(BOOKS_NAME_FIELD_ID));
        propertySelections.add(String.valueOf(BOOKS_DESCRIPTION_FIELD_ID));
        propertySelections.add(String.valueOf(BOOKS_PRICE_FIELD_ID));
        propertySelections.add(String.valueOf(BOOKS_AUTHOR_FIELD_ID));
        propertySelections.add(String.valueOf(BOOKS_PUBLISHER_FIELD_ID));
        return propertySelections;
    }
}
