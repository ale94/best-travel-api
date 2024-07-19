package ar.com.alejandro.best_travel_api.api.controllers.error_handler;

import ar.com.alejandro.best_travel_api.api.models.responses.BaseErrorResponse;
import ar.com.alejandro.best_travel_api.api.models.responses.ErrorResponse;
import ar.com.alejandro.best_travel_api.util.exceptions.ForbiddenCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenCustomerController {

    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse forbiddenCustomerExceptionException(ForbiddenCustomerException exception) {
        return ErrorResponse.builder()
                .error(exception.getMessage())
                .status(HttpStatus.FORBIDDEN.name())
                .code(HttpStatus.FORBIDDEN.value())
                .build();
    }
}
