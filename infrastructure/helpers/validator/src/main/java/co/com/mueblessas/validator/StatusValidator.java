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
        String expected = createHashByStats(stats);
        return expected.equalsIgnoreCase(stats.getHash())?Mono.empty():
                Mono.error(new InvalidHashException("The hash is not valid"));
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
