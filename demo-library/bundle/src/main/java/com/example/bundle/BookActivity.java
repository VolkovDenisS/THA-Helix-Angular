package com.example.bundle;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.bmc.arsys.rx.services.action.domain.Action;
import com.bmc.arsys.rx.services.common.Logger;
import com.bmc.arsys.rx.services.common.Service;
import com.bmc.arsys.rx.services.common.domain.Scope;
import com.bmc.arsys.rx.services.record.RecordService;
import com.example.bundle.domain.Author;
import com.example.bundle.domain.Book;
import com.example.bundle.domain.Publisher;
import com.example.bundle.repository.AuthorRepo;
import com.example.bundle.repository.BookRepo;
import com.example.bundle.repository.PublisherRepo;
import com.example.bundle.repository.impl.AuthorRepoImpl;
import com.example.bundle.repository.impl.BookRepoImpl;
import com.example.bundle.repository.impl.PublisherRepoImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 07.04.2026
 * Активити получения топа книг
 */
public class BookActivity implements Service {
    private final RecordService recordService;
    private final AuthorRepo authorsRepo;
    private final BookRepo booksRepo;
    private final PublisherRepo publisherRepo;
    private final Logger logger = ServiceLocator.getLogger();

    public BookActivity() {
        recordService = ServiceLocator.getRecordService();
        authorsRepo = new AuthorRepoImpl(recordService);
        this.booksRepo = new BookRepoImpl(recordService);
        this.publisherRepo = new PublisherRepoImpl(recordService);
    }

    @Action(name = "GetTopBooksActivity", scope = Scope.PUBLIC)
    public String action() {
        logger.info("GetTopBooksActivity");
        Optional<Author> optAuthor = authorsRepo.findByDisplayId("000000000000001");

        List<Book> books = optAuthor.map(Author::getId)
                .map(booksRepo::findTopBooksByAuthorId)
                .orElse(Collections.emptyList());
        Set<String> publisherIds = books.stream()
                .map(Book::getPublisher)
                .map(Publisher::getId)
                .collect(Collectors.toSet());
        Map<String, Publisher> publisherMap = publisherRepo.findByIds(publisherIds);

        books.forEach(book -> {
            optAuthor.ifPresent(book::setAuthor);
            Optional.ofNullable(publisherMap.get(book.getPublisher().getId()))
                    .ifPresent(book::setPublisher);
        });

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
