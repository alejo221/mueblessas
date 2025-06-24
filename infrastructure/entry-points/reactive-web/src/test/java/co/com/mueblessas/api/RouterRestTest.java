package co.com.mueblessas.api;

import co.com.mueblessas.api.exception.GlobalExceptionHandler;
import co.com.mueblessas.api.stats.StatsRequest;
import co.com.mueblessas.api.stats.mapper.StatsMapper;
import co.com.mueblessas.api.validator.RequestValidator;
import co.com.mueblessas.model.stats.Stats;
import co.com.mueblessas.model.stats.exception.InvalidHashException;
import co.com.mueblessas.usecase.stats.StatsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockitoBean
    RequestValidator validator;
    @MockitoBean
    StatsMapper mapper;
    @MockitoBean
    StatsUseCase useCase;
    @MockitoBean
    Handler handler;
    @MockitoBean
    GlobalExceptionHandler exceptionHandler;

    RouterFunction<ServerResponse> route;
    @BeforeEach
    void setUp() {
        validator = mock(RequestValidator.class);
        mapper = mock(StatsMapper.class);
        useCase = mock(StatsUseCase.class);
        handler = new Handler(useCase,mapper,validator);
        route = new RouterRest().routerFunction(handler);
        webTestClient = WebTestClient.bindToRouterFunction(route).build();
    }

    @Test
    void shouldReturnCreatedOnValidStatsRequest() {
        StatsRequest request = new StatsRequest(100, 1,
                1, 1, 1, 1, 1, "validhash");
        Stats domainStats = new Stats();

        when(validator.validate(request)).thenReturn(Mono.just(request));
        when(mapper.toDomain(request)).thenReturn(domainStats);
        when(useCase.processStats(domainStats)).thenReturn(Mono.just(domainStats));

        webTestClient.post()
                .uri("/api/v1/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Registered");
    }

//    @Test
//    void shouldReturnBadRequestOnStatsRequest() {
//        StatsRequest request = new StatsRequest(100, 1,
//                1, 1, 1, 1, 1, "novalidhash");
//        Stats domainStats = new Stats();
//        when(validator.validate(request)).thenReturn(Mono.just(request));
//        when(mapper.toDomain(request)).thenReturn(domainStats);
//        when(useCase.processStats(domainStats)).thenReturn(Mono.error(new InvalidHashException("The hash is not valid")));
//
//        webTestClient.post()
//                .uri("/api/v1/stats")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(request)
//                .exchange()
//                .expectStatus().isBadRequest()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                .expectBody()
//                .jsonPath("$.message").isEqualTo("The hash is not valid");
//    }


}
