package com.markguiang.backend.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.markguiang.backend.annotation.ValidRequired;
import com.markguiang.backend.auth.dto.user.UserFieldsWithoutPasswordDTO;
import com.markguiang.backend.auth.dto.user.UserIdsDTO;
import jakarta.validation.constraints.NotNull;

public class RegisterUserDTO {
    @JsonUnwrapped
    @ValidRequired
    private UserFieldsWithoutPasswordDTO userFieldsWithoutPasswordDTO;
    @JsonUnwrapped
    @ValidRequired
    private UserIdsDTO userIdsDTO;
    @NotNull
    private String password;

    public UserIdsDTO getUserIdsDTO() {
        return userIdsDTO;
    }

    public void setUserIdsDTO(UserIdsDTO userIdsDTO) {
        this.userIdsDTO = userIdsDTO;
    }

    public UserFieldsWithoutPasswordDTO getUserFieldsWithoutPasswordDTO() {
        return userFieldsWithoutPasswordDTO;
    }

    public void setUserFieldsWithoutPasswordDTO(UserFieldsWithoutPasswordDTO userFieldsWithoutPasswordDTO) {
        this.userFieldsWithoutPasswordDTO = userFieldsWithoutPasswordDTO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}