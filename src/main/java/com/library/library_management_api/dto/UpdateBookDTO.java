package com.library.library_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateBookDTO(
        @NotBlank @Size(min = 3, max = 100, message = "Book title must be between 3 and  100 characteres") String title,
        @NotBlank @Size(min = 3, max = 100, message = "Book author must be between 3 and 100 characteres") String author,
        @NotBlank Integer publicationYear,
        @NotBlank Integer stock,
        @NotBlank Long genreId) {
}
