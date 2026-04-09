package com.example.bundle.domain;

import java.util.Objects;

/**
 * @author Created by ZotovES on 09.04.2026
 * Модель автора
 */
public class Author {
    /**
     * Уникальный идентификатор
     */
    private String id;

    /**
     * Ид отображения
     */
    private String displayId;
    /**
     * Полное имя автора
     */
    private String fullName;
    /**
     * Адрес
     */
    private String from;
    /**
     * Почта
     */
    private String email;

    public Author() {}

    public Author(String id, String displayId, String fullName, String from, String email) {
        this.id = id;
        this.displayId = displayId;
        this.fullName = fullName;
        this.from = from;
        this.email = email;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Author author = (Author) object;
        return Objects.equals(id, author.id) && Objects.equals(displayId, author.displayId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(displayId);
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", displayId='" + displayId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", from='" + from + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
