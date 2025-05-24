package com.KartingRM.grupo_descuento_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DescuentoGrupo")
@Getter
@Setter
@NoArgsConstructor
public class DescuentoGrupoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cantidadPersonasMin")
    private int cantidadPersonasMin;

    @Column(name = "cantidadPersonasMax")
    private int cantidadPersonasMax;

    @Column(name = "descuento")
    private float descuento;

    public DescuentoGrupoEntity(int cantidadPersonasMin, int cantidadPersonasMax, float descuento) {
        this.cantidadPersonasMin = cantidadPersonasMin;
        this.cantidadPersonasMax = cantidadPersonasMax;
        this.descuento = descuento;
    }
}
