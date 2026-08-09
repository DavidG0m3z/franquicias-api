package com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.router;

import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.handler.BranchHandler;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.handler.FranchiseHandler;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.handler.ProductHandler;
import com.davidgomez.franquicias_api.infrastructure.entrypoints.reactiveweb.handler.ReportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(
            FranchiseHandler franchiseHandler,
            BranchHandler branchHandler,
            ProductHandler productHandler,
            ReportHandler reportHandler
    ) {
        return RouterFunctions
                .route(POST("/franchises"), franchiseHandler::createFranchise)
                .andRoute(PATCH("/franchises/{franchiseId}/name"), franchiseHandler::updateFranchiseName)
                .andRoute(POST("/franchises/{franchiseId}/branches"), branchHandler::addBranch)
                .andRoute(PATCH("/branches/{branchId}/name"), branchHandler::updateBranchName)
                .andRoute(POST("/branches/{branchId}/products"), productHandler::addProduct)
                .andRoute(DELETE("/products/{productId}"), productHandler::deleteProduct)
                .andRoute(PATCH("/products/{productId}/stock"), productHandler::updateStock)
                .andRoute(PATCH("/products/{productId}/name"), productHandler::updateName)
                .andRoute(GET("/franchises/{franchiseId}/top-stock-products"), reportHandler::topStockPerBranch);
    }
}
