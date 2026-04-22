package com.example.demolibrary.domain;

/**
 * Маркерный интерфейс для всех сущностей с которыми
 * работает приложение. Необходим во избежание использования
 * AbstractRepository с неизвестными сущностями
 * @see com.example.demolibrary.repo.AbstractRecordRepository
 * @see com.example.demolibrary.repo.AuthorRepository
 * @see com.example.demolibrary.repo.PublisherRepository
 * @see com.example.demolibrary.repo.BookRepository
 */
public interface BusinessEntity {
}
