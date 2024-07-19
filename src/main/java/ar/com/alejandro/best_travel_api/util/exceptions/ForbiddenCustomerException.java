package ar.com.alejandro.best_travel_api.util.exceptions;

public class ForbiddenCustomerException extends RuntimeException {

    public ForbiddenCustomerException() {
        super("This customer is blocked");
    }
}
