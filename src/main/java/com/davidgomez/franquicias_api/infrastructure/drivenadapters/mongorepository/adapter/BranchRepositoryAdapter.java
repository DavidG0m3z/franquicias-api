package com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.adapter;

import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.document.BranchDocument;
import com.davidgomez.franquicias_api.infrastructure.drivenadapters.mongorepository.repository.BranchMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class BranchRepositoryAdapter implements BranchRepository {

    private final BranchMongoRepository mongoRepository;

    @Override
    public Mono<Branch> save(Branch branch){
        return mongoRepository.save(toDocument(branch))
                .map(this::toDomain);
    }

    @Override
    public Mono<Branch> findById(String id) {
        return mongoRepository.findById(id)
                .map(this::toDomain);
    }

    public Flux<Branch> findByFranchiseId(String franchiseId){
        return mongoRepository.findByFranchiseId(franchiseId)
                .map(this::toDomain);
    }

    private BranchDocument toDocument(Branch branch) {
        return new BranchDocument(branch.id(), branch.name(), branch.franchiseId());
    }

    private Branch toDomain(BranchDocument document){
        return new Branch(document.getId(), document.getName(), document.getFranchiseId());
    }
}
