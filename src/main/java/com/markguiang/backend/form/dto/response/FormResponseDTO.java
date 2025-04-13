package com.markguiang.backend.form.dto.response;

import com.markguiang.backend.form.enum_.FieldType;

import java.util.List;

public class FormResponseDTO {
    private Long formId;
    private Long eventId;
    private String name;
    private String description;
    private List<FieldResponseDTO> fieldResponseDTOList;

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
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

    public List<FieldResponseDTO> getFieldResponseDTOList() {
        return fieldResponseDTOList;
    }

    public void setFieldResponseDTOList(List<FieldResponseDTO> fieldResponseDTOList) {
        this.fieldResponseDTOList = fieldResponseDTOList;
    }

    public static class FieldResponseDTO {
        private Long fieldId;
        private String name;
        private FieldType fieldType;
        private Boolean mandatory;
        private Integer order;

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public Boolean getMandatory() {
            return mandatory;
        }

        public void setMandatory(Boolean mandatory) {
            this.mandatory = mandatory;
        }

        public FieldType getFieldType() {
            return fieldType;
        }

        public void setFieldType(FieldType fieldType) {
            this.fieldType = fieldType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getFieldId() {
            return fieldId;
        }

        public void setFieldId(Long fieldId) {
            this.fieldId = fieldId;
        }
    }
}