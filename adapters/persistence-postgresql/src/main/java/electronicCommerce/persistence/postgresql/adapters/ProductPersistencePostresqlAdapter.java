package electronicCommerce.persistence.postgresql.adapters;

import electronicCommerce.domain.exceptions.NotFoundException;
import electronicCommerce.domain.models.Product;
import electronicCommerce.domain.models.RequestDomain;
import electronicCommerce.domain.models.impl.GetProductRequest;
import electronicCommerce.domain.ports.ProductPersistence;
import electronicCommerce.persistence.postgresql.entities.ProductEntity;
import electronicCommerce.persistence.postgresql.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@AllArgsConstructor
@Slf4j
public class ProductPersistencePostresqlAdapter implements ProductPersistence {

    private final ProductRepository productRepository;

    @Override
    public Mono<Product> findByDateAndProductIdAndBrandId(RequestDomain requestDomain) throws NotFoundException {
        GetProductRequest getProductRequest = (GetProductRequest) requestDomain;


        return productRepository
                .findProductsInDateRangeAndByProductAndBrand(getProductRequest.getDate(), getProductRequest.getProductId(), getProductRequest.getBrandId())
                .collectList()
                .flatMap(productEntities -> {
                    if (productEntities.isEmpty()) {
                        log.warn("Product Not Found");
                        return Mono.error(new NotFoundException("Product Not Found"));
                    } else {
                        ProductEntity productEntity = productEntities.stream().max(Comparator.comparingInt(ProductEntity::getPriority)).get();
                        return Mono.just(ProductEntity.toProduct(productEntity));
                    }
                });
    }
}
