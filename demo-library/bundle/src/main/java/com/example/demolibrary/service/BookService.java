package com.example.demolibrary.service;

import com.bmc.arsys.rx.services.common.annotation.RxInstanceTransactional;
import com.example.demolibrary.domain.Author;
import com.example.demolibrary.domain.Book;
import com.example.demolibrary.domain.Publisher;
import com.example.demolibrary.repo.AuthorRepository;
import com.example.demolibrary.repo.BookRepository;
import com.example.demolibrary.repo.PublisherRepository;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;

public class BookService {

    private static final int TOP_BOOKS_LIMIT = 2;

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @RxInstanceTransactional(readOnly = true, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public List<Book> getTopTwoByAuthor(String authorDisplayId) {
        return findTopBooksWithNested(authorDisplayId);
    }

    List<Book> findTopBooksWithNested(String authorDisplayId) {
        List<Book> books = bookRepository.findTopByAuthor(authorDisplayId, TOP_BOOKS_LIMIT);
        Author author = authorRepository.findByDisplayId(authorDisplayId).orElse(null);
        List<Publisher> publishers = publisherRepository.findAll();

        for (Book book : books) {
            book.setAuthor(author);
            book.setPublishers(publishers);
        }
        return books;
    }
}
