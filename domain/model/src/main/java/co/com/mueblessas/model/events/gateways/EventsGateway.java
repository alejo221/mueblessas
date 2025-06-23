package co.com.mueblessas.model.events.gateways;

import co.com.mueblessas.model.stats.Stats;
import reactor.core.publisher.Mono;

public interface EventsGateway {
    Mono<Void> emit(Stats stat);
}
