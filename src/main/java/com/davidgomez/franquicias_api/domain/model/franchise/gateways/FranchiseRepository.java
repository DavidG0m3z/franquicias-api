package com.davidgomez.franquicias_api.domain.model.franchise.gateways;

import com.davidgomez.franquicias_api.domain.model.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(String id);
}
