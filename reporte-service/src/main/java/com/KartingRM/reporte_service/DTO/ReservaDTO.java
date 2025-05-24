package com.KartingRM.reporte_service.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private long id;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String rut;

    private String fechaInicio;

    private String fechaFin;

    private int tipo;

    private int cantidadPersonas;

    private int cantidadCumple = 0;

    private String email;

    private float tarifaEspecial = 0;
}
