package com.example.demolibrary.support;

import com.bmc.arsys.rx.services.record.domain.FieldDefinition;
import com.bmc.arsys.rx.services.record.domain.RecordDefinition;
import com.bmc.arsys.rx.services.record.domain.RegularRecordDefinition;
import com.bmc.arsys.rx.services.record.domain.StorageType;
import com.bmc.arsys.rx.standardlib.record.CharacterFieldDefinition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TestRecordDefinitions {

    private TestRecordDefinitions() {
    }

    public static RecordDefinition of(String name, Map<Integer, String> fields) {
        RegularRecordDefinition definition = new RegularRecordDefinition();
        definition.setName(name);
        List<FieldDefinition<? extends StorageType>> fieldDefinitions = new ArrayList<>();
        fields.forEach((id, fieldName) -> fieldDefinitions.add(new NamedField(id, fieldName)));
        definition.setFieldDefinitions(fieldDefinitions);
        return definition;
    }

    public static Map<Integer, String> fields(Object... pairs) {
        Map<Integer, String> result = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            result.put((Integer) pairs[i], (String) pairs[i + 1]);
        }
        return result;
    }

    private static final class NamedField extends CharacterFieldDefinition {
        NamedField(int id, String name) {
            super();
            setId(id);
            setName(name);
        }
    }
}
