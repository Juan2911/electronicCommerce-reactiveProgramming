package electronicCommerce.domain.service.impl;

import electronicCommerce.domain.constants.Constants;
import electronicCommerce.domain.exceptions.ValidationException;
import electronicCommerce.domain.models.Product;
import electronicCommerce.domain.models.impl.GetProductRequest;
import electronicCommerce.domain.ports.ProductPersistence;
import electronicCommerce.domain.services.impl.ProductServiceImpl;
import electronicCommerce.domain.utils.TestObjectBuilder;
import electronicCommerce.domain.validations.ValidationService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ProductServiceImplTest {

    private ProductServiceImpl productServiceImpl;

    @Mock
    private ValidationService productServiceValidationServiceImpl;

    @Mock
    private ProductPersistence productPersistenceH2Adapter;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        productServiceImpl = new ProductServiceImpl(productServiceValidationServiceImpl, productPersistenceH2Adapter);
    }

    @Test
    public void shouldReturnAProduct() {
        GetProductRequest getProductRequest = (GetProductRequest) TestObjectBuilder.buildGetProductRequest();

        Product product = TestObjectBuilder.buildAProductWithPriority();

        when(productPersistenceH2Adapter.findByDateAndProductIdAndBrandId(getProductRequest)).thenReturn(Mono.just(product));
        Mono<Product> productResultMono = productServiceImpl.readProductBy("2020-06-14-17.00.00",1,1);
        Product productResult = productResultMono.block();
        Assert.assertEquals(product, productResult);
    }

    @Test
    public void shouldThrowAValidationExceptionWhenDateIsInvalid() {

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> productServiceImpl.readProductBy("2020-06-14-17",1,1));

        Assert.assertEquals(Constants.DATE_FORMAT_VALIDATION_MESSAGE, validationException.getMessage());
    }

    @Test
    public void shouldThrowAValidationExceptionWhenProductIdIsInvalid() {
        GetProductRequest getProductRequest = (GetProductRequest) TestObjectBuilder.buildGetProductRequest();

        ValidationException validationException = new ValidationException(Constants.PRODUCT_ID_CORRECT_VALUE_VALIDATION_MESSAGE);

        when(productPersistenceH2Adapter.findByDateAndProductIdAndBrandId(getProductRequest)).thenThrow(validationException);
        ValidationException validationExceptionResponse = assertThrows(ValidationException.class,
                () -> productServiceImpl.readProductBy("2020-06-14-17.00.00",1,1));

        Assert.assertEquals(Constants.PRODUCT_ID_CORRECT_VALUE_VALIDATION_MESSAGE, validationExceptionResponse.getMessage());
    }

    @Test
    public void shouldThrowAValidationExceptionWhenBrandIdIsInvalid() {
        GetProductRequest getProductRequest = (GetProductRequest) TestObjectBuilder.buildGetProductRequest();

        ValidationException validationException = new ValidationException(Constants.BRAND_ID_CORRECT_VALUE_VALIDATION_MESSAGE);

        when(productPersistenceH2Adapter.findByDateAndProductIdAndBrandId(getProductRequest)).thenThrow(validationException);
        ValidationException validationExceptionResponse = assertThrows(ValidationException.class,
                () -> productServiceImpl.readProductBy("2020-06-14-17.00.00",1,1));

        Assert.assertEquals(Constants.BRAND_ID_CORRECT_VALUE_VALIDATION_MESSAGE, validationExceptionResponse.getMessage());
    }
}