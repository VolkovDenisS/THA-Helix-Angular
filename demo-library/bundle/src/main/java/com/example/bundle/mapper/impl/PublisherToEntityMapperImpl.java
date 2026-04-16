package com.example.bundle.mapper.impl;

import com.bmc.arsys.rx.services.common.DataPage;
import com.example.bundle.domain.Publisher;
import com.example.bundle.mapper.Mapper;

import static com.example.bundle.constant.Constants.*;
import static com.example.bundle.mapper.MappingHelper.getStringValueFromDataPage;

/**
 * @author Created by ZotovES on 16.04.2026
 * Реализация маппера набора данных в энтити Издателя
 */
public class PublisherToEntityMapperImpl implements Mapper<Object, Publisher> {
    @Override
    public Publisher toEntity(Object source) {
        if (source instanceof DataPage) {
            DataPage dataPage = (DataPage) source;
            return new Publisher(
                    getStringValueFromDataPage(dataPage, PUBLISHERS_ID_FIELD_ID),
                    getStringValueFromDataPage(dataPage, PUBLISHERS_DISPLAY_ID_FIELD_ID),
                    getStringValueFromDataPage(dataPage, PUBLISHERS_NAME_FIELD_ID)
            );
        }
        return null;
    }
}
