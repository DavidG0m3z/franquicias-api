package com.davidgomez.franquicias_api.domain.usecase.product;

import com.davidgomez.franquicias_api.domain.exception.NotFoundException;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import com.davidgomez.franquicias_api.domain.model.product.Product;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RequiredArgsConstructor
public class AddProductUseCase {
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public Mono<Product> execute(String branchId, String productName, Integer stock){
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new NotFoundException("Branch not found " + branchId)))
                .flatMap(branch -> {
                    Product product = new Product(UUID.randomUUID().toString(), productName, stock, branchId);
                    return productRepository.save(product);
                });
    }
}
