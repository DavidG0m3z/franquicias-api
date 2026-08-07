package com.davidgomez.franquicias_api.domain.usecases.franchise;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.franchise.Franchise;
import com.davidgomez.franquicias_api.domain.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

public class UpdateFranchiseNameUseCase {

    private final FranchiseRepository franchiseRepository;

    public UpdateFranchiseNameUseCase(FranchiseRepository franchiseRepository){
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Franchise> execute(String franchiseId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found" + franchiseId)))
                .map(franchise -> franchise.withName(newName))
                .flatMap(franchiseRepository :: save);
    }

}
