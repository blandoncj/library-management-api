package com.library.library_management_api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanResponseDTO(
        @NotNull Long id,
        @NotBlank String bookTitle,
        @NotBlank String username,
        @NotNull Integer quantity,
        @NotNull LocalDate loanDate,
        @NotNull LocalDate dueDate,
        LocalDate returnDate,
        @NotNull Boolean isReturned) {
}
