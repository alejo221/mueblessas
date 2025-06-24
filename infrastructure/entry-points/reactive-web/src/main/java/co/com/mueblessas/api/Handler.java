package co.com.mueblessas.api;

import co.com.mueblessas.api.stats.StatsRequest;
import co.com.mueblessas.api.stats.mapper.StatsMapper;
import co.com.mueblessas.api.stats.mapper.StatsResponse;
import co.com.mueblessas.api.util.ApiResponse;
import co.com.mueblessas.api.validator.RequestValidator;
import co.com.mueblessas.model.stats.Stats;
import co.com.mueblessas.usecase.stats.StatsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final StatsUseCase useCase;
    private final StatsMapper mapper;
    private final RequestValidator validator;

    public Mono<ServerResponse> statsUseCase(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(StatsRequest.class)
                .flatMap(validator::validate)
                .map(mapper::toDomain)
                .flatMap(useCase::processStats)
                .map(mapper::toEntity)
                .flatMap(stats -> {
                    System.out.println(stats.getTimestamp());
                    ApiResponse<StatsResponse> response = ApiResponse.success("Registered", stats, 201);
                    return ServerResponse
                            .status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(response);
                });
    }

    public Mono<ServerResponse> status(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("Working!");
    }


}
