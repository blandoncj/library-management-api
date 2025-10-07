package com.library.library_management_api.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Validated
public record CreateGenreDTO(
        @NotBlank @Size(min = 3, max = 50, message = "Genre name must be between 3 and 50 characters") String name) {
}
