package co.com.mueblessas.api.exception.dto;

import java.time.Instant;
import java.util.List;


public record ErrorResponse<T>(
        boolean success,
        String message,
        T data,
        Instant timestamp,
        Integer code,
        List<ValidationError> errors
){
    public ErrorResponse(boolean success, String message, T data, Integer code) {
        this(success,message,data,Instant.now(), code,null);
    }

    public ErrorResponse(boolean success, String message, T data, Integer code, List<ValidationError> errors) {
        this(success, message, data, Instant.now(), code, errors);
    }
}

