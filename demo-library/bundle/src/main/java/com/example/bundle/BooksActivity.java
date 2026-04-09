package com.example.bundle;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.bmc.arsys.rx.services.action.domain.Action;
import com.bmc.arsys.rx.services.common.Logger;
import com.bmc.arsys.rx.services.common.Service;
import com.bmc.arsys.rx.services.common.domain.Scope;
import com.bmc.arsys.rx.services.record.RecordService;
import com.example.bundle.domain.Author;
import com.example.bundle.domain.Book;
import com.example.bundle.repository.AuthorsRepo;
import com.example.bundle.repository.BooksRepo;
import com.example.bundle.repository.impl.AuthorsRepoImpl;
import com.example.bundle.repository.impl.BooksRepoImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 07.04.2026
 * Активити получения топа книг
 */
public class BooksActivity implements Service {
    private final RecordService recordService;
    private final AuthorsRepo authorsRepo;
    private final BooksRepo booksRepo;
    private final Logger logger = ServiceLocator.getLogger();

    public BooksActivity() {
        recordService = ServiceLocator.getRecordService();
        authorsRepo = new AuthorsRepoImpl(recordService);
        this.booksRepo = new BooksRepoImpl(recordService);
    }

    @Action(name = "GetTopBooksActivity", scope = Scope.PUBLIC)
    public String action() {
        logger.info("GetTopBooksActivity");

        Optional<Author> optAuthor = authorsRepo.findByDisplayId("000000000000001");
        List<Book> books = optAuthor.map(Author::getId)
                .map(booksRepo::findTopBooksByAuthorId)
                .orElse(Collections.emptyList());

        books.forEach(book -> optAuthor.ifPresent(book::setAuthor));

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonTopBooks = mapper.writeValueAsString(books);
            logger.info("Top Books: " + jsonTopBooks);
            return jsonTopBooks;
        } catch (JsonProcessingException e) {
            logger.error("Json convert error", e);
            return "[]";
        }
    }
}
