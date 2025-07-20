package com.markguiang.backend.form.service;

import com.markguiang.backend.base.exceptions.FieldMismatchException;
import com.markguiang.backend.form.model.Field;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.repository.FieldRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FieldService {
    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public List<Field> createFieldList(Form form) {
        List<Field> fieldList = form.getFieldList();
        for (Field field: fieldList) {
            field.setForm(form);
        }
        return IterableUtils.toList(this.fieldRepository.saveAll(fieldList));
    }

    @Transactional
    public List<Field> updateFieldList(Form form) {
        List<Field> fieldList = form.getFieldList();
        List<Field> fieldListDb = fieldRepository.findFieldByFormId(form.getFormId());

        if (fieldList.size() < fieldListDb.size()) {
            throw new IllegalStateException("fieldList length should not be less than " + fieldListDb.size());
        }

        Map<Long, Field> fieldMapDb = fieldListDb.stream()
                .collect(Collectors.toMap(Field::getFieldId, Function.identity()));
        for (Field field: fieldList) {
            if (field.getFieldId() == null) {
                fieldListDb.add(field);
                continue;
            }
            Field fieldDb = fieldMapDb.get(field.getFieldId());
            if (!fieldDb.getFieldType().equals(field.getFieldType())) {
                throw new FieldMismatchException("Field Type");
            }
            fieldDb.setName(field.getName());
            fieldDb.setMandatory(field.getMandatory());
            fieldDb.setOrder(field.getOrder());
        }
        return IterableUtils.toList(this.fieldRepository.saveAll(fieldListDb));
    }
}