package electronicCommerce.rest.exceptionHandlers;

import electronicCommerce.domain.exceptions.NotFoundException;
import electronicCommerce.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiExceptionHandlerTest {

    @Test
    public void handleNotFoundException() {
        NotFoundException exception = new NotFoundException("Not found");
        ApiExceptionHandler handler = new ApiExceptionHandler();

        ResponseEntity<ErrorResponse> response = handler.handleNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    public void handleValidationException() {
        ValidationException exception = new ValidationException("Validation error");
        ApiExceptionHandler handler = new ApiExceptionHandler();

        ResponseEntity<ErrorResponse> response = handler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation error", response.getBody().getMessage());
    }

    @Test
    public void handleInternalServerErrorException() {
        Exception exception = new Exception("Internal server error");
        ApiExceptionHandler handler = new ApiExceptionHandler();

        ResponseEntity<ErrorResponse> response = handler.handleInternalServerErrorException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().getMessage());
    }
}
