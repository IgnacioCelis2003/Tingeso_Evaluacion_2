package com.KartingRM.reserva_service.Controller;

import com.KartingRM.reserva_service.Entity.ComprobanteEntity;
import com.KartingRM.reserva_service.Service.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comprobante")
public class ComprobanteController {

    @Autowired
    private ComprobanteService comprobanteService;

    @GetMapping("/comprobantes")
    public List<ComprobanteEntity> getComprobantes() {
        return comprobanteService.getComprobantes();
    }

}
