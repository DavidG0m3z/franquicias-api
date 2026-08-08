package com.davidgomez.franquicias_api.domain.model.branch.gateways;

import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface BranchRepository {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(String id);
    Flux<Branch> findByFranchiseId(String franchiseId);
}
