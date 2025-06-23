package co.com.mueblessas.events;

import co.com.mueblessas.model.events.gateways.EventsGateway;
import co.com.mueblessas.model.stats.Stats;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.DomainEventBus;
import org.reactivecommons.async.impl.config.annotations.EnableDomainEventBus;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.logging.Level;

import static reactor.core.publisher.Mono.from;

@Log
@RequiredArgsConstructor
@EnableDomainEventBus
public class ReactiveEventsGateway implements EventsGateway {
    public static final String STATS_VALIDATED_EVENT = "event.stats.validated";
    private final DomainEventBus domainEventBus;

    @Override
    public Mono<Void> emit(Stats stats) {
        log.log(Level.INFO, "Sending domain event: {0}: {1}", new String[]{STATS_VALIDATED_EVENT, stats.getHash()});
        return from(domainEventBus.emit(new DomainEvent<>(STATS_VALIDATED_EVENT, UUID.randomUUID().toString(), stats)));
    }
}
