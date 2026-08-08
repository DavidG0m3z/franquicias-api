package com.davidgomez.franquicias_api.domain.usecase.product;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DeleteProductUseCase {
    private final ProductRepository productRepository;

    public Mono<Void> execute(String productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found: " + productId)))
                .flatMap(product -> productRepository.deleteById(productId));
    }
}
