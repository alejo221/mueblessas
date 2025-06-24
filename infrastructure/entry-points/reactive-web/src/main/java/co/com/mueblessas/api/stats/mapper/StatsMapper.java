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
                .timestamp(Instant.now())
                .build();
    }

    public StatsResponse toEntity(Stats stats) {
        return StatsResponse.builder()
                .totalCustomerContacts(stats.getTotalCustomerContacts())
                .complaintReason(stats.getComplaintReason())
                .warrantyReason(stats.getWarrantyReason())
                .questionReason(stats.getQuestionReason())
                .purchaseReason(stats.getPurchaseReason())
                .praiseReason(stats.getPraiseReason())
                .exchangeReason(stats.getExchangeReason())
                .hash(stats.getHash())
                .timestamp(stats.getTimestamp() != null ? stats.getTimestamp().toString() : null)
                .build();
    }
}