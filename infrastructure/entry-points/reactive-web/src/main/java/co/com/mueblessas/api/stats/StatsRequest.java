package co.com.mueblessas.api.stats;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsRequest {

    @NotNull(message = "El totalContactoClientes es obligatorio")
    @Min(value = 0, message = "El valor no es válido para un totalContactoClientes")
    private Integer totalContactoClientes;

    @NotNull(message = "El motivoReclamo es obligatorio")
    @Min(value = 0, message = "El valor no es válido para un motivoReclamo")
    private Integer motivoReclamo;

    @NotNull(message = "El motivoGarantia es obligatorio")
    @Min(value = 0, message = "El valor no es válido para un motivoGarantia")
    private Integer motivoGarantia;

    @NotNull(message = "El motivoDuda es obligatorio")
    @Min(value = 0, message = "El valor no es válido para un motivoDuda")
    private Integer motivoDuda;

    @NotNull(message = "El motivoCompra es obligatorio")
    @Min(value = 0, message = "El valor no es válido para un motivoCompra")
    private Integer motivoCompra;

    @NotNull(message = "El motivoFelicitaciones es obligatorio")
    @Min(value = 0, message = "El valor no es válido para un motivoFelicitaciones")
    private Integer motivoFelicitaciones;

    @NotNull(message = "El motivoCambio es obligatorio")
    @Min(value = 0, message = "El valor no es válido para un motivoCambio")
    private Integer motivoCambio;

    @NotBlank(message = "El hash es obligatorio")
    private String hash;
}
