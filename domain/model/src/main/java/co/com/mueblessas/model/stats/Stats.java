package co.com.mueblessas.model.stats;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.time.Instant;


/**
 * Represents user interaction stats.
 * Original Spanish fields:
 * totalContactoClientes → totalCustomerContacts
 * motivoReclamo → complaintReason
 * motivoGarantia → warrantyReason
 * motivoDuda → questionReason
 * motivoCompra → purchaseReason
 * motivoFelicitaciones → praiseReason
 * motivoCambio → exchangeReason
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Stats {
    int totalCustomerContacts;
    int complaintReason;
    int warrantyReason;
    int questionReason;
    int purchaseReason;
    int praiseReason;
    int exchangeReason;
    String hash;
    Instant timestamp;
}
