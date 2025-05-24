package com.KartingRM.reserva_service.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Comprobante")
@Getter
@Setter
@NoArgsConstructor
public class ComprobanteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "idReserva")
    private long idReserva;

    @Column(name = "nombreCliente")
    private String nombreCliente;

    @Column(name = "tarifa")
    private float tarifa;

    @Column(name = "descuento")
    private float descuento;

    @Column(name = "montoDescuento")
    private float montoDescuento;

    @Column(name = "IVA")
    private float IVA;

    @Column(name = "montoTotal")
    private float montoTotal;


    public ComprobanteEntity(long idReserva, String nombreCliente) {
        this.idReserva = idReserva;
        this.nombreCliente = nombreCliente;
    }
}
