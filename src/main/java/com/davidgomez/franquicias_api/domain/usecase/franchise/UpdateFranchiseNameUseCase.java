package com.davidgomez.franquicias_api.domain.usecase.franchise;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.franchise.Franchise;
import com.davidgomez.franquicias_api.domain.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateFranchiseNameUseCase {
    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> execute(String franchiseId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found" + franchiseId)))
                .map(franchise -> franchise.withName(newName))
                .flatMap(franchiseRepository :: save);
    }
}
