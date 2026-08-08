package com.davidgomez.franquicias_api.domain.model.product.gateways;

import com.davidgomez.franquicias_api.domain.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product product);
    Mono<Product> findById(String Id);
    Mono<Void> deleteById(String Id);
    Flux<Product> findByBranchId(String BranchId);
    Mono<Product> findTopProductStockByBranchId(String branchId);
}
