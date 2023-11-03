package it;

import electronicCommerce.application.ElectronicCommerceApplication;
import electronicCommerce.domain.models.Product;
import electronicCommerce.rest.exceptionHandlers.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import utils.TestObjectBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ElectronicCommerceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.profiles.active=test")
@SpringJUnitConfig
public class ProductIntegrationTest {
    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldResponseSuccessfulWhenRetrieveAProduct() {
        Product productExpected = TestObjectBuilder.buildAProductWithPriority();

        Product product = webTestClient.get()
                .uri("/product/date/2020-06-14-17.00.00/productId/35455/brandId/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        assertEquals(productExpected, product);
    }


    @Test
    public void shouldReturnABadRequestWhenBrandIdIsIncorrect() {
        ErrorResponse errorResponseExpected = TestObjectBuilder
                .buildABadRequestErrorResponse("Brand Id can not be less of 1",
                        "electronicCommerce.domain.validations.impl.ProductServiceValidationServiceImpl",
                        "validate",
                        26);

        ErrorResponse errorResponse = webTestClient.get()
                .uri("/product/date/2020-06-14-17.00.00/productId/35455/brandId/0")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertErrorResponse(errorResponseExpected, errorResponse);
    }

    @Test
    public void shouldReturnABadRequestWhenProductIdIsIncorrect() {
        ErrorResponse errorResponseExpected = TestObjectBuilder
                .buildABadRequestErrorResponse("Product Id can not be less of 1",
                        "electronicCommerce.domain.validations.impl.ProductServiceValidationServiceImpl",
                        "validate",
                        26);

        ErrorResponse errorResponse = webTestClient.get()
                .uri("/product/date/2020-06-14-17.00.00/productId/0/brandId/1")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertErrorResponse(errorResponseExpected, errorResponse);
    }

    @Test
    public void shouldReturnABadRequestWhenDateIsIncorrect() {
        ErrorResponse errorResponseExpected = TestObjectBuilder
                .buildABadRequestErrorResponse("Date is not in the correct format",
                        "electronicCommerce.domain.utils.DateMapper",
                        "fromStringToLocalDateTime",
                        21);

        ErrorResponse errorResponse = webTestClient.get()
                .uri("/product/date/wrong/productId/35455/brandId/1")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertErrorResponse(errorResponseExpected, errorResponse);
    }

    @Test
    public void shouldReturnNotFoundWhenNotExistProducts() {
        ErrorResponse errorResponseExpected = TestObjectBuilder
                .buildANotFoundErrorResponse("Product Not Found",
                        "electronicCommerce.persistence.postgresql.adapters.ProductPersistencePostresqlAdapter",
                        "lambda$findByDateAndProductIdAndBrandId$0",
                        35);

        ErrorResponse errorResponse = webTestClient.get()
                .uri("/product/date/2020-06-14-17.00.00/productId/1/brandId/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .returnResult()
                .getResponseBody();

        assertErrorResponse(errorResponseExpected, errorResponse);
    }

    @Test
    public void testCase1() {
        Product productExpected = TestObjectBuilder.buildAProductWithoutPriority();

        Product product = webTestClient.get()
                .uri("/product/date/2020-06-14-10.00.00/productId/35455/brandId/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        assertEquals(productExpected, product);
    }

    @Test
    public void testCase2() {
            Product productExpected = TestObjectBuilder.buildAProductEntityTestCase2();

            Product product = webTestClient.get()
                    .uri("/product/date/2020-06-14-16.00.00/productId/35455/brandId/1")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(Product.class)
                    .returnResult()
                    .getResponseBody();

        assertEquals(productExpected, product);
    }

    @Test
    public void testCase3() {
        Product productExpected = TestObjectBuilder.buildAProductWithoutPriority();

        Product product = webTestClient.get()
                .uri("/product/date/2020-06-14-21.00.00/productId/35455/brandId/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        assertEquals(productExpected, product);
    }

    @Test
    public void testCase4() {
        Product productExpected = TestObjectBuilder.buildAProductEntityTestCase4();

        Product product = webTestClient.get()
                .uri("/product/date/2020-06-15-20.00.00/productId/35455/brandId/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        assertEquals(productExpected, product);
    }

    @Test
    public void testCase5() throws Exception {
        Product productExpected = TestObjectBuilder.buildAProductEntityTestCase4();

        Product product = webTestClient.get()
                .uri("/product/date/2020-06-16-21.00.00/productId/35455/brandId/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        assertEquals(productExpected, product);
    }

    private void assertErrorResponse(ErrorResponse errorResponseExpected, ErrorResponse errorResponse) {
        assertEquals(errorResponseExpected.getHttpStatus(), errorResponse.getHttpStatus());
        assertEquals(errorResponseExpected.getType(), errorResponse.getType());
        assertEquals(errorResponseExpected.getMessage(), errorResponse.getMessage());
        assertEquals(errorResponseExpected.getClazz(), errorResponse.getClazz());
        assertEquals(errorResponseExpected.getMethod(), errorResponse.getMethod());
        assertEquals(errorResponseExpected.getLine(), errorResponse.getLine());
    }
}
