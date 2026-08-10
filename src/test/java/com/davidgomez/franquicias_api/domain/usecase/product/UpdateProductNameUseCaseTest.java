package com.davidgomez.franquicias_api.domain.usecase.product;

import com.davidgomez.franquicias_api.domain.model.product.Product;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateProductNameUseCaseTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final UpdateProductNameUseCase updateProductNameUseCase = new UpdateProductNameUseCase(productRepository);

    @Test
    void shouldUpdateProductNameSuccessfully() {
        Product exist = new Product("product-1", "Chaqueta Cuero", 50, "branch-1");
        when(productRepository.findById("product-1")).thenReturn(Mono.just(exist));
        when(productRepository.save(any(Product.class))).thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        Mono<Product> result = updateProductNameUseCase.execute("product-1", "Camisa Lino");

        StepVerifier.create(result)
                .expectNextMatches(product -> product.name().equals("Camisa Lino"))
                .verifyComplete();
    }
}
