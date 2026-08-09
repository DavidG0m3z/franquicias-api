package com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.adapter;

import com.davidgomez.franquicias_api.domain.model.product.Product;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.document.ProductDocument;
import com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.repository.ProductMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductMongoRepository mongoRepository;

    @Override
    public Mono<Product> save(Product product){
        return mongoRepository.save(toDocument(product))
                .map(this::toDomain);
    }

    @Override
    public Mono<Product> findById(String id){
        return mongoRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id){
        return mongoRepository.deleteById(id);
    }

    @Override
    public Flux<Product> findByBranchId(String branchId){
        return mongoRepository.findByBranchId(branchId)
                .map(this::toDomain);
    }

    @Override
    public Mono<Product> findFirstByBranchIdOrderByStockDesc(String branchId){
        return mongoRepository.findFirstByBranchIdOrderByStockDesc(branchId)
                .map(this::toDomain);
    }

    private ProductDocument toDocument(Product product){
        return new ProductDocument(product.id(), product.name(), product.stock(), product.branchId());
    }

    private Product toDomain(ProductDocument document){
        return new Product(document.getId(), document.getName(), document.getStock(), document.getBranchId());
    }
}
