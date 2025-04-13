package com.markguiang.backend.auth.dto.mapper;

import com.markguiang.backend.auth.dto.request.RegisterUserDTO;
import com.markguiang.backend.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface UserRequestMapper {
    User registerUserDTOtoUser(RegisterUserDTO registerUserDTO);
}