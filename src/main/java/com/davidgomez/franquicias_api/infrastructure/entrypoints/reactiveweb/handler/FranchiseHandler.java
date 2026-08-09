package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.handler;

import com.davidgomez.franquicias_api.domain.model.franchise.Franchise;
import com.davidgomez.franquicias_api.domain.usecase.branch.UpdateBranchNameUseCase;
import com.davidgomez.franquicias_api.domain.usecase.franchise.CreateFranchiseUseCase;
import com.davidgomez.franquicias_api.domain.usecase.franchise.UpdateFranchiseNameUseCase;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.CreateFranchiseRequest;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.FranchiseResponse;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.UpdateNameRequest;
import com.davidgomez.franquicias_api.infrastructure.helpers.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseHandler {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(CreateFranchiseRequest.class)
                .flatMap(requestValidator :: validate)
                .flatMap(req -> createFranchiseUseCase.execute(req.name()))
                .map(this :: toResponse)
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(response));
    }

    public Mono<ServerResponse> updateFranchiseName(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return request.bodyToMono(UpdateNameRequest.class)
                .flatMap(requestValidator :: validate)
                .flatMap(req -> updateFranchiseNameUseCase.execute(franchiseId, req.name()))
                .map(this :: toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public FranchiseResponse toResponse(Franchise franchise) {
        return new FranchiseResponse(franchise.id(), franchise.name());
    }
}
