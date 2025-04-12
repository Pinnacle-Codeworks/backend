package com.markguiang.backend.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoginResponseDTO {
    @JsonUnwrapped
    private UserResponseDTO userResponseDTO;
    private String csrfToken;

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public UserResponseDTO getUserResponseDTO() {
        return userResponseDTO;
    }

    public void setUserResponseDTO(UserResponseDTO userResponseDTO) {
        this.userResponseDTO = userResponseDTO;
    }
}