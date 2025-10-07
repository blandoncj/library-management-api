package com.library.library_management_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record SignupDTO(@NotBlank String username, @NotBlank String password,
        @Valid CreateRoleDTO roleRequest) {

}
