package com.davidgomez.franquicias_api.domain.usecase.franchise;

import com.davidgomez.franquicias_api.domain.model.franchise.Franchise;
import com.davidgomez.franquicias_api.domain.model.franchise.gateways.FranchiseRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateFranchiseNameUseCaseTest {

    private final FranchiseRepository franchiseRepository = mock(FranchiseRepository.class);
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase = new UpdateFranchiseNameUseCase(franchiseRepository);

    @Test
    void shouldUpdateFranchiseNameSuccessfully() {
        Franchise exist = new Franchise("franchise-1", "Cueros Velez");
        when(franchiseRepository.findById("franchise-1")).thenReturn(Mono.just(exist));
        when(franchiseRepository.save(any(Franchise.class)))
                .thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        Mono<Franchise> result = updateFranchiseNameUseCase.execute("franchise-1", "Arturo Calle");

        StepVerifier.create(result)
                .expectNextMatches(franchise -> franchise.name().equals("Arturo Calle"))
                .verifyComplete();

    }
}
