package co.com.mueblessas.api.validator;

import co.com.mueblessas.api.exception.dto.ValidationError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T body) {
        Set<ConstraintViolation<T>> violations = validator.validate(body);

        if (violations.isEmpty()) {
            return Mono.just(body);
        }

        List<ValidationError> errors = violations.stream()
                .map(violation -> new ValidationError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                ))
                .toList();

        return Mono.error(new InvalidRequestException("Validación fallida", errors));
    }
}
