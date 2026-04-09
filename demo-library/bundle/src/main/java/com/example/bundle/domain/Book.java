package com.example.bundle.domain;

import java.util.Objects;

/**
 * @author Created by ZotovES on 09.04.2026
 * Книга
 */
public class Book {
    /**
     * Уникальный идентификатор
     */
    private String id;
    /**
     * Ид отображения
     */
    private String displayId;
    /**
     * Наименование книги
     */
    private String name;
    /**
     * Описание
     */
    private String description;
    /**
     * Цена
     */
    private String price;
    /**
     * Автор
     */
    private Author author;
    /**
     * Издатель
     */
    private Publisher publisher;

    public Book() {
    }

    public Book(String id, String displayId, String name, String description, String price, Author author, Publisher publisher) {
        this.id = id;
        this.displayId = displayId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Book book = (Book) object;
        return Objects.equals(id, book.id) && Objects.equals(displayId, book.displayId) &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(displayId);
        result = 31 * result + Objects.hashCode(name);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", displayId='" + displayId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
