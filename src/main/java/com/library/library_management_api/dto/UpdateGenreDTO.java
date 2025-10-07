package com.library.library_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateGenreDTO(
        @NotBlank @Size(min = 3, max = 50, message = "Genre name must be between 3 and 50 characteres") String name) {

}
