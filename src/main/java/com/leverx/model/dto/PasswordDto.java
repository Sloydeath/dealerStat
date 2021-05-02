package com.leverx.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO-class for reset password
 *
 * @author Andrew Panas
 */

@Data
public class PasswordDto {
    @NotEmpty
    @NotNull
    private String code;
    @NotEmpty
    @NotNull
    private String newPassword;
}
