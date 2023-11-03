package electronicCommerce.rest.controllers;

import electronicCommerce.domain.models.Product;
import electronicCommerce.domain.services.impl.ProductServiceImpl;
import electronicCommerce.rest.utils.TestObjectBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ProductControllerTest {

    private ProductController productController;

    @Mock
    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        productController = new ProductController(productServiceImpl);
    }

    @Test
    public void shouldGetACorrectProduct() {
        Product product = TestObjectBuilder.buildAProductWithPriority();

        when(productServiceImpl.readProductBy("2020-06-14-17.00.00",1,1)).thenReturn(Mono.just(product));

        Mono<ResponseEntity<Product>> responseEntity = productController.getProduct("2020-06-14-17.00.00",1,1);
        ResponseEntity<Product> response = responseEntity.block();

        Assert.assertEquals(product, response.getBody());
    }
}
