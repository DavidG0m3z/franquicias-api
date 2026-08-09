package com.davidgomez.franquicias_api.config;

import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import com.davidgomez.franquicias_api.domain.model.franchise.gateways.FranchiseRepository;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import com.davidgomez.franquicias_api.domain.usecase.branch.AddBranchUseCase;
import com.davidgomez.franquicias_api.domain.usecase.branch.UpdateBranchNameUseCase;
import com.davidgomez.franquicias_api.domain.usecase.franchise.CreateFranchiseUseCase;
import com.davidgomez.franquicias_api.domain.usecase.franchise.UpdateFranchiseNameUseCase;
import com.davidgomez.franquicias_api.domain.usecase.product.AddProductUseCase;
import com.davidgomez.franquicias_api.domain.usecase.product.DeleteProductUseCase;
import com.davidgomez.franquicias_api.domain.usecase.product.UpdateProductNameUseCase;
import com.davidgomez.franquicias_api.domain.usecase.product.UpdateProductStockUseCase;
import com.davidgomez.franquicias_api.domain.usecase.report.TopStockProductPerBranchUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public CreateFranchiseUseCase createFranchiseUseCase(FranchiseRepository franchiseRepository){
        return new CreateFranchiseUseCase(franchiseRepository);
    }

    @Bean
    public UpdateFranchiseNameUseCase updateFranchiseNameUseCase(FranchiseRepository franchiseRepository){
        return new UpdateFranchiseNameUseCase(franchiseRepository);
    }

    @Bean
    public AddBranchUseCase addBranchUseCase(BranchRepository branchRepository, FranchiseRepository franchiseRepository){
        return new AddBranchUseCase(branchRepository, franchiseRepository);
    }

    @Bean
    public UpdateBranchNameUseCase updateBranchNameUseCase(BranchRepository branchRepository){
        return new UpdateBranchNameUseCase(branchRepository);
    }

    @Bean
    public AddProductUseCase addProductUseCase(ProductRepository productRepository, BranchRepository branchRepository){
        return new AddProductUseCase(productRepository, branchRepository);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductRepository productRepository) {
        return new DeleteProductUseCase(productRepository);
    }

    @Bean
    public UpdateProductStockUseCase updateProductStockUseCase(ProductRepository productRepository) {
        return new UpdateProductStockUseCase(productRepository);
    }

    @Bean
    public UpdateProductNameUseCase updateProductNameUseCase(ProductRepository productRepository) {
        return new UpdateProductNameUseCase(productRepository);
    }

    @Bean
    public TopStockProductPerBranchUseCase topStockProductPerBranchUseCase(BranchRepository branchRepository, ProductRepository productRepository) {
        return new TopStockProductPerBranchUseCase(branchRepository, productRepository);
    }
}
