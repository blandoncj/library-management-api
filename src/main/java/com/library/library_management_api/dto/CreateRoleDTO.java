package com.library.library_management_api.dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;

@Validated
public record CreateRoleDTO(
        @Size(max = 2, message = "The user cannot have more than 2 roles") List<String> roleNamesList) {
}
