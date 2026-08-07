package com.davidgomez.franquicias_api.domain.model.branch;

public record Branch(String id, String name, String franchiseId) {

    public Branch withName(String newName){
        return new Branch(id, newName, franchiseId);
    }
}
