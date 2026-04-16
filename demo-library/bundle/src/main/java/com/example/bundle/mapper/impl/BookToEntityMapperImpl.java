package com.example.bundle.mapper.impl;

import com.bmc.arsys.rx.services.common.DataPage;
import com.example.bundle.domain.Author;
import com.example.bundle.domain.Book;
import com.example.bundle.domain.Publisher;
import com.example.bundle.mapper.Mapper;

import static com.example.bundle.constant.Constants.*;
import static com.example.bundle.mapper.MappingHelper.getStringValueFromDataPage;

/**
 * @author Created by ZotovES on 16.04.2026
 * Реализация маппера из набора данных в энтити Книги
 */
public class BookToEntityMapperImpl implements Mapper<Object, Book> {
    @Override
    public Book toEntity(Object source) {
        if (source instanceof DataPage) {
            DataPage dataPage = (DataPage) source;
            return new Book(
                    getStringValueFromDataPage(dataPage, BOOKS_ID_FIELD_ID),
                    getStringValueFromDataPage(dataPage, BOOKS_DISPLAY_ID_FIELD_ID),
                    getStringValueFromDataPage(dataPage, BOOKS_NAME_FIELD_ID),
                    getStringValueFromDataPage(dataPage, BOOKS_DESCRIPTION_FIELD_ID),
                    getStringValueFromDataPage(dataPage, BOOKS_PRICE_FIELD_ID),
                    new Author(getStringValueFromDataPage(dataPage, BOOKS_AUTHOR_FIELD_ID)),
                    new Publisher(getStringValueFromDataPage(dataPage, BOOKS_PUBLISHER_FIELD_ID)));
        }
        return null;
    }
}
