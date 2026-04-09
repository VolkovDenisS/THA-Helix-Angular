package com.example.bundle.utils;

import com.bmc.arsys.rx.application.common.ServiceLocator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Created by ZotovES on 09.04.2026
 * Утилитарный класс для работы с JSON
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {}

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            ServiceLocator.getLogger().error("Json convert error", e);
            return "{}";
        }
    }
}
