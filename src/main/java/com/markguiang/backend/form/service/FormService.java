package com.markguiang.backend.form.service;

import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.domain.ports.EventRepository;
import com.markguiang.backend.event.domain.ports.EventService;
import com.markguiang.backend.form.model.Field;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.repository.FormRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class FormService {
  private final FormRepository formRepository;
  private final FieldService fieldService;
  private final EventService eventService;

  public FormService(
      FormRepository formRepository, FieldService fieldService, EventRepository eventRepository) {
    this.formRepository = formRepository;
    this.fieldService = fieldService;
    this.eventService = new EventService(eventRepository);
  }

  // TODO: broken
  public Form createFormWithFieldList(Form form) {
    Event event = this.eventService.getEvent(UUID.fromString(form.getEventId().toString()));
    if (!event.getId().equals(form.getEventId())) {
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
