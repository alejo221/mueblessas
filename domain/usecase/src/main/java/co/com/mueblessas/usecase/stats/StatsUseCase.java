package co.com.mueblessas.usecase.stats;

import co.com.mueblessas.model.events.gateways.EventsGateway;
import co.com.mueblessas.model.stats.Stats;
import co.com.mueblessas.model.stats.gateways.HashValidator;
import co.com.mueblessas.model.stats.gateways.StatsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class StatsUseCase {
    private final StatsRepository repository;
    private final HashValidator validator;
    private final EventsGateway eventsGateway;

    public Mono<Stats> processStats(Stats stats) {
        return validator.validateFromStats(stats)
                .then(Mono.defer(() ->
                repository.save(stats)
                        .flatMap(saved -> eventsGateway.emit(saved).thenReturn(saved))
        ));
    }
}
