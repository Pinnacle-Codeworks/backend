package com.markguiang.backend.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.auth.dto.user.UserFieldsWithoutPasswordDTO;
import com.markguiang.backend.auth.dto.user.UserIdsDTO;
import com.markguiang.backend.auth.role.Role;

import java.util.List;

public class UserResponseDTO {
    @JsonUnwrapped
    private UserFieldsWithoutPasswordDTO userFieldsWithoutPasswordDTO;
    @JsonUnwrapped
    private UserIdsDTO userIdsDTO;
    private List<Role> roles;

    public UserFieldsWithoutPasswordDTO getUserFieldsWithoutPasswordDTO() {
        return userFieldsWithoutPasswordDTO;
    }

    public void setUserFieldsWithoutPasswordDTO(UserFieldsWithoutPasswordDTO userFieldsWithoutPasswordDTO) {
        this.userFieldsWithoutPasswordDTO = userFieldsWithoutPasswordDTO;
    }

    public UserIdsDTO getUserIdsDTO() {
        return userIdsDTO;
    }

    public void setUserIdsDTO(UserIdsDTO userIdsDTO) {
        this.userIdsDTO = userIdsDTO;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}