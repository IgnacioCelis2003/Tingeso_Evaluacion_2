package com.KartingRM.reporte_service.Controller;

import com.KartingRM.reporte_service.Service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reporte")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/tipo/{inicio}/{fin}")
    public List<List<Double>> getReportePorTipo(@PathVariable String inicio, @PathVariable String fin){
        return reporteService.getReportePorTipo(inicio, fin);
    }

    @GetMapping("/cantidad/{inicio}/{fin}")
    public List<List<Double>> getReportePorCantidad(@PathVariable String inicio, @PathVariable String fin){
        return reporteService.getReportePorCantidad(inicio, fin);
    }
}
