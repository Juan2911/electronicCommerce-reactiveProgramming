package electronicCommerce.rest.exceptionHandlers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int httpStatus;
    private String type;
    private String message;
    private String clazz;
    private String method;
    private int line;

}
