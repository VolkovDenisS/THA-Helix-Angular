package com.example.bundle.mapper;

import com.bmc.arsys.rx.services.common.DataPage;

import java.util.Optional;

/**
 * @author Created by ZotovES on 16.04.2026
 * Утилитарный класс с вспомогательными методами для маппинга
 */
public class MappingHelper {
    private MappingHelper() {
    }

    /**
     * Получить из набора данных строковое значение по ид поля
     *
     * @param dataPage набор данных
     * @param fieldId  ид поля
     * @return значение
     */
    public static String getStringValueFromDataPage(DataPage dataPage, int fieldId) {
        return Optional.ofNullable(dataPage.getData().get(fieldId))
                .map(Object::toString)
                .orElse("");
    }
}
