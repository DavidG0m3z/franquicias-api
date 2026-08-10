package com.davidgomez.franquicias_api.domain.usecase.product;
import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import com.davidgomez.franquicias_api.domain.model.product.Product;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddProductUseCaseTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final BranchRepository branchRepository = mock(BranchRepository.class);
    private final AddProductUseCase addProductUseCase = new AddProductUseCase(productRepository, branchRepository);

    @Test
    void shouldAddProductSuccessfully() {
        String branchId = "branch-1";
        when(branchRepository.findById(branchId)).thenReturn(Mono.just(new Branch(branchId, "Sede Centro", "franchise-1")));
        when(productRepository.save(any(Product.class))).thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        Mono<Product> result = addProductUseCase.execute(branchId, "Chaqueta Cuero", 50);

        StepVerifier.create(result)
                .expectNextMatches(product -> product.id() != null && product.name().equals("Chaqueta Cuero") && product.stock() == 50 && product.branchId().equals(branchId))
                .verifyComplete();
    }
}
