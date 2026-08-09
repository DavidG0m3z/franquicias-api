package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.handler;

import com.davidgomez.franquicias_api.domain.model.product.Product;
import com.davidgomez.franquicias_api.domain.usecase.product.AddProductUseCase;
import com.davidgomez.franquicias_api.domain.usecase.product.DeleteProductUseCase;
import com.davidgomez.franquicias_api.domain.usecase.product.UpdateProductNameUseCase;
import com.davidgomez.franquicias_api.domain.usecase.product.UpdateProductStockUseCase;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.AddProductRequest;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.ProductResponse;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.UpdateNameRequest;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.dto.UpdateStockRequest;
import com.davidgomez.franquicias_api.infrastructure.helpers.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final AddProductUseCase addProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> addProduct(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        return request.bodyToMono(AddProductRequest.class)
                .flatMap(requestValidator :: validate)
                .flatMap(req -> addProductUseCase.execute(branchId, req.name(), req.stock()))
                .map(this :: toResponse)
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(response));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String productId = request.pathVariable("productId");
        return deleteProductUseCase.execute(productId)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateStock(ServerRequest request) {
        String productId = request.pathVariable("productId");
        return request.bodyToMono(UpdateStockRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(req -> updateProductStockUseCase.execute(productId, req.stock()))
                .map(this::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> updateName(ServerRequest request) {
        String productId = request.pathVariable("productId");
        return request.bodyToMono(UpdateNameRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(req -> updateProductNameUseCase.execute(productId, req.name()))
                .map(this::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(product.id(), product.name(), product.stock(), product.branchId());
    }
}
