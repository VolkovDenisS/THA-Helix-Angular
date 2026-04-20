package com.example.demolibrary.repo;

import com.bmc.arsys.rx.services.common.DataPage;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.QueryPredicate;
import com.bmc.arsys.rx.services.record.RecordService;
import com.bmc.arsys.rx.services.record.domain.FieldDefinition;
import com.bmc.arsys.rx.services.record.domain.RecordDefinition;
import com.bmc.arsys.rx.services.record.domain.StorageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Базовый класс для любого репозитория
 *
 * @param <T> сущность в хранилище данных
 */
public abstract class AbstractRecordRepository<T> {

    private static final int PAGE_START = 0;

    protected final RecordService recordService;

    protected AbstractRecordRepository(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * Текстовый идентификатор record definition
     *
     * @return текстовый идентификатор
     */
    protected abstract String getRecordDefinitionName();

    /**
     * Определяет реализацию маппинга record definition -> POJO
     *
     * @param row строчка из record definition
     * @return POJO объект описывающий запись в БД
     */
    protected abstract T mapToDto(Map<String, Object> row);

    /**
     * Получение записей из хранилища
     *
     * @param fieldFilters применяемые фильтры
     * @param pageSize     размерность страницы
     * @return record
     */
    protected List<T> fetchRecords(Map<Integer, String> fieldFilters, int pageSize) {
        String recordDefinitionName = getRecordDefinitionName();
        RecordDefinition definition = recordService.getRecordDefinition(recordDefinitionName);
        DataPageQueryParameters parameters = buildQueryParameters(
                recordDefinitionName, definition, fieldFilters, pageSize);

        DataPage page = recordService.getRecordInstancesByIdDataPage(parameters);
        List<?> raw = page.getData();
        if (raw == null || raw.isEmpty()) {
            return Collections.emptyList();
        }
        return raw.stream()
                .map(row -> rowToNamedMap(row, definition))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Получение первого элемента
     *
     * @param fieldFilters применяемые фильтры
     * @return если список пустой, то Optional.empty, в любом другом случае первый элемент
     * @see AbstractRecordRepository#fetchRecords
     */
    protected Optional<T> fetchFirst(Map<Integer, String> fieldFilters) {
        List<T> result = fetchRecords(fieldFilters, 1);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    /**
     * Конвертирует переданный объект в строку путем вызова у объекта метода toString
     *
     * @param raw объект для конвертации
     * @return null если переданный объект null, в других случае текстовая репрезентация объекта
     */
    protected String asString(Object raw) {
        if (raw == null) {
            return null;
        }
        return raw.toString();
    }


    private DataPageQueryParameters buildQueryParameters(String recordDefinitionName,
                                                         RecordDefinition definition,
                                                         Map<Integer, String> fieldFilters,
                                                         int pageSize) {
        List<String> propertySelections = definition.getFieldDefinitions().stream()
                .map(f -> String.valueOf(f.getId()))
                .collect(Collectors.toList());

        Map<String, QueryPredicate> predicates = new HashMap<>();
        predicates.put(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
                new QueryPredicate(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME,
                        recordDefinitionName));
        fieldFilters.forEach((fieldId, value) -> {
            String key = String.valueOf(fieldId);
            predicates.put(key, new QueryPredicate(key, value));
        });

        return new DataPageQueryParameters(
                pageSize, PAGE_START, propertySelections, null, predicates);
    }

    private Map<String, Object> rowToNamedMap(Object row, RecordDefinition definition) {
        Map<?, ?> rawRow = row instanceof Map ? (Map<?, ?>) row : Collections.emptyMap();
        Map<String, Object> result = new LinkedHashMap<>();
        for (FieldDefinition<? extends StorageType> field : definition.getFieldDefinitions()) {
            result.put(field.getName(), valueFor(rawRow, field));
        }
        return result;
    }

    private Object valueFor(Map<?, ?> rawRow, FieldDefinition<? extends StorageType> field) {
        Object value = rawRow.get(field.getId());
        if (value == null) {
            value = rawRow.get(String.valueOf(field.getId()));
        }
        if (value == null) {
            value = rawRow.get(field.getName());
        }
        return value;
    }
}
