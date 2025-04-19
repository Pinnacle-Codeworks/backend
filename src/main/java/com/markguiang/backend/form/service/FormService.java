package com.markguiang.backend.form.service;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.service.EventService;
import com.markguiang.backend.form.model.Field;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.repository.FormRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final FieldService fieldService;
    private final EventService eventService;

    public FormService(
            FormRepository formRepository, FieldService fieldService, EventService eventService) {
        this.formRepository = formRepository;
        this.fieldService = fieldService;
        this.eventService = eventService;
    }

    public Form createFormWithFieldList(Form form) {
        Optional<Event> event = this.eventService.getEvent(form.getEventId());
        if (event.isEmpty() || !event.get().getEventId().equals(form.getEventId())) {
            throw new NoSuchElementException("Event not found");
        }

        this.formRepository.save(form);

        List<Field> fieldList = this.fieldService.createFieldList(form);
        form.setFieldList(fieldList);

        return form;
    }

    public Form updateFormWithFields(Form form) {
        Optional<Form> formDb = this.formRepository.findById(form.getFormId());
        if (formDb.isEmpty()) {
            throw new NoSuchElementException("Form not found");
        }
        List<Field> fieldList = this.fieldService.updateFieldList(form);
        form.setFieldList(fieldList);
        this.formRepository.save(form);
        return form;
    }
}
