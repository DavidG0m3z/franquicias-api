package com.davidgomez.franquicias_api.domain.model.franchise;

public record Franchise(String id, String name) {

    public Franchise withName(String newName){
        return new Franchise(id, newName);
    }
}


