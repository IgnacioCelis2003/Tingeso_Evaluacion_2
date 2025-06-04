package com.KartingRM.grupo_descuento_service.Controller;

import com.KartingRM.grupo_descuento_service.Service.DescuentoGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/descuentoGrupo")
public class DescuentoGrupoController {

    @Autowired
    private DescuentoGrupoService descuentoGrupoService;

    @GetMapping("/{cantidad}")
    public float getDescuentoGrupoByCantidad(@PathVariable int cantidad) {
        return descuentoGrupoService.getDescuentoGrupoByCantidad(cantidad);
    }

}
