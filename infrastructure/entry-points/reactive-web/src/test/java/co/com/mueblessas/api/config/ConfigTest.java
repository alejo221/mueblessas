package co.com.mueblessas.api.config;

import co.com.mueblessas.api.Handler;
import co.com.mueblessas.api.RouterRest;
import co.com.mueblessas.api.exception.GlobalExceptionHandler;
import co.com.mueblessas.api.stats.mapper.StatsMapper;
import co.com.mueblessas.api.validator.RequestValidator;
import co.com.mueblessas.usecase.stats.StatsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

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
    void corsConfigurationShouldAllowOrigins() {
        handler = new Handler(useCase,mapper,validator);
        webTestClient.get()
                .uri("/api/v1/status")
                .exchange()
                .expectStatus().isOk();
    }

}