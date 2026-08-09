package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateFranchiseRequest(@NotBlank(message = "name is required") String name) {
}
