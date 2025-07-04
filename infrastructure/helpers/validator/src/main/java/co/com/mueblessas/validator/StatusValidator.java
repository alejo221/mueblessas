package co.com.mueblessas.validator;

import co.com.mueblessas.model.stats.Stats;
import co.com.mueblessas.model.stats.exception.InvalidHashException;
import co.com.mueblessas.model.stats.gateways.HashValidator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;


@Component
public class StatusValidator implements HashValidator {

    @Override
    public Mono<Void> validateFromStats(Stats stats) {
        return Mono.defer(() -> {
            String expected = createHashByStats(stats);
            if (!expected.equalsIgnoreCase(stats.getHash())) {
                return Mono.error(new InvalidHashException("The hash is not valid"));
            }
            return Mono.empty();
        });
    }

    private String createHashByStats(Stats stats){
        String data = String.format("%d,%d,%d,%d,%d,%d,%d",
                stats.getTotalCustomerContacts(),
                stats.getComplaintReason(),
                stats.getWarrantyReason(),
                stats.getQuestionReason(),
                stats.getPurchaseReason(),
                stats.getPraiseReason(),
                stats.getExchangeReason()
        );
        return DigestUtils.md5DigestAsHex(data.getBytes(StandardCharsets.UTF_8));
    }


}
