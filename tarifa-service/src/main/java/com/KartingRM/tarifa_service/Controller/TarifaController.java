package com.KartingRM.tarifa_service.Controller;

import com.KartingRM.tarifa_service.Entity.TarifaEntity;
import com.KartingRM.tarifa_service.Service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarifa")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @GetMapping("/{tipo}")
    public float getTarifaByTipo(@PathVariable int tipo) {
        float tarifa = tarifaService.getTarifaByTipo(tipo);
        return tarifa;
    }
}
