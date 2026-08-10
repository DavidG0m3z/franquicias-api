package com.davidgomez.franquicias_api.domain.usecase.product;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.product.Product;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateProductStockUseCaseTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final UpdateProductStockUseCase updateProductStockUseCase = new UpdateProductStockUseCase(productRepository);

    @Test
    void shouldUpdateProductStockSuccessully() {
        Product exist = new Product("product-1", "Chaqueta Cuero", 50, "branch-1");
        when(productRepository.findById("product-1")).thenReturn(Mono.just(exist));
        when(productRepository.save(any(Product.class))).thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        Mono<Product> result = updateProductStockUseCase.execute("product-1", 100);

        StepVerifier.create(result)
                .expectNextMatches(product -> product.stock() == 100)
                .verifyComplete();
    }

    @Test
    void shouldThrowNotFoundExceptionWhenProductDoesNotExist() {
        String productId = "product-inexistente";
        when(productRepository.findById(productId)).thenReturn(Mono.empty());

        Mono<Product> result = updateProductStockUseCase.execute(productId, 100);

        StepVerifier.create(result)
                .expectErrorMatches(error -> error instanceof NotFoundException && error.getMessage().contains(productId))
                .verify();

        verify(productRepository, never()).save(any());
    }
}
