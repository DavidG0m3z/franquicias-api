package com.davidgomez.franquicias_api.domain.usecases.branch;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import com.davidgomez.franquicias_api.domain.model.franchise.gateways.FranchiseRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class AddBranchUseCase {

    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    public AddBranchUseCase(BranchRepository branchRepository, FranchiseRepository franchiseRepository){
        this.branchRepository = branchRepository;
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Branch> execute(String franchiseId, String branchName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found " + franchiseId)))
                .flatMap(franchise -> {
                   Branch branch = new Branch(UUID.randomUUID().toString(), branchName, franchiseId);
                   return branchRepository.save(branch);
                });
    }

}
