package com.KartingRM.cliente_frecuente_service.Service;

import com.KartingRM.cliente_frecuente_service.Entity.ClienteFrecuenteEntity;
import com.KartingRM.cliente_frecuente_service.Repository.ClienteFrecuenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ClienteFrecuenteService {

    @Autowired
    private ClienteFrecuenteRepository clienteFrecuenteRepository;

    public ClienteFrecuenteEntity registerClienteFrecuente(String rut, String fechaInicio) {
        ClienteFrecuenteEntity clienteFrecuente = new ClienteFrecuenteEntity(rut, fechaInicio);
        return clienteFrecuenteRepository.save(clienteFrecuente);
    }

    public float getDescuentoFrecuente(String rut, String fechaInicio) {
        float reservasPorMes = 0;
        List<ClienteFrecuenteEntity> reservas = clienteFrecuenteRepository.findByRut(rut);

        // Formato completo para parsear fechaInicio
        DateTimeFormatter formatoCompleto = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaInicioDT = LocalDateTime.parse(fechaInicio, formatoCompleto);

        // Extraemos YearMonth para comparar solo año y mes
        YearMonth fechaInicioYM = YearMonth.from(fechaInicioDT);

        for(ClienteFrecuenteEntity clienteFrecuente : reservas) {
            // Asumiendo que getFechaInicio() devuelve también con formato completo, igual parseamos
            LocalDateTime fechaReservaDT = LocalDateTime.parse(clienteFrecuente.getFechaInicio(), formatoCompleto);
            YearMonth fechaInicioReserva = YearMonth.from(fechaReservaDT);

            if(fechaInicioReserva.equals(fechaInicioYM)) {
                reservasPorMes++;
            }
        }

        if(reservasPorMes >= 7) {
            reservasPorMes = 0.30f;
        }
        else if(reservasPorMes == 5 || reservasPorMes == 6) {
            reservasPorMes = 0.20f;
        }
        else if(reservasPorMes == 2 || reservasPorMes == 3 || reservasPorMes == 4) {
            reservasPorMes = 0.10f;
        }

        return reservasPorMes;
    }

}
