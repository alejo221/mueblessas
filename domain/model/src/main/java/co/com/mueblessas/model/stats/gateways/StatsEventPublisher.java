package co.com.mueblessas.model.stats.gateways;

import co.com.mueblessas.model.stats.Stats;
import reactor.core.publisher.Mono;

public interface StatsEventPublisher {
    Mono<Void> publish(Stats stats);
}
