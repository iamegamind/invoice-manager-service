package za.co.digitalplatoon.invoiceservice.exception;

import org.springframework.http.HttpStatus;

public class InvoiceServiceException extends Exception {

    private final HttpStatus status;

    public InvoiceServiceException(HttpStatus status, String errorMessage) {
        super(errorMessage);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
