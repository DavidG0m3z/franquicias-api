package com.davidgomez.franquicias_api.domain.usecase.report;

public record ProductByBranch(
        String branchId,
        String branchName,
        String productId,
        String productName,
        Integer stock) {
}
