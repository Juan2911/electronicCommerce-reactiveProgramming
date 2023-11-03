package electronicCommerce.persistence.postgresql.repositories;

import electronicCommerce.persistence.postgresql.entities.BrandEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends ReactiveCrudRepository<BrandEntity, Integer> {
}
