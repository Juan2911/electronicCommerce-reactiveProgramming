package electronicCommerce.domain.ports;

import electronicCommerce.domain.exceptions.NotFoundException;
import electronicCommerce.domain.models.Product;
import electronicCommerce.domain.models.RequestDomain;
import reactor.core.publisher.Mono;

public interface ProductPersistence {
    Mono<Product> findByDateAndProductIdAndBrandId(final RequestDomain requestDomain) throws NotFoundException;
}
    