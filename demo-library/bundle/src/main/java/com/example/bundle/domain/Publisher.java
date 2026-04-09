package com.example.bundle.domain;

import java.util.Objects;

/**
 * @author Created by ZotovES on 09.04.2026
 * Издатель
 *
 */
public class Publisher {
    /**
     * Уникальный идентификатор
     */
    private String id;
    /**
     * Ид отображения
     */
    private String displayId;
    /**
     * Наименование
     */
    private String name;

    public Publisher() {
    }

    public Publisher(String id) {
        this.id = id;
    }

    public Publisher(String id, String displayId, String name) {
        this.id = id;
        this.displayId = displayId;
        this.name = name;
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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Publisher publisher = (Publisher) object;
        return Objects.equals(id, publisher.id) && Objects.equals(displayId, publisher.displayId) &&
                Objects.equals(name, publisher.name);
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
        return "Publisher{" +
                "id='" + id + '\'' +
                ", displayId='" + displayId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
