package com.KartingRM.dia_especial_service.Service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class TarifaEspecialService {

    public float calcularTarifaEspecial(float tarifaBase, String fechaInicio) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaInicioFormato = LocalDateTime.parse(fechaInicio, formato);

        DateTimeFormatter formatoMesDia = DateTimeFormatter.ofPattern("MM-dd");
        String fechaInicioMesDia = fechaInicioFormato.format(formatoMesDia);

        List<String> feriados = Arrays.asList("01-01", "05-01", "09-18", "09-19", "12-25");

        float tarifaEspecial = tarifaBase;

        if(fechaInicioFormato.getDayOfWeek() == DayOfWeek.SATURDAY || fechaInicioFormato.getDayOfWeek() == DayOfWeek.SUNDAY || feriados.contains(fechaInicioMesDia))
        {
            tarifaEspecial = tarifaBase + (tarifaBase * 0.25f);
        }

        return tarifaEspecial;
    }

    public float calcularDescuentoCumple(int cantidadCumple, int cantidadPersonas, float tarifaBase) {
        float descuento = 0.0f;

        if (cantidadCumple >= 1 && (cantidadPersonas >= 3 && cantidadPersonas <= 5)) {
            descuento = tarifaBase * 0.50f;
        }
        if ((cantidadCumple >= 2) && (cantidadPersonas >= 6)) {
            descuento = tarifaBase;
        }
        return descuento;

    }
}
