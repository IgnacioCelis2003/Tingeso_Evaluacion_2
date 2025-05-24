package com.KartingRM.reporte_service.Service;

import com.KartingRM.reporte_service.DTO.ComprobanteDTO;
import com.KartingRM.reporte_service.DTO.ReservaDTO;
import com.KartingRM.reporte_service.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private RestTemplate restTemplate;


    public ReservaDTO obtenerReserva(long id) {
        String url = "http://localhost:8079/reserva/" + id;
        ResponseEntity<ReservaDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ReservaDTO>() {
                }
        );
        return response.getBody();
    }

    public List<ComprobanteDTO> obtenerComprobantes() {
        String url = "http://localhost:8079/comprobante/comprobantes";
        ResponseEntity<List<ComprobanteDTO>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ComprobanteDTO>>() {
                }
        );
        return response.getBody();
    }


    public List<List<Double>> getReportePorTipo(String inicio, String fin) {
        if (inicio == null || fin == null) {
            throw new RuntimeException("Los valores no pueden ser nulos");
        }
        List<ComprobanteDTO> comprobantes = obtenerComprobantes();
        List<List<Double>> reporte = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth inicioFormatted = YearMonth.parse(inicio, formatter);
        YearMonth finFormatted = YearMonth.parse(fin, formatter);

        int mesInicio = inicioFormatted.getMonthValue();
        int mesFin = finFormatted.getMonthValue();
        if (mesInicio > mesFin) {
            throw new RuntimeException("El mes de inicio no puede ser mayor al mes de fin");
        }

        //Inicializamos las listas
        for (int i = 0; i < 3; i++) {
            List<Double> fila = new ArrayList<>(Collections.nCopies(12, 0.0));
            reporte.add(fila);
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        for (ComprobanteDTO comprobante : comprobantes) {
            ReservaDTO reserva = obtenerReserva(comprobante.getId());
            if (reserva == null) {
                throw new RuntimeException("La reserva no existe");
            }
            LocalDateTime fechaInicio = LocalDateTime.parse(reserva.getFechaInicio(), formato);
            int mes = fechaInicio.getMonthValue() - 1;
            Double valorActual = reporte.get(reserva.getTipo() - 1).get(mes);

            reporte.get(reserva.getTipo() - 1).set(mes, valorActual + comprobante.getMontoTotal());
        }

        List<List<Double>> reporteFinal = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Double> fila = reporte.get(i).subList(mesInicio - 1, mesFin);
            reporteFinal.add(fila);
        }

        return reporteFinal;
    }


    public List<List<Double>> getReportePorCantidad(String inicio, String fin) {
        if (inicio == null || fin == null) {
            throw new RuntimeException("Los valores no pueden ser nulos");
        }
        List<ComprobanteDTO> comprobantes = obtenerComprobantes();
        List<List<Double>> reporte = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth inicioFormatted = YearMonth.parse(inicio, formatter);
        YearMonth finFormatted = YearMonth.parse(fin, formatter);

        int mesInicio = inicioFormatted.getMonthValue();
        int mesFin = finFormatted.getMonthValue();
        if (mesInicio > mesFin) {
            throw new RuntimeException("El mes de inicio no puede ser mayor al mes de fin");
        }

        //Inicializamos las listas
        for (int i = 0; i < 4; i++) {
            List<Double> fila = new ArrayList<>(Collections.nCopies(12, 0.0));
            reporte.add(fila);
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        for (ComprobanteDTO comprobante : comprobantes) {
            ReservaDTO reserva = obtenerReserva(comprobante.getId());
            if (reserva == null) {
                throw new RuntimeException("La reserva no existe");
            }
            LocalDateTime fechaInicio = LocalDateTime.parse(reserva.getFechaInicio(), formato);
            int mes = fechaInicio.getMonthValue() - 1;

            if (reserva.getCantidadPersonas() == 1 || reserva.getCantidadPersonas() == 2) {
                Double valorActual = reporte.get(0).get(mes);
                reporte.get(0).set(mes, valorActual + comprobante.getMontoTotal());

            } else if (reserva.getCantidadPersonas() >= 3 && reserva.getCantidadPersonas() <= 5) {
                Double valorActual = reporte.get(1).get(mes);
                reporte.get(1).set(mes, valorActual + comprobante.getMontoTotal());
            } else if (reserva.getCantidadPersonas() >= 6 && reserva.getCantidadPersonas() <= 10) {
                Double valorActual = reporte.get(2).get(mes);
                reporte.get(2).set(mes, valorActual + comprobante.getMontoTotal());
            } else if (reserva.getCantidadPersonas() >= 11 && reserva.getCantidadPersonas() <= 15) {
                Double valorActual = reporte.get(3).get(mes);
                reporte.get(3).set(mes, valorActual + comprobante.getMontoTotal());
            } else {
                throw new RuntimeException("La cantidad de personas no es válida");
            }

        }

        List<List<Double>> reporteFinal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<Double> fila = reporte.get(i).subList(mesInicio - 1, mesFin);
            reporteFinal.add(fila);
        }

        return reporteFinal;
    }

}


