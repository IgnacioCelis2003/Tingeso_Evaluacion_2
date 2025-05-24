package com.KartingRM.dia_especial_service.Controller;

import com.KartingRM.dia_especial_service.Service.TarifaEspecialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tarifaEspecial")
public class TarifaEspecialController {

    @Autowired
    private TarifaEspecialService tarifaEspecialService;

    @GetMapping("/calcular/{tarifaBase}/{fechaInicio}")
    public float calcularTarifaEspecial(@PathVariable float tarifaBase, @PathVariable String fechaInicio) {
        return tarifaEspecialService.calcularTarifaEspecial(tarifaBase, fechaInicio);
    }

    @GetMapping("/descuento/{cantidadCumple}/{cantidadPersonas}/{tarifaBase}")
    public float calcularDescuentoCumple(@PathVariable int cantidadCumple, @PathVariable int cantidadPersonas, @PathVariable float tarifaBase) {
        return tarifaEspecialService.calcularDescuentoCumple(cantidadCumple, cantidadPersonas, tarifaBase);
    }
}
