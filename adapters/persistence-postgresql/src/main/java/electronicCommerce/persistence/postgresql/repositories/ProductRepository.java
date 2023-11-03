package electronicCommerce.persistence.postgresql.repositories;

import electronicCommerce.persistence.postgresql.entities.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Integer> {

    @Query("SELECT * FROM product WHERE :date >= start_date AND :date <= end_date AND product_id = :productId AND brand_id = :brandId")
    Flux<ProductEntity> findProductsInDateRangeAndByProductAndBrand(LocalDateTime date, int productId, int brandId);
}
