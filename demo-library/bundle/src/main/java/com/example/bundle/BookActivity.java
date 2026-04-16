package com.example.bundle;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.bmc.arsys.rx.services.action.domain.Action;
import com.bmc.arsys.rx.services.common.Logger;
import com.bmc.arsys.rx.services.common.Service;
import com.bmc.arsys.rx.services.common.annotation.RxInstanceTransactional;
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
import com.example.bundle.utils.JsonUtils;
import org.springframework.transaction.annotation.Isolation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 07.04.2026
 * Активити получения топа книг
 */
public class BookActivity implements Service {
    private final AuthorRepo authorsRepo;
    private final BookRepo booksRepo;
    private final PublisherRepo publisherRepo;
    private final Logger logger = ServiceLocator.getLogger();

    public BookActivity() {
        RecordService recordService = ServiceLocator.getRecordService();
        this.authorsRepo = new AuthorRepoImpl(recordService);
        this.booksRepo = new BookRepoImpl(recordService);
        this.publisherRepo = new PublisherRepoImpl(recordService);
    }

    @Action(name = "GetTopBooksActivity", scope = Scope.PUBLIC)
    @RxInstanceTransactional(readOnly = true, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
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

        String jsonTopBooks = JsonUtils.toJson(books);
        logger.info("Top Books: " + jsonTopBooks);

        return jsonTopBooks;
    }
}
