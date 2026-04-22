package com.example.demolibrary.support;

import com.bmc.arsys.rx.services.RequestMessage;
import com.bmc.arsys.rx.services.association.domain.AssociationOperation;
import com.bmc.arsys.rx.services.common.DataPage;
import com.bmc.arsys.rx.services.common.DataPageQueryParameters;
import com.bmc.arsys.rx.services.common.QueryPredicate;
import com.bmc.arsys.rx.services.common.domain.PermissionObjectType;
import com.bmc.arsys.rx.services.record.RecordService;
import com.bmc.arsys.rx.services.record.domain.Attachment;
import com.bmc.arsys.rx.services.record.domain.BulkRecordInstance;
import com.bmc.arsys.rx.services.record.domain.RecordDefinition;
import com.bmc.arsys.rx.services.record.domain.RecordInstance;
import com.bmc.arsys.rx.services.record.repository.RecordInstanceBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryRecordService implements RecordService {

    private final Map<String, RecordDefinition> definitionsByName = new HashMap<>();
    private final Map<String, List<Map<Integer, Object>>> rowsByDefinition = new HashMap<>();

    public InMemoryRecordService registerDefinition(RecordDefinition definition) {
        definitionsByName.put(definition.getName(), definition);
        rowsByDefinition.putIfAbsent(definition.getName(), new ArrayList<>());
        return this;
    }

    public InMemoryRecordService addRow(String recordDefinitionName, Map<Integer, Object> row) {
        rowsByDefinition
                .computeIfAbsent(recordDefinitionName, k -> new ArrayList<>())
                .add(new HashMap<>(row));
        return this;
    }

    @Override
    public RecordDefinition getRecordDefinition(String name) {
        RecordDefinition def = definitionsByName.get(name);
        if (def == null) {
            throw new IllegalStateException("Record definition not registered: " + name);
        }
        return def;
    }

    @Override
    public DataPage getRecordInstancesByIdDataPage(DataPageQueryParameters parameters) {
        Map<String, QueryPredicate> predicates = parameters.getQueryPredicatesByName();
        QueryPredicate recordDefPredicate = predicates.get(RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME);
        if (recordDefPredicate == null) {
            throw new IllegalStateException("Missing 'recorddefinition' predicate");
        }
        String recordDefinitionName = recordDefPredicate.getRightOperand();
        List<Map<Integer, Object>> rows = rowsByDefinition
                .getOrDefault(recordDefinitionName, Collections.emptyList());

        List<Map<Integer, Object>> filtered = rows.stream()
                .filter(row -> matchesAllPredicates(row, predicates))
                .collect(Collectors.toList());

        int startIndex = parameters.getStartIndex() == null ? 0 : parameters.getStartIndex();
        int pageSize = parameters.getPageSize() == null ? filtered.size() : parameters.getPageSize();
        int to = Math.min(startIndex + pageSize, filtered.size());
        List<Map<Integer, Object>> page = startIndex >= filtered.size()
                ? Collections.emptyList()
                : new ArrayList<>(filtered.subList(startIndex, to));

        return new DataPage(filtered.size(), page);
    }

    private boolean matchesAllPredicates(Map<Integer, Object> row,
                                         Map<String, QueryPredicate> predicates) {
        for (Map.Entry<String, QueryPredicate> entry : predicates.entrySet()) {
            String key = entry.getKey();
            if (RecordService.RECORD_DEFINITION_QUERY_PARAMETER_NAME.equals(key)) {
                continue;
            }
            int fieldId;
            try {
                fieldId = Integer.parseInt(key);
            } catch (NumberFormatException e) {
                continue;
            }
            Object actual = row.get(fieldId);
            String expected = entry.getValue().getRightOperand();
            if (actual == null || !actual.toString().equals(expected)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public DataPage getRecordInstancesByIdDataPage(DataPageQueryParameters parameters, boolean b) {
        return getRecordInstancesByIdDataPage(parameters);
    }

    // --- everything below is unused by the repos under test ---

    @Override
    public void registerRecordInstanceBuilder(RecordInstanceBuilder b) {
        throw unsupported();
    }

    @Override
    public void createRecordDefinition(RecordDefinition d) {
        throw unsupported();
    }

    @Override
    public void updateRecordDefinition(RecordDefinition d) {
        throw unsupported();
    }

    @Override
    public void updateRecordDefinition(RecordDefinition d, boolean b) {
        throw unsupported();
    }

    @Override
    public void convertFieldDataType(String a, int b, String c) {
        throw unsupported();
    }

    @Override
    public RecordInstance getRecordInstance(String a, String b) {
        throw unsupported();
    }

    @Override
    public void createRecordInstance(RecordInstance r) {
        throw unsupported();
    }

    @Override
    public List<String> createRecordInstances(List<RecordInstance> r) {
        throw unsupported();
    }

    @Override
    public List<String> createRecordInstancesWithAttachments(List<BulkRecordInstance> r) {
        throw unsupported();
    }

    @Override
    public void createRecordInstance(RecordInstance r, Set<Attachment> a, Set<AssociationOperation> o) {
        throw unsupported();
    }

    @Override
    public void updateRecordInstance(RecordInstance r) {
        throw unsupported();
    }

    @Override
    public void updateRecordInstances(String a, List<RecordInstance> b) {
        throw unsupported();
    }

    @Override
    public void updateRecordInstancesWithAttachments(String a, List<BulkRecordInstance> b) {
        throw unsupported();
    }

    @Override
    public void updateRecordInstance(RecordInstance r, boolean b) {
        throw unsupported();
    }

    @Override
    public void updateRecordInstance(RecordInstance r, Set<Attachment> a, Set<AssociationOperation> o, boolean b) {
        throw unsupported();
    }

    @Override
    public DataPage getRecordInstancesDataPage(DataPageQueryParameters p, List<QueryPredicate> q) {
        throw unsupported();
    }

    @Override
    public List<RecordInstance> getRecordInstances(String a, List<String> b, List<Integer> c) {
        throw unsupported();
    }

    @Override
    public void renameRecordDefinition(String a, String b) {
        throw unsupported();
    }

    @Override
    public void deleteRecordDefinition(String a) {
        throw unsupported();
    }

    @Override
    public void deleteRecordDefinitions(List<String> a) {
        throw unsupported();
    }

    @Override
    public List<String> getRecordDefinitionNames() {
        throw unsupported();
    }

    @Override
    public void deleteRecordInstance(String a, String b) {
        throw unsupported();
    }

    @Override
    public void deleteRecordInstances(String a, List<String> b) {
        throw unsupported();
    }

    @Override
    public DataPage getRecordDefinitionDataPage(DataPageQueryParameters p) {
        throw unsupported();
    }

    @Override
    public DataPage getRecordDefinitionInheritanceDataPage(DataPageQueryParameters p) {
        throw unsupported();
    }

    @Override
    public RecordInstance buildRecordInstance(String a) {
        throw unsupported();
    }

    @Override
    public DataPage getSearchDataPage(DataPageQueryParameters p) {
        throw unsupported();
    }

    @Override
    public void persistAttachment(Attachment a) {
        throw unsupported();
    }

    @Override
    public Attachment getAttachment(String a, String b, String c) {
        throw unsupported();
    }

    @Override
    public void deleteAttachment(String a, String b, String c) {
        throw unsupported();
    }

    @Override
    public void importRecordInstance(RecordInstance r, String a) {
        throw unsupported();
    }

    @Override
    public void importRecordInstanceWithImportOptions(RecordInstance r, String a, List<Integer> b) {
        throw unsupported();
    }

    @Override
    public String getRecordDefinitionId(String a) {
        throw unsupported();
    }

    @Override
    public String getRecordDefinitionGuid(String a) {
        throw unsupported();
    }

    @Override
    public void importRecordDefinition(RecordDefinition d) {
        throw unsupported();
    }

    @Override
    public RecordDefinition buildRecordDefinition(String a) {
        throw unsupported();
    }

    @Override
    public Map<String, Set<RequestMessage>> updateRecordInstances(RecordInstance r, Set<String> s,
                                                                  Set<Attachment> a, Set<AssociationOperation> o,
                                                                  boolean b) {
        throw unsupported();
    }

    @Override
    public Set<RequestMessage> updateRecordInstanceForBulkEdit(RecordInstance r, String a,
                                                               Set<Attachment> att, Set<AssociationOperation> o,
                                                               boolean b) {
        throw unsupported();
    }

    @Override
    public void validateConfidentialDataAccess(String a) {
        throw unsupported();
    }

    @Override
    public void validateBundleAccess(String a) {
        throw unsupported();
    }

    @Override
    public Map<PermissionObjectType, List<String>> getSecurityLabel(String a, String b, String c) {
        throw unsupported();
    }

    @Override
    public void setSecurityLabel(RecordInstance r, String a, List<String> b,
                                 List<String> c, List<String> d,
                                 List<String> e, Boolean f, Boolean g) {
        throw unsupported();
    }

    @Override
    public void removeSecurityLabel(RecordInstance r, String a, List<String> b,
                                    List<String> c, List<String> d,
                                    List<String> e, Boolean f, Boolean g) {
        throw unsupported();
    }

    @Override
    public void securePasswordFields(RecordInstance r) {
        throw unsupported();
    }

    @Override
    public void securePasswordFields(DataPage p, String a) {
        throw unsupported();
    }

    @Override
    public void copyRecordDefinition(String a, String b) {
        throw unsupported();
    }

    @Override
    public RecordInstance createRecordInstance(String a, Map<String, Object> b, Map<String, String> c,
                                               Map<String, String> d, Map<String, String> e, Map<String, String> f) {
        throw unsupported();
    }

    @Override
    public boolean isCustomRecordDefinition(RecordDefinition d) {
        throw unsupported();
    }

    @Override
    public Integer getRecordInstanceCount(String a, Map<String, QueryPredicate> p) {
        throw unsupported();
    }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("not needed for tests");
    }
}
