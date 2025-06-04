package com.KartingRM.rack_service.Controller;

import com.KartingRM.rack_service.DTO.ReservaDTO;
import com.KartingRM.rack_service.Service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rack")
public class RackController {

    @Autowired
    private RackService rackService;

    @GetMapping("/reservas")
    public List<ReservaDTO> obtenerReservas() {
        return rackService.obtenerReservas();
    }
}
