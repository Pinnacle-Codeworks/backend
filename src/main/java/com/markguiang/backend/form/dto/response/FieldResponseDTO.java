package com.markguiang.backend.form.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.form.dto.field.FieldFieldsDTO;
import com.markguiang.backend.form.dto.field.FieldIdsDTO;

public class FieldResponseDTO {
    @JsonUnwrapped
    private FieldFieldsDTO fieldFieldsDTO;
    @JsonUnwrapped
    private FieldIdsDTO fieldIdsDTO;

    public FieldIdsDTO getFieldIdsDTO() {
        return fieldIdsDTO;
    }

    public void setFieldIdsDTO(FieldIdsDTO fieldIdsDTO) {
        this.fieldIdsDTO = fieldIdsDTO;
    }

    public FieldFieldsDTO getFieldFieldsDTO() {
        return fieldFieldsDTO;
    }

    public void setFieldFieldsDTO(FieldFieldsDTO fieldFieldsDTO) {
        this.fieldFieldsDTO = fieldFieldsDTO;
    }
}