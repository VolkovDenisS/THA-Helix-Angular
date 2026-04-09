package com.example.bundle.repository;

import com.example.bundle.domain.Author;

import java.util.Optional;

/**
 * @author Created by ZotovES on 09.04.2026
 * Репозиторий авторов
 */
public interface AuthorRepo {
    /**
     * Поиск автора по ид отображения
     *
     * @param displayId ид отображения
     * @return автор
     */
    Optional<Author> findByDisplayId(String displayId);
}
