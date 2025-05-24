package com.KartingRM.tarifa_service.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Tarifa")
@Getter
@Setter
@NoArgsConstructor
public class TarifaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tipo")
    private int tipo;

    @Column(name = "precio")
    private float precio;

    @Column(name = "minutos")
    private int minutos;

    public TarifaEntity(int tipo, float precio, int minutos) {
        this.tipo = tipo;
        this.precio = precio;
        this.minutos = minutos;
    }
}
