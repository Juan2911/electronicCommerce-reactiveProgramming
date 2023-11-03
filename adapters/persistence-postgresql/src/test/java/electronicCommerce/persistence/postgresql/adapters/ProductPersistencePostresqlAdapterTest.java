package electronicCommerce.persistence.postgresql.adapters;

import electronicCommerce.domain.exceptions.NotFoundException;
import electronicCommerce.domain.models.Product;
import electronicCommerce.domain.models.impl.GetProductRequest;
import electronicCommerce.persistence.postgresql.entities.ProductEntity;
import electronicCommerce.persistence.postgresql.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import utils.TestObjectBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ProductPersistencePostresqlAdapterTest {
    private ProductPersistencePostresqlAdapter productPersistencePostresqlAdapter;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        productPersistencePostresqlAdapter = new ProductPersistencePostresqlAdapter(productRepository);
    }

    @Test
    public void testFindByDateAndProductIdAndBrandId_ProductFound() throws NotFoundException {
        GetProductRequest getProductRequest = (GetProductRequest) TestObjectBuilder.buildGetProductRequest();
        ProductEntity productEntity = TestObjectBuilder.buildAProductEntityWithoutPriority();
        Product product = TestObjectBuilder.buildAProductWithoutPriority();

        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(productEntity);

        when(productRepository.findProductsInDateRangeAndByProductAndBrand(any(), anyInt(), anyInt()))
                .thenReturn(Flux.fromIterable(productEntities));

        Mono<Product> result = productPersistencePostresqlAdapter.findByDateAndProductIdAndBrandId(getProductRequest);
        Product productResponse = result.block();

        assertEquals(product, productResponse);
    }


    @Test
    public void shouldReturnMostPriorityProduct() {
        GetProductRequest getProductRequest = (GetProductRequest) TestObjectBuilder.buildGetProductRequest();
        ProductEntity productEntityWithoutPriority = TestObjectBuilder.buildAProductEntityWithoutPriority();
        ProductEntity productEntityWithPriority = TestObjectBuilder.buildAProductEntityWithPriority();
        List<ProductEntity> productEntities = new ArrayList<>();
        Product product = TestObjectBuilder.buildAProductWithPriority();
        productEntities.add(productEntityWithoutPriority);
        productEntities.add(productEntityWithPriority);

        when(productRepository.findProductsInDateRangeAndByProductAndBrand(any(), anyInt(), anyInt()))
                .thenReturn(Flux.fromIterable(productEntities));

        Mono<Product> result = productPersistencePostresqlAdapter.findByDateAndProductIdAndBrandId(getProductRequest);
        Product productResponse = result.block();
        assertEquals(product, productResponse);
    }

    @Test
    public void shouldThrowANotFoundException() {
        GetProductRequest getProductRequest = (GetProductRequest) TestObjectBuilder.buildGetProductRequest();
        List<ProductEntity> productEntities = new ArrayList<>();

        when(productRepository.findProductsInDateRangeAndByProductAndBrand(getProductRequest.getDate(),
                getProductRequest.getProductId(),
                getProductRequest.getBrandId())).thenReturn(Flux.fromIterable(productEntities));

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> productPersistencePostresqlAdapter.findByDateAndProductIdAndBrandId(getProductRequest).block());

        assertEquals("Product Not Found", notFoundException.getMessage());
    }
}