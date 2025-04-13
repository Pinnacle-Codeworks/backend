package com.markguiang.backend.form.mapper;

import com.markguiang.backend.form.dto.request.UpdateFieldDTO;
import com.markguiang.backend.form.dto.response.FieldResponseDTO;
import com.markguiang.backend.form.model.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    @Mapping(source = ".", target = "fieldIdsDTO")
    @Mapping(source = ".", target = "fieldFieldsDTO")
    FieldResponseDTO fieldToFieldResponseDTO(Field field);

    @Mapping(source = "fieldIdsDTO", target = ".")
    @Mapping(source = "fieldFieldsDTO", target = ".")
    Field updateFieldDTOtoField(UpdateFieldDTO updateFieldDTO);
}