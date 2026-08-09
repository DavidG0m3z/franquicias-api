package com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.adapter;

import com.davidgomez.franquicias_api.domain.model.franchise.Franchise;
import com.davidgomez.franquicias_api.domain.model.franchise.gateways.FranchiseRepository;
import com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.document.FranchiseDocument;
import com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.repository.FranchiseMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepository {

    private final FranchiseMongoRepository mongoRepository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return mongoRepository.save(toDocument(franchise))
                .map(this::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return mongoRepository.findById(id)
                .map(this::toDomain);
    }

    private FranchiseDocument toDocument(Franchise franchise){
        return new FranchiseDocument(franchise.id(), franchise.name());
    }

    private Franchise toDomain(FranchiseDocument document){
        return new Franchise(document.getId(), document.getName());
    }
}
