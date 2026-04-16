package com.example.bundle.mapper;

/**
 * @author Created by ZotovES on 16.04.2026
 * Интерфейс маппера
 */
public interface Mapper<S, D> {
    D toEntity(S source);
}
