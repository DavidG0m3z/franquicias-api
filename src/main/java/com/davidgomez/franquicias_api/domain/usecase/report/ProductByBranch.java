package com.davidgomez.franquicias_api.domain.usecase.report;

public record ProductByBranch(
        String branchId,
        String branchName,
        String ProductId,
        String ProductName,
        Integer stock) {
}
