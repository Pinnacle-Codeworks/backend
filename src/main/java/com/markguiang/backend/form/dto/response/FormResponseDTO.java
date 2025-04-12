package com.markguiang.backend.form.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.form.dto.form.FormFieldsDTO;
import com.markguiang.backend.form.dto.form.FormIdsDTO;

import java.util.List;

public class FormResponseDTO {
    @JsonUnwrapped
    private FormFieldsDTO formFieldsDTO;
    @JsonUnwrapped
    private FormIdsDTO formIdsDTO;
    @JsonProperty("fieldList")
    private List<FieldResponseDTO> fieldResponseDTOList;

    public FormFieldsDTO getFormFieldsDTO() {
        return formFieldsDTO;
    }

    public void setFormFieldsDTO(FormFieldsDTO formFieldsDTO) {
        this.formFieldsDTO = formFieldsDTO;
    }

    public FormIdsDTO getFormIdsDTO() {
        return formIdsDTO;
    }

    public void setFormIdsDTO(FormIdsDTO formIdsDTO) {
        this.formIdsDTO = formIdsDTO;
    }

    public List<FieldResponseDTO> getFieldResponseDTOList() {
        return fieldResponseDTOList;
    }

    public void setFieldResponseDTOList(List<FieldResponseDTO> fieldResponseDTOList) {
        this.fieldResponseDTOList = fieldResponseDTOList;
    }
}