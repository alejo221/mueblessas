package co.com.mueblessas.api.util;

import co.com.mueblessas.api.exception.dto.ValidationError;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        String timestamp,
        Integer code,
        List<ValidationError> errors
) {
    public static <T> ApiResponse<T> success(String message, T data, int code) {
        return new ApiResponse<>(true, message, data, Instant.now().toString(), code, null);
    }



    public static <T> ApiResponse<T> error(String message, int code) {
        return new ApiResponse<>(false, message, null, Instant.now().toString(), code, null);
    }

    public static <T> ApiResponse<T> validationError(String message, int code, List<ValidationError> errors) {
        return new ApiResponse<>(false, message, null, Instant.now().toString(), code, errors);
    }
}
