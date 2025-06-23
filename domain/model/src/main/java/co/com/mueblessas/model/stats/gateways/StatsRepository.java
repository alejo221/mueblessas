package co.com.mueblessas.model.stats.gateways;

import co.com.mueblessas.model.stats.Stats;
import reactor.core.publisher.Mono;

public interface StatsRepository {
    Mono<Stats> save(Stats stats);
}
