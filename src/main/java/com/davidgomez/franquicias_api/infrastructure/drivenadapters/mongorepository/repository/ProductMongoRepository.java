package com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.repository;

import com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.document.ProductDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductMongoRepository extends ReactiveMongoRepository<ProductDocument, String> {

    Flux<ProductDocument> findByBranchId(String branchId);
    Mono<ProductDocument> findFirstByBranchIdOrderByStockDesc(String branchId);
}
