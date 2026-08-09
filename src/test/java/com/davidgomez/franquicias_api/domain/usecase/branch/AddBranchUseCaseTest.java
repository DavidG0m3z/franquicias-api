package com.davidgomez.franquicias_api.domain.usecase.branch;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import com.davidgomez.franquicias_api.domain.model.franchise.Franchise;
import com.davidgomez.franquicias_api.domain.model.franchise.gateways.FranchiseRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddBranchUseCaseTest {

    private final BranchRepository branchRepository = mock(BranchRepository.class);
    private final FranchiseRepository franchiseRepository = mock(FranchiseRepository.class);
    private final AddBranchUseCase addBranchUseCase = new AddBranchUseCase(branchRepository, franchiseRepository);

    @Test
    void shouldAddBranchSuccessfully() {
        String franchiseId = "franchise-1";
        when(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(new Franchise(franchiseId, "McDonald's")));
        when(branchRepository.save(any(Branch.class))).thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        Mono<Branch> result = addBranchUseCase.execute(franchiseId, "Sede Centro");


        StepVerifier.create(result)
                .expectNextMatches(branch -> branch.id() != null && branch.name().equals("Sede Centro") && branch.franchiseId().equals(franchiseId))
                .verifyComplete();
    }

    @Test
    void shouldThrowNotFoundExceptionWhenFranchiseDoesNotExist() {
        String frianchiseId = "franchise-inexistente";
        when(franchiseRepository.findById(frianchiseId)).thenReturn(Mono.empty());

        Mono<Branch> result = addBranchUseCase.execute(frianchiseId, "Sede Centro");

        StepVerifier.create(result)
                .expectErrorMatches(error -> error instanceof NotFoundException && error.getMessage().contains(frianchiseId))
                .verify();

        verify(branchRepository, never()).save(any());
    }
}
