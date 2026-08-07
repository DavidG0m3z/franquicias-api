package com.davidgomez.franquicias_api.domain.usecases.branch;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import com.davidgomez.franquicias_api.domain.usecases.franchise.UpdateFranchiseNameUseCase;
import reactor.core.publisher.Mono;

public class UpdateBranchNameUseCase {

    private final BranchRepository branchRepository;

    public UpdateBranchNameUseCase(BranchRepository branchRepository){
        this.branchRepository = branchRepository;
    }

    public Mono<Branch> execute(String branchId, String newName) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new NotFoundException("Branch not found " + branchId)))
                .map(branch -> branch.withName(newName))
                .flatMap(branchRepository :: save);
    }

}
