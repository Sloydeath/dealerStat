package com.leverx.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PasswordDTO {
    @NotEmpty
    @NotNull
    private String code;
    @NotEmpty
    @NotNull
    private String newPassword;
}
