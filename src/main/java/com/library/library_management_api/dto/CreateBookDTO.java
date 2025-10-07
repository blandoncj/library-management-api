package com.library.library_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBookDTO(
        @NotBlank @Size(min = 13, max = 13, message = "Book isbn must be 13 characteres") String isbn,
        @NotBlank @Size(min = 3, max = 100, message = "Book title must be between 3 and 100 characteres") String title,
        @NotBlank @Size(min = 3, max = 100, message = "Book author must be between 3 and 100 characteres") String author,
        @NotNull(message = "Publication year cannot be null") Integer publicationYear,
        @NotNull(message = "Stock cannot be null") Integer stock,
        @NotNull(message = "Genre id cannot be null") Long genreId) {
}