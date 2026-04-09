package com.example.bundle.repository;

import com.example.bundle.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 09.04.2026
 * Репозиторий книг
 */
public interface BooksRepo {
    /**
     * Поиск списка с топом книг по ид автора
     *
     * @param authorId ид автора
     * @return список книг
     */
    List<Book> findTopBooksByAuthorId(String authorId);
}
