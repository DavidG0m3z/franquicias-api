package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddProductRequest(
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "Stock is required")
        @Min(value = 0, message = "Stock cannot be negative") Integer stock
) {
}
