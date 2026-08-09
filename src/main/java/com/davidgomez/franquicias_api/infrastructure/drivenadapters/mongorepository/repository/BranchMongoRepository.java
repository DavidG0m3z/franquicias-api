package com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.repository;

import com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.document.BranchDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BranchMongoRepository extends ReactiveMongoRepository<BranchDocument, String> {

    Flux<BranchDocument> findByFranchiseId(String franchiseId);

    String id(String id);
}
