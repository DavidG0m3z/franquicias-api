package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto;

import jakarta.validation.constraints.NotBlank;

public record AddBrnachRequest(@NotBlank(message = "Name is required") String name) {
}
