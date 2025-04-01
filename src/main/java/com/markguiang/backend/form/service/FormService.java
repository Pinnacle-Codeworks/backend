package com.markguiang.backend.form.service;

import com.markguiang.backend.exceptions.FieldMismatchException;
import com.markguiang.backend.exceptions.UniqueConstraintViolationException;
import com.markguiang.backend.form.model.Field;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.repository.FieldRepository;
import com.markguiang.backend.form.repository.FormRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final FieldRepository fieldRepository;

    public FormService(FormRepository formRepository, FieldRepository fieldRepository) {
        this.formRepository = formRepository;
        this.fieldRepository = fieldRepository;
    }

    public Form createForm(Form form) throws UniqueConstraintViolationException {
        try {
            Form form1 = formRepository.save(form);

            List<Field> fieldList = form.getFieldList();
            for (Field field: fieldList) {
                field.setFormId(form.getFormId());
            }
            fieldRepository.saveAll(fieldList);
            return form1;
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueConstraintViolationException(form.getName());
        }
    }

    public Form editFormFields(Form form) throws NoSuchElementException, FieldMismatchException {
        Optional<Form> formDbOpt = formRepository.findById(form.getFormId());
        if (formDbOpt.isEmpty()) {
            throw new NoSuchElementException();
        }
        Form formDb = formDbOpt.get();

        List<Field> fieldListDb = formDb.getFieldList();
        List<Field> fieldList = form.getFieldList();

        if (fieldList.size() < fieldListDb.size()) {
           throw new IllegalStateException("fieldList length should not be less than " + fieldListDb.size());
        }

        Map<Long, Field> fieldMapDb = fieldListDb.stream()
                .collect(Collectors.toMap(Field::getFieldId, Function.identity()));
        for (Field field: fieldList) {
            Field fieldDb = fieldMapDb.get(field.getFieldId());
            if (!fieldDb.getFieldType().equals(field.getFieldType())) {
                throw new FieldMismatchException("Field Type");
            }
            fieldDb.setName(field.getName());
            fieldDb.setMandatory(field.getMandatory());
            fieldDb.setOrder(field.getOrder());
            fieldDb.setDeleted(field.getDeleted());
        }
        fieldRepository.saveAll(fieldListDb);
        form.setFieldList(fieldListDb);
        formRepository.save(form);
        return form;
    }
}
