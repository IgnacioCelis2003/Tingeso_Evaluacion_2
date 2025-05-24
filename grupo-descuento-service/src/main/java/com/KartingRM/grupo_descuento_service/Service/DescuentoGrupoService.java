package com.KartingRM.grupo_descuento_service.Service;

import com.KartingRM.grupo_descuento_service.Entity.DescuentoGrupoEntity;
import com.KartingRM.grupo_descuento_service.Repository.DescuentoGrupoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescuentoGrupoService {

    @Autowired
    private DescuentoGrupoRepository descuentoGrupoRepository;

    @PostConstruct
    public void initData() {
        if (descuentoGrupoRepository.count() == 0) {
            descuentoGrupoRepository.save(new DescuentoGrupoEntity( 1, 2, 0.00f));
            descuentoGrupoRepository.save(new DescuentoGrupoEntity( 3, 5, 0.10f));
            descuentoGrupoRepository.save(new DescuentoGrupoEntity( 6, 10, 0.20f));
            descuentoGrupoRepository.save(new DescuentoGrupoEntity( 11, 15, 0.30f));
            System.out.println("Datos iniciales de descuentos por grupo cargados.");
        }
    }

    public float getDescuentoGrupoByCantidad(int cantidad) {
        List<DescuentoGrupoEntity> descuentoGrupoEntities = descuentoGrupoRepository.findAll();

        for (DescuentoGrupoEntity descuentoGrupo : descuentoGrupoEntities) {
            if (cantidad >= descuentoGrupo.getCantidadPersonasMin() && cantidad <= descuentoGrupo.getCantidadPersonasMax()) {
                return descuentoGrupo.getDescuento();
            }
        }

        return 0.0f; // Si no se encuentra un descuento aplicable, se devuelve 0
    }
}
