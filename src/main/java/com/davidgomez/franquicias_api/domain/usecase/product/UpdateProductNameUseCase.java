package com.davidgomez.franquicias_api.domain.usecase.product;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.product.Product;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateProductNameUseCase {
    private final ProductRepository productRepository;

    public Mono<Product> execute(String productId, String newName){
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found "+ productId)))
                .map(product -> product.withName(newName))
                .flatMap(productRepository :: save);
    }
}
