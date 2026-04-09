package com.example.bundle.constant;

import com.bmc.arsys.rx.services.record.domain.RecordInstance;

/**
 * @author Created by ZotovES on 09.04.2026
 * Константы
 */
public interface Constants {
    //Автор
    String AUTHORS_RECORD_DEFINITION_NAME = "com.example.demo-library:Authors";
    int AUTHORS_ID_FIELD_ID = RecordInstance.RECORD_ID_FIELD_ID;
    int AUTHORS_DISPLAY_ID_FIELD_ID = RecordInstance.CORE_ENTRY_FIELD_ID;
    int AUTHORS_FULL_NAME_FIELD_ID = 536870913;
    int AUTHORS_FROM_FIELD_ID = 536870914;
    int AUTHORS_EMAIL_FIELD_ID = 536870915;

    //Книга
    String BOOKS_RECORD_DEFINITION_NAME = "com.example.demo-library:Books";
    int BOOKS_ID_FIELD_ID = RecordInstance.RECORD_ID_FIELD_ID;
    int BOOKS_DISPLAY_ID_FIELD_ID = RecordInstance.CORE_ENTRY_FIELD_ID;
    int BOOKS_NAME_FIELD_ID = 536870914;
    int BOOKS_DESCRIPTION_FIELD_ID = 536870915;
    int BOOKS_PRICE_FIELD_ID = 536870916;
    int BOOKS_AUTHOR_FIELD_ID = 536870917;

    //Издатель
    String PUBLISHERS_RECORD_DEFINITION_NAME = "com.example.demo-library:Publishers";
    int PUBLISHERS_ID_FIELD_ID = RecordInstance.RECORD_ID_FIELD_ID;
    int PUBLISHERS_DISPLAY_ID_FIELD_ID = RecordInstance.CORE_ENTRY_FIELD_ID;
    int PUBLISHERS_NAME_FIELD_ID = 536870913;
}
