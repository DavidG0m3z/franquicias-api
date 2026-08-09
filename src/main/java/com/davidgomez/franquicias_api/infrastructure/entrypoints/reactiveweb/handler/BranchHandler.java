package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.handler;

import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.usecase.branch.AddBranchUseCase;
import com.davidgomez.franquicias_api.domain.usecase.branch.UpdateBranchNameUseCase;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.AddBrnachRequest;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.BranchResponse;
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
public class BranchHandler {

    private final AddBranchUseCase addBranchUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> addBranch(ServerRequest request) {
        String branchId = request.pathVariable("franchiseId");
        return request.bodyToMono(AddBrnachRequest.class)
                .flatMap(requestValidator :: validate)
                .flatMap(req -> addBranchUseCase.execute(branchId, req.name()))
                .map(this :: toResponse)
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(response));
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        return request.bodyToMono(UpdateNameRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(req -> updateBranchNameUseCase.execute(branchId, req.name()))
                .map(this::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    private BranchResponse toResponse(Branch branch) {
        return new BranchResponse(branch.id(), branch.name(), branch.franchiseId());
    }
}
