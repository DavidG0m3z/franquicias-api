package com.davidgomez.franquicias_api.domain.usecase.report;

import com.davidgomez.franquicias_api.domain.model.branch.Branch;
import com.davidgomez.franquicias_api.domain.model.branch.gateways.BranchRepository;
import com.davidgomez.franquicias_api.domain.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TopStockProductPerBranchUseCase {
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    public Flux<ProductByBranch> execute(String franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId)
                .flatMap(this :: topProductPerBranch);
    }

    private Mono<ProductByBranch> topProductPerBranch(Branch branch) {
        return productRepository.findTopProductStockByBranchId(branch.id())
                .map(product -> new ProductByBranch(
                        branch.id(),
                        branch.name(),
                        product.id(),
                        product.name(),
                        product.stock()
                ));
    }
}
