package utils;

import electronicCommerce.domain.models.Product;
import electronicCommerce.domain.utils.DateMapper;
import electronicCommerce.persistence.postgresql.entities.BrandEntity;
import electronicCommerce.persistence.postgresql.entities.ProductEntity;
import electronicCommerce.rest.exceptionHandlers.ErrorResponse;

import java.math.BigDecimal;

public class TestObjectBuilder {
    public static Product buildAProductWithPriority() {
        return Product.builder()
                .brandId(1)
                .startDate(DateMapper.fromStringToLocalDateTime("2020-06-14-15.00.00"))
                .endDate(DateMapper.fromStringToLocalDateTime("2020-06-14-18.30.00"))
                .priceListId(2)
                .productId(35455)
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();
    }

    public static ErrorResponse buildABadRequestErrorResponse(String message, String clazz, String method, int line) {
        return ErrorResponse.builder()
                .httpStatus(400)
                .type("BAD_REQUEST")
                .message(message)
                .clazz(clazz)
                .method(method)
                .line(line)
                .build();
    }

    public static ErrorResponse buildANotFoundErrorResponse(String message, String clazz, String method, int line) {
        return ErrorResponse.builder()
                .httpStatus(404)
                .type("NOT_FOUND")
                .message(message)
                .clazz(clazz)
                .method(method)
                .line(line)
                .build();
    }

    public static Product buildAProductWithoutPriority() {
        return Product.builder()
                .brandId(1)
                .startDate(DateMapper.fromStringToLocalDateTime("2020-06-14-00.00.00"))
                .endDate(DateMapper.fromStringToLocalDateTime("2020-12-31-23.59.59"))
                .priceListId(1)
                .productId(35455)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();
    }

    public static Product buildAProductEntityTestCase2() {
        return Product.builder()
                .brandId(1)
                .startDate(DateMapper.fromStringToLocalDateTime("2020-06-14-15.00.00"))
                .endDate(DateMapper.fromStringToLocalDateTime("2020-06-14-18.30.00"))
                .priceListId(2)
                .productId(35455)
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();
    }

    public static Product buildAProductEntityTestCase4() {
        return Product.builder()
                .brandId(1)
                .startDate(DateMapper.fromStringToLocalDateTime("2020-06-15-16.00.00"))
                .endDate(DateMapper.fromStringToLocalDateTime("2020-12-31-23.59.59"))
                .priceListId(4)
                .productId(35455)
                .priority(1)
                .price(new BigDecimal("38.95"))
                .currency("EUR")
                .build();
    }

}
