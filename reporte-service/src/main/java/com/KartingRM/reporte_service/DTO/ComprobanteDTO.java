package com.KartingRM.reporte_service.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteDTO {

    private long id;

    private long idReserva;

    private String nombreCliente;

    private float tarifa;

    private float descuento;

    private float montoDescuento;

    private float IVA;

    private float montoTotal;
}
