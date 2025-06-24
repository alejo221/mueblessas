package co.com.mueblessas.usecase;

import co.com.mueblessas.model.events.gateways.EventsGateway;
import co.com.mueblessas.model.stats.Stats;
import co.com.mueblessas.model.stats.exception.InvalidHashException;
import co.com.mueblessas.model.stats.gateways.HashValidator;
import co.com.mueblessas.model.stats.gateways.StatsRepository;
import co.com.mueblessas.usecase.stats.StatsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class StatsUseCaseTest {

    @Mock
    private StatsRepository repository;

    @Mock
    private HashValidator validator;

    @Mock
    private EventsGateway eventsGateway;


    private StatsUseCase useCase;

    private Stats sampleStats;

    @BeforeEach
    void setUp() {
        sampleStats = Stats.builder()
                .totalCustomerContacts(100)
                .complaintReason(10)
                .warrantyReason(5)
                .questionReason(20)
                .purchaseReason(50)
                .praiseReason(10)
                .exchangeReason(5)
                .hash("abc123")
                .build();
        useCase = new StatsUseCase(repository, validator, eventsGateway);
    }

    @Test
    void shouldProcessStatsSuccessfully() {
        when(validator.validateFromStats(sampleStats)).thenReturn(Mono.empty());
        when(repository.save(sampleStats)).thenReturn(Mono.just(sampleStats));
        when(eventsGateway.emit(sampleStats)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.processStats(sampleStats))
                .expectNext(sampleStats)
                .verifyComplete();

        verify(validator).validateFromStats(sampleStats);
        verify(repository).save(sampleStats);
        verify(eventsGateway).emit(sampleStats);
    }


    @Test
    void shouldFailWhenValidatorFails() {
        doReturn(Mono.error(new InvalidHashException("Validation failed")))
                .when(validator)
                .validateFromStats(any());
        StepVerifier.create(useCase.processStats(sampleStats))
                .expectErrorMatches(error ->
                        error instanceof InvalidHashException &&
                                error.getMessage().equals("Validation failed"))
                .verify();

        verify(repository, never()).save(any());
        verify(eventsGateway, never()).emit(any());
    }

    @Test
    void shouldFailWhenRepositoryFails() {
        when(validator.validateFromStats(sampleStats)).thenReturn(Mono.empty());
        when(repository.save(sampleStats)).thenReturn(Mono.error(new RuntimeException("Save failed")));

        StepVerifier.create(useCase.processStats(sampleStats))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("Save failed"))
                .verify();

        verify(repository).save(sampleStats);
        verify(eventsGateway, never()).emit(any());
    }
}
