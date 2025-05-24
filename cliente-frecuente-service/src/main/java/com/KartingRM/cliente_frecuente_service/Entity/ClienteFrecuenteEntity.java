package com.KartingRM.cliente_frecuente_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ClienteFrecuente")
@Getter
@Setter
@NoArgsConstructor
public class ClienteFrecuenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rut")
    private String rut;

    @Column(name = "fechaInicio")
    private String fechaInicio;

    public ClienteFrecuenteEntity(String rut, String fechaInicio) {
        this.rut = rut;
        this.fechaInicio = fechaInicio;
    }
}
