package electronicCommerce.rest.controllers;

import electronicCommerce.domain.models.Product;
import electronicCommerce.domain.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ProductController.PRODUCT)
@AllArgsConstructor
@Slf4j
public class ProductController {
    static final String PRODUCT = "/product";
    private static final String PRODUCT_ID = "/productId/{productId}";
    private static final String DATE = "/date/{date}";
    private static final String BRAND_ID = "/brandId/{brandId}";
    private final ProductService productServiceImpl;

    @GetMapping(DATE + PRODUCT_ID + BRAND_ID)
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable("date") String date, @PathVariable("productId") int productId, @PathVariable("brandId") int brandId) {
        log.info("Getting product");

        return productServiceImpl.readProductBy(date, productId, brandId)
                .map(product -> ResponseEntity.ok(product));
    }
}
