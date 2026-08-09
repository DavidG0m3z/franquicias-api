package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto;

public record ProductResponse(String id, String name, Integer stock, String branchId) {
}
