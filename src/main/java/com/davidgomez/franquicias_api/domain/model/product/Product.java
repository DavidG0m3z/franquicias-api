package com.davidgomez.franquicias_api.domain.model.product;

public record Product(String id, String name, Integer stock, String branchId) {

    public Product withStock(int newStock){
        return new Product(id, name, newStock, branchId);
    }

    public Product withName(String newName){
        return new Product(id, newName, stock, branchId);
    }
}
