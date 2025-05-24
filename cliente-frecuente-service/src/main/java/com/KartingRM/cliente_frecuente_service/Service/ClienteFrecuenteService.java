package com.KartingRM.cliente_frecuente_service.Service;

import com.KartingRM.cliente_frecuente_service.Entity.ClienteFrecuenteEntity;
import com.KartingRM.cliente_frecuente_service.Repository.ClienteFrecuenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        DateTimeFormatter formatoAnioMes = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth fechaInicioYM = YearMonth.parse(fechaInicio, formatoAnioMes);

        for(ClienteFrecuenteEntity clienteFrecuente : reservas) {
            YearMonth fechaInicioReserva = YearMonth.parse(clienteFrecuente.getFechaInicio(), formatoAnioMes);
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
        else if(reservasPorMes == 3 || reservasPorMes == 4 || reservasPorMes == 2) {
            reservasPorMes = 0.10f;
        }

        return reservasPorMes;
    }
}
