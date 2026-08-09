package com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.repository;

import com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.document.FranchiseDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranchiseMongoRepository extends ReactiveMongoRepository<FranchiseDocument, String> {
}
