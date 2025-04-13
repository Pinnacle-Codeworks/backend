package com.markguiang.backend.form.dto.mapper;

import com.markguiang.backend.form.dto.response.FormAnswersResponseDTO;
import com.markguiang.backend.form.dto.response.FormResponseDTO;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.model.FormAnswers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FormResponseMapper {
    @Mapping(source = "fieldAnswerList", target = "fieldAnswerResponseDTOList")
    FormAnswersResponseDTO formAnswersToFormAnswersResponseDTO(FormAnswers formAnswers);
    @Mapping(source = "fieldList", target = "fieldResponseDTOList")
    FormResponseDTO formToFormResponseDTO(Form form);
}