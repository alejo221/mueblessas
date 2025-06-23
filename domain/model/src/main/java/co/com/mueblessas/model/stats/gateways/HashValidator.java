package co.com.mueblessas.model.stats.gateways;

import co.com.mueblessas.model.stats.Stats;
import reactor.core.publisher.Mono;

public interface HashValidator {
    Mono<Void> validateFromStats(Stats stats);
}
