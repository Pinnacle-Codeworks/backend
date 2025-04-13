package com.markguiang.backend.form.service;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.service.EventService;
import com.markguiang.backend.form.model.Field;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.repository.FormRepository;
import com.markguiang.backend.user.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final FieldService fieldService;
    private final EventService eventService;
    private final UserContext userContext;

    public FormService(FormRepository formRepository, FieldService fieldService, UserContext userContext, EventService eventService) {
        this.formRepository = formRepository;
        this.fieldService = fieldService;
        this.eventService = eventService;
        this.userContext = userContext;
    }

    public Form createFormWithFieldList(Form form) {
        Optional<Event> event = this.eventService.getEvent(form.getEventId());
        if (event.isEmpty() || !event.get().getEventId().equals(form.getEventId())) {
            throw new NoSuchElementException("Event not found");
        }

        form.setCompanyId(this.userContext.getUser().getCompanyId());
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