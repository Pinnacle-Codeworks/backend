package com.markguiang.backend.auth.dto.mapper;

import com.markguiang.backend.auth.dto.response.LoginResponseDTO;
import com.markguiang.backend.auth.dto.response.UserResponseDTO;
import com.markguiang.backend.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserResponseMapper {

    UserResponseDTO userToUserResponseDTO(User user);

    @Mapping(source = "user", target = "userResponseDTO")
    LoginResponseDTO userToLoginResponseDTO(User user, String csrfToken);
}