package com.markguiang.backend.form.mapper;

import com.markguiang.backend.form.dto.request.AnswerFormDTO;
import com.markguiang.backend.form.dto.request.CreateFormDTO;
import com.markguiang.backend.form.dto.request.UpdateFormDTO;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.model.FormAnswers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface FormRequestMapper {
    @Mapping(source = "fieldAnswerDTOList", target = "fieldAnswerList")
    FormAnswers answerFormDTOtoFormAnswers(AnswerFormDTO answerFormDTO);
    @Mapping(source = "createFormFieldDTOList", target = "fieldList")
    Form createFormDTOtoForm(CreateFormDTO createFormDTO);
    @Mapping(source = "updateFormFieldDTOList", target = "fieldList")
    Form updateFormDTOtoForm(UpdateFormDTO updateFormDTO);
}