package electronicCommerce.domain.services;

import electronicCommerce.domain.models.Product;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Product> readProductBy(final String date, final int productId, final int brandId);
}