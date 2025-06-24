package co.com.mueblessas.api.stats.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {
    private Integer totalCustomerContacts;
    private Integer complaintReason;
    private Integer warrantyReason;
    private Integer questionReason;
    private Integer purchaseReason;
    private Integer praiseReason;
    private Integer exchangeReason;
    private String hash;
    private String timestamp;


}
