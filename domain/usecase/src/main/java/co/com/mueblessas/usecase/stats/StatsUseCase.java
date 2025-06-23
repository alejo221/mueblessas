package co.com.mueblessas.usecase.stats;

import co.com.mueblessas.model.stats.Stats;
import co.com.mueblessas.model.stats.gateways.HashValidator;
import co.com.mueblessas.model.stats.gateways.StatsEventPublisher;
import co.com.mueblessas.model.stats.gateways.StatsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class StatsUseCase {
    private final StatsRepository repository;
    //private final StatsEventPublisher publisher;
    private final HashValidator validator;

    public Mono<Stats> processStats(Stats stats){
            return validator.validateFromStats(stats)
                    .then(repository.save(stats));
                    //.then(publisher.publish(stats));
    }
}
