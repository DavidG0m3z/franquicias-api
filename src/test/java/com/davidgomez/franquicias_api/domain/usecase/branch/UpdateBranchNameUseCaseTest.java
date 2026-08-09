package com.davidgomez.franquicias_api.domain.usecase.branch;

import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateBranchNameUseCaseTest {

    private final BranchRepository branchRepository = mock(BranchRepository.class);
    private final UpdateBranchNameUseCase updateBranchNameUseCase = new UpdateBranchNameUseCase(branchRepository);

    @Test
    void shouldUpdateBranchNameSuccessfully() {
        Branch exist = new Branch("branch-1", "Sede Centro", "franchise-1");
        when(branchRepository.findById("branch-1")).thenReturn(Mono.just(exist));
        when(branchRepository.save(any(Branch.class))).thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        Mono<Branch> result = updateBranchNameUseCase.execute("branch-1", "Sede Norte");

        StepVerifier.create(result)
                .expectNextMatches(branch -> branch.name().equals("Sede Norte"))
                .verifyComplete();
    }
}
