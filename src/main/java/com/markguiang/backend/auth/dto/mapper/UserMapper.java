package com.markguiang.backend.auth.dto.mapper;

import com.markguiang.backend.auth.dto.request.RegisterUserDTO;
import com.markguiang.backend.auth.dto.response.LoginResponseDTO;
import com.markguiang.backend.auth.dto.response.UserResponseDTO;
import com.markguiang.backend.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userIdsDTO", target = ".")
    @Mapping(source = "userFieldsWithoutPasswordDTO", target = ".")
    User registerUserDTOtoUser(RegisterUserDTO registerUserDTO);

    @Mapping(source = ".", target = "userIdsDTO")
    @Mapping(source = ".", target = "userFieldsWithoutPasswordDTO")
    UserResponseDTO userToUserResponseDTO(User user);

    @Mapping(source = "user", target = "userResponseDTO")
    LoginResponseDTO userToLoginResponseDTO(User user, String csrfToken);
}