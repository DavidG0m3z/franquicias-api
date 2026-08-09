package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto;

public record ProductByBranchResponse(String branchId, String branchName, String productId, String productName, Integer stock) {
}
