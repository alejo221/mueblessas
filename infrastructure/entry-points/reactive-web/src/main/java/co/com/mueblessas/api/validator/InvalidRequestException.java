package co.com.mueblessas.api.validator;

import co.com.mueblessas.api.exception.dto.ValidationError;
import co.com.mueblessas.model.stats.exception.TechnicalException;

import java.util.List;

public class InvalidRequestException extends TechnicalException {
    private List<ValidationError> errors ;
    public InvalidRequestException(String message, List<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
