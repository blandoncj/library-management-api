package com.library.library_management_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockDTO(@NotNull @Min(1) Integer quantity) {
}
