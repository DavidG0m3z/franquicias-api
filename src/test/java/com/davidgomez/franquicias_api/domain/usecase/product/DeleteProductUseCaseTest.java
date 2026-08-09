package com.davidgomez.franquicias_api.domain.usecase.product;

import com.davidgomez.franquicias_api.domain.model.product.Product;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteProductUseCaseTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final DeleteProductUseCase deleteProductUseCase = new DeleteProductUseCase(productRepository);

    @Test
    void shouldDeleteProductSuccessfully() {
        String productId = "product-1";
        Product exist = new Product(productId, "Big Mac", 50, "branch-1");
        when(productRepository.findById(productId)).thenReturn(Mono.just(exist));
        when(productRepository.deleteById(productId)).thenReturn(Mono.empty());

        Mono<Void> result = deleteProductUseCase.execute(productId);

        StepVerifier.create(result).verifyComplete();
    }
}
