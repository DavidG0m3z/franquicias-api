package com.davidgomez.franquicias_api.domain.usecase.franchise;

import com.davidgomez.franquicias_api.domain.model.franchise.Franchise;
import com.davidgomez.franquicias_api.domain.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateFranchiseUseCase {
    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> execute(String name){
        Franchise franchise = new Franchise(UUID.randomUUID().toString(), name);
        return franchiseRepository.save(franchise);
    }
}
