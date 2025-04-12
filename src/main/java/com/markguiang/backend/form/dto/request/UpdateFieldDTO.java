package com.markguiang.backend.form.dto.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.annotation.ValidRequired;
import com.markguiang.backend.form.dto.field.FieldFieldsDTO;
import com.markguiang.backend.form.dto.field.FieldIdsDTO;

public class UpdateFieldDTO {
    @JsonUnwrapped
    @ValidRequired
    private FieldFieldsDTO fieldFieldsDTO;
    @JsonUnwrapped
    @ValidRequired
    private FieldIdsDTO fieldIdsDTO;

    public FieldFieldsDTO getFieldFieldsDTO() {
        return fieldFieldsDTO;
    }

    public void setFieldFieldsDTO(FieldFieldsDTO fieldFieldsDTO) {
        this.fieldFieldsDTO = fieldFieldsDTO;
    }

    public FieldIdsDTO getFieldIdsDTO() {
        return fieldIdsDTO;
    }

    public void setFieldIdsDTO(FieldIdsDTO fieldIdsDTO) {
        this.fieldIdsDTO = fieldIdsDTO;
    }
}