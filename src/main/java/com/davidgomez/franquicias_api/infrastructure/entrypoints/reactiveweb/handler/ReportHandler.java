package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.handler;

import com.davidgomez.franquicias_api.domain.usecase.report.ProductByBranch;
import com.davidgomez.franquicias_api.domain.usecase.report.TopStockProductPerBranchUseCase;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.ProductByBranchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReportHandler {

    private final TopStockProductPerBranchUseCase topStockProductPerBranchUseCase;

    public Mono<ServerResponse> topStockPerBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        Flux<ProductByBranchResponse> result = topStockProductPerBranchUseCase.execute(franchiseId)
                .map(this :: toResponse);
        return ServerResponse.ok().body(result, ProductByBranchResponse.class);
    }

    private ProductByBranchResponse toResponse(ProductByBranch p) {
        return new ProductByBranchResponse(p.branchId(), p.branchName(), p.productId(), p.productName(), p.stock());
    }
}
