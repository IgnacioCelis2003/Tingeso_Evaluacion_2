package com.KartingRM.reserva_service.Controller;

import com.KartingRM.reserva_service.Entity.ReservaEntity;
import com.KartingRM.reserva_service.Service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/crear")
    public ReservaEntity crearReserva(@RequestBody ReservaEntity reserva){
        return reservaService.crearReserva(
                reserva.getNombre(),
                reserva.getApellidoPaterno(),
                reserva.getApellidoMaterno(),
                reserva.getRut(),
                reserva.getFechaInicio(),
                reserva.getTipo(),
                reserva.getCantidadPersonas(),
                reserva.getCantidadCumple(),
                reserva.getEmail(),
                reserva.getTarifaEspecial()
        );
    }

    @GetMapping("/reservas")
    public List<ReservaEntity> getReservas(){
        return reservaService.getReservas();
    }

    @GetMapping("/{id}")
    public ReservaEntity getReservaById(@PathVariable int id){
        return reservaService.getReservaById(id);
    }
}
