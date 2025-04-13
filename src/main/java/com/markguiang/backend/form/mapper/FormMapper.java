package com.markguiang.backend.form.mapper;

import com.markguiang.backend.form.dto.request.CreateFormDTO;
import com.markguiang.backend.form.dto.request.UpdateFormDTO;
import com.markguiang.backend.form.dto.response.FormResponseDTO;
import com.markguiang.backend.form.model.Form;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FieldMapper.class)
public interface FormMapper {
    @Mapping(source = "formFieldsDTO", target = ".")
    @Mapping(source = "fieldFieldsDTOList", target = "fieldList")
    Form createFormDTOtoForm(CreateFormDTO createFormDTO);

    @Mapping(source = "formFieldsDTO", target = ".")
    @Mapping(source = "formIdsDTO", target = ".")
    @Mapping(source = "updateFieldDTOList", target = "fieldList")
    Form updateFormDTOtoForm(UpdateFormDTO updateFormDTO);

    @Mapping(source = ".", target = "formFieldsDTO")
    @Mapping(source = ".", target = "formIdsDTO")
    @Mapping(source = "fieldList", target = "fieldResponseDTOList")
    FormResponseDTO formToFormResponseDTO(Form form);


}