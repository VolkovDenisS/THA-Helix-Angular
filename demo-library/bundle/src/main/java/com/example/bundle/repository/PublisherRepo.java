package com.example.bundle.repository;

import com.example.bundle.domain.Publisher;

import java.util.Map;
import java.util.Set;

/**
 * @author Created by ZotovES on 09.04.2026
 * Репозиторий издателей
 */
public interface PublisherRepo {
    /**
     * Поиск издателей по списку ид
     *
     * @param ids список ид
     * @return список издателей
     */
    Map<String, Publisher> findByIds(Set<String> ids);
}
