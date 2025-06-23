package co.com.mueblessas.api.stats.mapper;

import co.com.mueblessas.api.stats.StatsRequest;
import co.com.mueblessas.model.stats.Stats;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class StatsMapper {

    public Stats toDomain(StatsRequest statsRequest) {
        return Stats.builder()
                .totalCustomerContacts(statsRequest.getTotalContactoClientes())
                .complaintReason(statsRequest.getMotivoReclamo())
                .warrantyReason(statsRequest.getMotivoGarantia())
                .questionReason(statsRequest.getMotivoDuda())
                .purchaseReason(statsRequest.getMotivoCompra())
                .praiseReason(statsRequest.getMotivoFelicitaciones())
                .exchangeReason(statsRequest.getMotivoCambio())
                .hash(statsRequest.getHash())
                .timestamp(Instant.now()) // ← se genera en el dominio, no viene del request
                .build();

    }
}