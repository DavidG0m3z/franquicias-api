package com.davidgomez.franquicias_api.domain.usecase.franchise;

import com.davidgomez.franquicias_api.domain.model.franchise.Franchise;
import com.davidgomez.franquicias_api.domain.model.franchise.gateways.FranchiseRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateFranchiseUseCaseTest {

    private final FranchiseRepository franchiseRepository = mock(FranchiseRepository.class);
    private final CreateFranchiseUseCase createFranchiseUseCase = new CreateFranchiseUseCase(franchiseRepository);

    @Test
    void shouldCreateFranchiseSuccessfully() {
        String name = "Cueros Velez";
        when(franchiseRepository.save(any(Franchise.class)))
                .thenAnswer(invocationOnMock -> Mono.just((invocationOnMock.getArgument(0))));

        Mono<Franchise> result = createFranchiseUseCase.execute(name);

        StepVerifier.create(result)
                .expectNextMatches(franchise -> franchise.id() != null && franchise.name().equals(name))
                .verifyComplete();
    }
}
