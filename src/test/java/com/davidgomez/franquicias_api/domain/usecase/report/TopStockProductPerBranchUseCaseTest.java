package com.davidgomez.franquicias_api.domain.usecase.report;

import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import com.davidgomez.franquicias_api.domain.model.product.Product;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TopStockProductPerBranchUseCaseTest {

    private final BranchRepository branchRepository = mock(BranchRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final TopStockProductPerBranchUseCase topStockProductPerBranchUseCase = new TopStockProductPerBranchUseCase(branchRepository, productRepository);

    @Test
    void shouldReturnTopProductForEachBranch() {
        String franchiseId = "franchise-1";
        Branch branch1 = new Branch("branch-1", "Sede Centro", franchiseId);
        Branch branch2 = new Branch("branch-2", "Sede Norte", franchiseId);

        when(branchRepository.findByFranchiseId(franchiseId))
                .thenReturn(Flux.just(branch1, branch2));
        when(productRepository.findFirstByBranchIdOrderByStockDesc("branch-1")).thenReturn(Mono.just(new Product(
                "product-1",
                "Chaqueta Cuero",
                100,
                "branch-1")));
        when(productRepository.findFirstByBranchIdOrderByStockDesc("branch-2")).thenReturn(Mono.just(new Product(
                "product-2",
                "Camisa Lino",
                80,
                "branch-2")));

        Mono<List<ProductByBranch>> result =
                topStockProductPerBranchUseCase.execute(franchiseId).collectList();

        StepVerifier.create(result)
                .expectNextMatches(list ->
                        list.size() == 2
                                && list.stream().anyMatch(p -> p.branchId().equals("branch-1") && p.productName().equals("Chaqueta Cuero"))
                                && list.stream().anyMatch(p -> p.branchId().equals("branch-2") && p.productName().equals("Camisa Lino")))
                .verifyComplete();
    }
}
