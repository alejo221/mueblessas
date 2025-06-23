package co.com.mueblessas.api.exception;

import co.com.mueblessas.api.exception.dto.ErrorResponse;
import co.com.mueblessas.api.exception.dto.ValidationError;
import co.com.mueblessas.api.util.ApiResponse;
import co.com.mueblessas.api.validator.InvalidRequestException;
import co.com.mueblessas.model.stats.exception.BussinessException;
import co.com.mueblessas.model.stats.exception.TechnicalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.slf4j.Logger;
import java.util.stream.Collectors;

@Component
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = determineHttpStatus(ex);
        ApiResponse<?> errorResponse = buildErrorResponse(ex, status);

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return writeResponse(exchange, errorResponse);
    }

    private HttpStatus determineHttpStatus(Throwable ex) {

        if (ex instanceof WebExchangeBindException) return HttpStatus.BAD_REQUEST;
        if (ex instanceof BussinessException) return HttpStatus.BAD_REQUEST;
        if (ex instanceof TechnicalException) return HttpStatus.BAD_REQUEST;
        if (ex instanceof ResponseStatusException rse) {
            return HttpStatus.resolve(rse.getStatusCode().value());
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private ApiResponse<?> buildErrorResponse(Throwable ex, HttpStatus status) {
        log.error("Exception handled: ", ex);
        String message = (status.is5xxServerError())
                ? "Error interno del servidor"
                : ex.getMessage();

        if (ex instanceof InvalidRequestException invalidEx) {
            return ApiResponse.validationError(
                    invalidEx.getMessage(),
                    status.value(),
                    invalidEx.getErrors()
            );
        }

        return ApiResponse.error(message,status.value() );
    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, ApiResponse<?> errorResponse) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            byte[] fallback = "{\"message\":\"Error procesando respuesta\"}".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(fallback);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
    }


}
