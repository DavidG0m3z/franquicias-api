package com.davidgomez.franquicias_api.domain.usecase.branch;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateBranchNameUseCase {
    private final BranchRepository branchRepository;

    public Mono<Branch> execute(String branchId, String newName) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new NotFoundException("Branch not found " + branchId)))
                .map(branch -> branch.withName(newName))
                .flatMap(branchRepository :: save);
    }
}
