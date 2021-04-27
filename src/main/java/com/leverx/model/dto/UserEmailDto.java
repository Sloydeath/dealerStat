package com.leverx.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserEmailDto {
    @NotNull
    @NotEmpty
    private String email;
}
