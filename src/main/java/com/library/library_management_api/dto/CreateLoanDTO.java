package com.library.library_management_api.dto;

import jakarta.validation.constraints.NotNull;

public record CreateLoanDTO(
        @NotNull Long bookId,
        @NotNull Long userId,
        @NotNull Integer quantity) {
}
