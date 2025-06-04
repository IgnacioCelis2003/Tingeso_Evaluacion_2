package com.KartingRM.cliente_frecuente_service.Controller;

import com.KartingRM.cliente_frecuente_service.Service.ClienteFrecuenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clienteFrecuente")
public class ClienteFrecuenteController {

    @Autowired
    private ClienteFrecuenteService clienteFrecuenteService;

    @PostMapping("/register/{rut}/{fechaInicio}")
    public void registerClienteFrecuente(@PathVariable String rut, @PathVariable String fechaInicio) {
        clienteFrecuenteService.registerClienteFrecuente(rut, fechaInicio);
    }

    @GetMapping("/descuento/{rut}/{fechaInicio}")
    public float getDescuentoFrecuente(@PathVariable String rut, @PathVariable String fechaInicio) {
        return clienteFrecuenteService.getDescuentoFrecuente(rut, fechaInicio);
    }

}
