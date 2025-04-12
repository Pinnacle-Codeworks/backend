package com.markguiang.backend.form.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.annotation.ValidRequired;
import com.markguiang.backend.form.dto.form.FormFieldsDTO;
import com.markguiang.backend.form.dto.form.FormIdsDTO;

import java.util.List;

public class UpdateFormDTO {
    @JsonUnwrapped
    @ValidRequired
    private FormFieldsDTO formFieldsDTO;
    @JsonUnwrapped
    @ValidRequired
    private FormIdsDTO formIdsDTO;
    @JsonProperty("fieldList")
    private List<UpdateFieldDTO> updateFieldDTOList;

    public List<UpdateFieldDTO> getUpdateFieldDTOList() {
        return updateFieldDTOList;
    }

    public void setUpdateFieldDTOList(List<UpdateFieldDTO> updateFieldDTOList) {
        this.updateFieldDTOList = updateFieldDTOList;
    }

    public FormIdsDTO getFormIdsDTO() {
        return formIdsDTO;
    }

    public void setFormIdsDTO(FormIdsDTO formIdsDTO) {
        this.formIdsDTO = formIdsDTO;
    }

    public FormFieldsDTO getFormFieldsDTO() {
        return formFieldsDTO;
    }

    public void setFormFieldsDTO(FormFieldsDTO formFieldsDTO) {
        this.formFieldsDTO = formFieldsDTO;
    }
}