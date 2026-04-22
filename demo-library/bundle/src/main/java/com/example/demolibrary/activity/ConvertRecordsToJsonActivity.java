package com.example.demolibrary.activity;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.bmc.arsys.rx.services.action.domain.Action;
import com.bmc.arsys.rx.services.action.domain.ActionParameter;
import com.bmc.arsys.rx.services.common.Logger;
import com.bmc.arsys.rx.services.common.Service;
import com.bmc.arsys.rx.services.common.domain.Scope;
import com.bmc.arsys.rx.services.record.RecordService;
import com.example.demolibrary.domain.Book;
import com.example.demolibrary.service.BookService;

import java.util.List;

public class ConvertRecordsToJsonActivity implements Service {

    private static final Logger LOGGER = ServiceLocator.getLogger();
    private final BookService bookService;

    public ConvertRecordsToJsonActivity(RecordService recordService) {
        this.bookService = new BookService(recordService);
    }

    /**
     * Принимает на вход идентификатор автора (Display ID), осуществляет обогащение
     * данных по книгам и издателям переданного автора
     * @param authorId идентификатор автора, в случае елси идентификатор не указан, будет использован
     *                 дефолтный 000000000000001
     * @return список книг, включая автора и издателей
     */
    @Action(name = "convertRecordDefinitionsToJson", scope = Scope.PUBLIC)
    public List<Book> convertRecordDefinitionsToJson(@ActionParameter(name = "authorId") String authorId) {
        if (authorId == null || authorId.isBlank()) {
            authorId = "000000000000001";
            LOGGER.warn("Author ID is not specified, defaulted to 000000000000001");
        }
        return bookService.getTopTwoByAuthor(authorId);
    }
}

