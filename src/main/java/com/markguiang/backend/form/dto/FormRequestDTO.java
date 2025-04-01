package com.markguiang.backend.form.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.markguiang.backend.form.model.Field;
import com.markguiang.backend.form.model.Form;

import java.util.ArrayList;
import java.util.List;

public class FormRequestDTO {
    private Long formId;
    private Long companyId;
    private Long eventId;
    private String name;
    private String description;
    @JsonAlias("fieldList")
    private List<FieldRequestDTO> fieldRequestDTOList;

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FieldRequestDTO> getFieldRequestDTOList() {
        return fieldRequestDTOList;
    }

    public void setFieldRequestDTOList(List<FieldRequestDTO> fieldRequestDTOList) {
        this.fieldRequestDTOList = fieldRequestDTOList;
    }

    public static Form toForm(FormRequestDTO formDTO, Boolean withIds) {
        Form form = new Form();
        if (withIds) {
            form.setFormId(formDTO.getFormId());
        }
        form.setCompanyId(formDTO.getCompanyId());
        form.setEventId(formDTO.getEventId());
        form.setName(formDTO.getName());
        form.setDescription(formDTO.getDescription());
        List<Field> fieldList = new ArrayList<>();
        for (FieldRequestDTO fieldRequestDTO: formDTO.getFieldRequestDTOList()) {
            Field field = FieldRequestDTO.toField(fieldRequestDTO);
            if (withIds) {
                field.setFormId(form.getFormId());
                field.setFieldId(fieldRequestDTO.getFieldId());
            }
            fieldList.add(field);
        }
        form.setFieldList(fieldList);
        return form;
    }
}
