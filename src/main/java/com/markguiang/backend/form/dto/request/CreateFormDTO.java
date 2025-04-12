package com.markguiang.backend.form.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.annotation.ValidRequired;
import com.markguiang.backend.form.dto.field.FieldFieldsDTO;
import com.markguiang.backend.form.dto.form.FormFieldsDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateFormDTO {
    @JsonUnwrapped
    @ValidRequired
    private FormFieldsDTO formFieldsDTO;
    @JsonProperty("fieldList")
    @ValidRequired
    private List<FieldFieldsDTO> fieldFieldsDTOList;
    @NotNull
    private Long eventId;

    public FormFieldsDTO getFormFieldsDTO() {
        return formFieldsDTO;
    }

    public void setFormFieldsDTO(FormFieldsDTO formFieldsDTO) {
        this.formFieldsDTO = formFieldsDTO;
    }

    public List<FieldFieldsDTO> getFieldFieldsDTOList() {
        return fieldFieldsDTOList;
    }

    public void setFieldFieldsDTOList(List<FieldFieldsDTO> fieldFieldsDTOList) {
        this.fieldFieldsDTOList = fieldFieldsDTOList;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}