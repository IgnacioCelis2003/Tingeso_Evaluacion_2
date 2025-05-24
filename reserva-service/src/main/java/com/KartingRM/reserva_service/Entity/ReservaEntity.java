package com.KartingRM.reserva_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Reserva")
@Getter
@Setter
@NoArgsConstructor
public class ReservaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name =  "apellidoPaterno")
    private String apellidoPaterno;

    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;

    @Column(name = "rut")
    private String rut;

    @Column(name = "fechaInicio")
    private String fechaInicio;

    @Column(name = "fechaFin")
    private String fechaFin;

    @Column(name = "tipo")
    private int tipo;

    @Column(name = "cantidadPersonas")
    private int cantidadPersonas;

    @Column(name = "cantidadCumple")
    private int cantidadCumple = 0;

    @Column(name = "email")
    private String email;

    @Column(name = "tarifaEspecial")
    private float tarifaEspecial = 0;

    public ReservaEntity(String nombre, String apellidoPaterno, String apellidoMaterno, String rut, String fechaInicio,
                         int tipo, int cantidadPersonas, int cantidadCumple, String email, float tarifaEspecial) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.rut = rut;
        this.fechaInicio = fechaInicio;
        this.tipo = tipo;
        this.cantidadPersonas = cantidadPersonas;
        this.cantidadCumple = cantidadCumple;
        this.email = email;
        this.tarifaEspecial = tarifaEspecial;
    }
}
