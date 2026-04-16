package com.example.bundle.mapper.impl;

import com.bmc.arsys.rx.services.common.DataPage;
import com.example.bundle.domain.Author;
import com.example.bundle.mapper.Mapper;

import static com.example.bundle.constant.Constants.*;
import static com.example.bundle.mapper.MappingHelper.getStringValueFromDataPage;

/**
 * @author Created by ZotovES on 16.04.2026
 * Маппер из набора данных в энтити Автора
 */
public class AuthorToEntityMapperImpl implements Mapper<DataPage, Author> {
    @Override
    public Author toEntity(DataPage source) {
        return new Author(
                getStringValueFromDataPage(source, AUTHORS_ID_FIELD_ID),
                getStringValueFromDataPage(source, AUTHORS_DISPLAY_ID_FIELD_ID),
                getStringValueFromDataPage(source, AUTHORS_FULL_NAME_FIELD_ID),
                getStringValueFromDataPage(source, AUTHORS_FROM_FIELD_ID),
                getStringValueFromDataPage(source, AUTHORS_EMAIL_FIELD_ID));
    }
}
