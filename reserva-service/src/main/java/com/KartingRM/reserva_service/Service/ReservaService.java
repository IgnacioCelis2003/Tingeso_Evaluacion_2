package com.KartingRM.reserva_service.Service;


import com.KartingRM.reserva_service.Entity.ComprobanteEntity;
import com.KartingRM.reserva_service.Entity.ReservaEntity;
import com.KartingRM.reserva_service.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ComprobanteService comprobanteService;

    @Autowired
    private RestTemplate restTemplate;

    public List<ReservaEntity> getReservas(){
        return reservaRepository.findAll();
    }

    @Transactional
    boolean isReservaPosible(LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        if (fechaInicio == null || fechaFin == null) {
            throw new RuntimeException("Las fechas no pueden ser nulas");
        }

        List<ReservaEntity> reservas = reservaRepository.findAll();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Valida que la fecha de inicio y fin esten dentro del horario de atención
        DateTimeFormatter formatoHH = DateTimeFormatter.ofPattern("HH");
        LocalTime apertura = LocalTime.parse("10", formatoHH);
        LocalTime cierre = LocalTime.parse("22", formatoHH);
        if (fechaInicio.toLocalTime().isBefore(apertura) || fechaFin.toLocalTime().isAfter(cierre)) {
            throw new RuntimeException("La reserva no puede ser fuera del horario de atención");
        }

        // Valida que la fecha de inicio y fin no sean anteriores a la fecha actual
        LocalDateTime now = LocalDateTime.now();
        if (fechaInicio.isBefore(now) || fechaFin.isBefore(now)) {
            throw new RuntimeException("Las fechas no pueden ser anteriores a la fecha actual");
        }

        for (ReservaEntity reserva : reservas) {

            LocalDateTime inicioFor = LocalDateTime.parse(reserva.getFechaInicio(), formato);
            LocalDateTime finFor = LocalDateTime.parse(reserva.getFechaFin(), formato);

            if (inicioFor.isBefore(fechaFin) && finFor.isAfter(fechaInicio)) {
                return false;
            }
        }
        return true;
    }


    @Transactional
    public ReservaEntity crearReserva(String nombre, String apellidoPaterno, String apellidoMaterno, String rut, String fechaInicio,
                                      int tipo, int cantidadPersonas, int cantidadCumple, String email, float tarifaEspecial) {

        // Validar que los campos no sean nulos o vacíos
        if (email.isEmpty() || fechaInicio == null || fechaInicio.isEmpty() || cantidadPersonas <= 0 ||
                cantidadPersonas > 15 || cantidadCumple < 0 || cantidadCumple > 15 || cantidadCumple > cantidadPersonas ||
                nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || rut.isEmpty()) {

            throw new RuntimeException("Los valores deben ser validos");
        }

        if(tarifaEspecial < -15000 && tipo == 1){
            throw new RuntimeException("La tarifa especial no puede ser menor a 15000");
        } else if (tarifaEspecial < -20000 && tipo == 2) {
            throw new RuntimeException("La tarifa especial no puede ser menor a 20000");
        }
        else if (tarifaEspecial < -25000 && tipo == 3) {
            throw new RuntimeException("La tarifa especial no puede ser menor a 25000");
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaInicioFormatted = LocalDateTime.parse(fechaInicio, formato);
        LocalDateTime fechaFinFormatted;
        switch (tipo) {
            case 1:
                fechaFinFormatted = fechaInicioFormatted.plusMinutes(30);
                break;
            case 2:
                fechaFinFormatted = fechaInicioFormatted.plusMinutes(35);
                break;
            case 3:
                fechaFinFormatted = fechaInicioFormatted.plusMinutes(40);
                break;
            default:
                throw new RuntimeException("El tipo de reserva no es válido");
        }

        if (!isReservaPosible(fechaInicioFormatted, fechaFinFormatted)) {
            throw new RuntimeException("La reserva no es posible");
        }


        String fechaInicioStr = fechaInicioFormatted.format(formato);
        String fechaFinStr = fechaFinFormatted.format(formato);

        ReservaEntity reserva = new ReservaEntity(nombre, apellidoPaterno, apellidoMaterno, rut, fechaInicioStr, tipo, cantidadPersonas, cantidadCumple, email, tarifaEspecial);


        reserva.setFechaFin(fechaFinStr);

        reservaRepository.save(reserva);

        restTemplate.postForObject("http://localhost:8080/clienteFrecuente/register/" + rut + "/" + fechaInicioStr, null, void.class);

        // Crear el comprobante
        ComprobanteEntity comprobante = comprobanteService.crearComprobante(reserva.getId(), nombre + " " + apellidoPaterno + " " + apellidoMaterno);

        return reserva;
    }

}
