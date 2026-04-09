package com.example.bundle.mapper;

import com.bmc.arsys.rx.services.record.domain.RecordInstance;
import com.example.bundle.domain.Author;
import com.example.bundle.domain.Book;

import java.util.Map;

import static com.example.bundle.constant.Constants.*;

/**
 * @author Created by ZotovES on 09.04.2026
 * Фибрика маперов
 */
public class EntityMapperFactory {
    /**
     * Маппер записи в сущность автора
     *
     * @param recordInstance запись
     * @return автор
     */
    public static Author mappingToAuthor(RecordInstance recordInstance) {
        return new Author(
                recordInstance.getFieldValue(AUTHORS_ID_FIELD_ID),
                recordInstance.getFieldValue(AUTHORS_DISPLAY_ID_FIELD_ID),
                recordInstance.getFieldValue(AUTHORS_FULL_NAME_FIELD_ID),
                recordInstance.getFieldValue(AUTHORS_FROM_FIELD_ID),
                recordInstance.getFieldValue(AUTHORS_EMAIL_FIELD_ID));
    }

    /**
     * Маппер записи в сущность автора
     *
     * @param recordInstance запись
     * @return автор
     */
    public static Author mappingToAuthor(Map<String, Object> recordInstance) {
        return new Author(
                recordInstance.getOrDefault(String.valueOf(AUTHORS_ID_FIELD_ID), "").toString(),
                recordInstance.getOrDefault(String.valueOf(AUTHORS_DISPLAY_ID_FIELD_ID), "").toString(),
                recordInstance.getOrDefault(String.valueOf(AUTHORS_FULL_NAME_FIELD_ID), "").toString(),
                recordInstance.getOrDefault(String.valueOf(AUTHORS_FROM_FIELD_ID), "").toString(),
                recordInstance.getOrDefault(String.valueOf(AUTHORS_EMAIL_FIELD_ID), "").toString());
    }

    /**
     * Маппер записи в сущность книги
     *
     * @param record запись
     * @return книга
     */
    public static Book mappingToBook(Map<String, Object> record) {
        return new Book(
                record.getOrDefault(String.valueOf(BOOKS_ID_FIELD_ID), "").toString(),
                record.getOrDefault(String.valueOf(BOOKS_DISPLAY_ID_FIELD_ID), "").toString(),
                record.getOrDefault(String.valueOf(BOOKS_NAME_FIELD_ID), "").toString(),
                record.getOrDefault(String.valueOf(BOOKS_DESCRIPTION_FIELD_ID), "").toString(),
                record.getOrDefault(String.valueOf(BOOKS_PRICE_FIELD_ID), "").toString(),
                null,
                null);
    }
}
